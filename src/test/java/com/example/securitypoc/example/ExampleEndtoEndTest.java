package com.example.securitypoc.example;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.securitypoc.auth.jwt.JwtUtils;
import com.example.securitypoc.common.BaseEndToEndTest;
import com.example.securitypoc.common.BaseFixture;

import static org.hamcrest.Matchers.*;

public class ExampleEndtoEndTest extends BaseEndToEndTest {

    @Autowired
    public ExampleEndtoEndTest(JwtUtils jwtUtils, BaseFixture baseFixture) {
        super(jwtUtils, baseFixture);
    }

    @Test
    void getProtectedRoute_authenticated_succeeds() {
        spec()
                .withJwt(getBaseFixture().adminUser)
                .build()
                .when()
                .get("/example/protected-route")
                .then()
                .statusCode(200)
                .body(equalTo("You can only access this with the right credentials"));
    }

    @Test
    void getProtectedRoute_unauthenticated_fails() {
        spec()
                .build()
                .when()
                .get("/example/protected-route")
                .then()
                .statusCode(401);
    }

    @Test
    void getAdminOnly_asAdmin_succeeds() {

        spec()
                .withJwt(getBaseFixture().adminUser)
                .build()
                .when()
                .get("/example/admin-only")
                .then()
                .statusCode(200)
                .body(equalTo("Only an admin can see this"));
    }

    @Test
    void getAdminOnly_asCoach_fails() {
        spec()
                .withJwt(getBaseFixture().coachUser)
                .build()
                .when()
                .get("/example/admin-only")
                .then()
                .statusCode(403);
    }

    @Test
    void postCsrf_withCsrf_succeeds() {
        spec()
                .withJwt(getBaseFixture().adminUser)
                .withCsrf()
                .build()
                .when()
                .post("/example/protected-route")
                .then()
                .statusCode(200)
                .body(equalTo("This should be protected by CSRF"));
    }

    @Test
    void postCsrf_witouthCsrf_fails() {
        spec()
                .withJwt(getBaseFixture().adminUser)
                .build()
                .when()
                .post("/example/protected-route")
                .then()
                .statusCode(403);

    }
}
