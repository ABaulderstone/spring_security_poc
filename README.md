# Spring Security

This repo contains setup for stateless, JWT based, authentication. Additionality it contains an example of simple role based authorisation, as well as a mechanism for CSRF protection.
Some things to keep in mind if you are thinking about following this guide.

- Secrets are hardcoded in `application.properties` - you will want to use environment variables
- There isn't a mechanism for registration. This would likely require some kind of validity check on emails, likely a mechanism for sending a `magic link`
- Tokens expire after 15 minutes, a refresh token mechanism might be desired
- There isn't a mechanism for locking/blocking an account short of deletion. You may want to consider a more nuanced approach here
- Because there are no sessions, a fresh CSRF token is required for **every** state changing request. While this is better for security it will impact performance (effectively doubling the time to submit a form etc). If this is an issue consider moving to a stateful approach.

## BACKEND

### Users and Roles

Spring Security requires **something** to implement `UserDetails` and while it is entirely possible (and quite common) to have your `User` class directly implement `UserDetails` I prefer to keep these concerns seperate and instead pass a `User` into `UserDetailsImpl`. Although this is an added layer of abstraction it allows more versatility in how roles/authorities are applied, and how the various methods required by `UserDetails` are implemented. Similarly a `UserDetailsService` may be overkill and you could likely grab a `User` from a `UserService` and create a `UserDetailsImpl` from that, but this keeps the `auth` domain very focused.
`Role` is also located in `auth`. This is mostly personal prefernce to keep it co-located with things like the `AllowedRoles` annotation.

### JWT

This app uses version `0.11.5` of `jjwt-impl`. I have noticed reasonble frequent breaking changes with this library. Keep it in mind if you're using a more recent version. The docs will guide you on deprecated methods.
`JwtUtils` could rightfully be called `JwtService`. Naming is always awkward, you decide how to proceed. Depending on your Java experience you may not have encountered records

```java
    public record JwtUserInfo(Long userId, String role) {
    }
```

This behaves much like an object literal in JavaScript. Allowing for dot notation such as

```java
    public Long getUserIdFromJwt(String token) {
        return getUserInfoFromJwt(token).userId;
    }

    public String getRoleFromJwt(String token) {
        return getUserInfoFromJwt(token).role;
    }

    public JwtUserInfo getUserInfoFromJwt(String token) {
        Claims claims = getParser().parseClaimsJws(token).getBody();
        return new JwtUserInfo(Long.parseLong(claims.getSubject()), claims.get("role", String.class));
    }

```

The `JwtTokenFilter` (I know the 't' stands for 'token', I should rename). Behaves as you'd expect. Extracting and validating the jwt (calling validate from `JwtUtils`) and then either allowing the request through or causing an exception. However it is worth noting that the filter will accept a token either in a cookie or in an Authorization header

```java
    private String parseJwt(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

```

### Filter Chain

The idea of a **chain** of filters is useful. However if you have a background working with express or flask you might be more comfortable thinking about this as **middleware**. It's worth remembering that filters run **before** the regular request/response cycle and this does come with some gotchas. Everything required by the filter chain is instantiated in the `WebSecurityConfig` (including CORS). This can feel weird, but it works reasonably well. Remember that any `Beans` you set up here can be accessed throughout your application (for example `PasswordEncoder` is used in the seeder). Let's break down the filter chain itself

```java

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Very important. Without this code any errors dispatched will get swallowed up by your filter chain and you'll end up with a 401. You need this to ensure you get 404 on not found routes, 400 on bad requests etc
                .securityMatcher(new NegatedRequestMatcher(new DispatcherTypeRequestMatcher(DispatcherType.ERROR)))
                // Cors needs to be set up in the WebSecurityConfig - check the file for more details
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // we'll talk about CSRF in the next section, but important to not here it's been disabled for auth routes, and itself (although CSRF is disabled for all GET requests anyway)
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("/csrf/csrf-token", "/auth/**"))
                // You can't use controller advice to handle 401 and 403 errors caused by your filter chain. You can see how I created handlers to deal with them. Basically just standarising the JSON responses
                .exceptionHandling(eh -> eh.authenticationEntryPoint(unauthorizedHandler)
                        .accessDeniedHandler(new ForbiddenHandler()))
                // Stateless for no session on the server, a downside is the need to get a fresh CSRF token for every state changing request, but this is better security
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Just required - check the file for more info but this is basically boilerplate
                .authenticationProvider(authenticationProvider())
                // ensure you don't need a token for /auth routes - everything else does
                .authorizeHttpRequests(auths -> auths.requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/csrf/csrf-token").authenticated()
                        .anyRequest().authenticated())
                // login route will use UserNamePasswordAuthentication - this is required to ensure you can do that correctly
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
```

### CSRF

Storing a JWT in a cookie may help prevent XSS attacks, but it can leave us more open to [**Cross Site Request Forgery**](https://youtu.be/vRBihr41JTo?si=Omo0n-o7fsSweSSw)
In this app all state changing requests (POST, PATCH, DELETE etc ) are protected by requring a CSRF token
the `GET /csrf/csrf-token` endpoint adds a Cookie named `XSRF-TOKEN` and returns a response like

```json
{
  "headerName": "X-XSRF-TOKEN",
  "token": "token here",
  "parameterName": "_csrf"
}
```

In order to succesfully access a CSRF protected endpoint a request must both have the `XSRF-TOKEN` cookie and a header named `X-XSRF-TOKEN` and the corresponding token from the **body** of the response. This token only works once and is an additional layer of protection.

### AllowedRoles Annotation

The `@AllowedRoles` annotation is pretty straight forward

```java
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowedRoles {
    Role[] value();
}
```

It simply injects into a method an array of Roles allowed to access that method.
In combination with an **interceptor**

```java
@Component
public class RoleAuthorizationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        AllowedRoles allowedRoles = handlerMethod.getMethodAnnotation(AllowedRoles.class);

        if (allowedRoles == null) {
            return true;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new AuthenticationException();
        }

        Set<String> authorities = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        Role[] roles = allowedRoles.value();
        for (Role allowedRole : roles) {
            if (authorities.contains("ROLE_" + allowedRole)) {
                return true;
            }
        }

        throw new AccessDeniedException("Your role does not have permission to do this");

    }
}
```

We have an easy way to restrict certain endpoints from users e.g

```java
    @AllowedRoles({ Role.ADMIN })
    @GetMapping("/admin-only")
    public String adminRoute() {
        return "Only an admin can see this";
    }

    @AllowedRoles({ Role.ADMIN, Role.COACH })
    @GetMapping("/coach-admin")
    public String coachAdmin() {
        return "Restricted to Admin and Coach";
    }
```

### Example Controller.

Right now this app doesn't **do** much. It's here as a template for getting started with Auth. Feel free to take these ideas and run with them. The example controller is just a basic way of showing how endpoints can be protected.
