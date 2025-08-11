package com.example.securitypoc.common;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import com.example.securitypoc.auth.jwt.JwtUtils;
import com.example.securitypoc.user.entities.User;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jakarta.transaction.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
@Rollback
public abstract class BaseEndToEndTest {
    @LocalServerPort
    protected int port;

    private JwtUtils jwtUtils;
    private BaseFixture baseFixture;

    @Autowired
    public BaseEndToEndTest(JwtUtils jwtUtils, BaseFixture baseFixture) {
        this.jwtUtils = jwtUtils;
        this.baseFixture = baseFixture;
    }

    public BaseFixture getBaseFixture() {
        return baseFixture;
    }

    protected RequestSpecification anonymousRequest() {
        return new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(port)
                .setContentType(ContentType.JSON)
                .build();
    }

    protected RequestSpecification authenticatedRequest(User user) {
        String jwt = jwtUtils.generateJwt(user);
        System.out.println("*JWT*");
        System.out.println(jwt);
        System.out.println("*JWT*");
        return new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(port)
                .addCookie("jwt", jwt)
                .setContentType(ContentType.JSON)
                .build();
    }

    protected RequestSpecification authenticatedRequestWithCsrf(User user) {
        String jwt = jwtUtils.generateJwt(user);

        // need to repeat this so we can add the JWT again
        RequestSpecification baseSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(port)
                .addCookie("jwt", jwt)
                .setContentType(ContentType.JSON)
                .build();

        Response csrfResponse = fetchCsrfToken(baseSpec);
        String csrfToken = csrfResponse.path("token");
        String csrfCookie = csrfResponse.getCookie("XSRF-TOKEN");

        return new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(port)
                .addCookie("jwt", jwt)
                .addCookie("XSRF-TOKEN", csrfCookie)
                .addHeader("X-XSRF-TOKEN", csrfToken)
                .setContentType(ContentType.JSON)
                .build();
    }

    private Response fetchCsrfToken(RequestSpecification spec) {
        return RestAssured.given()
                .spec(spec)
                .get("/csrf/csrf-token")
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

}
