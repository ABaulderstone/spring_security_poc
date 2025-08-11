package com.example.securitypoc.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.example.securitypoc.auth.jwt.JwtUtils;
import com.example.securitypoc.common.BaseEndToEndTest;
import com.example.securitypoc.common.BaseFixture;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.hamcrest.Matchers.*;

import java.util.Arrays;

public class ExampleEndtoEndTest extends BaseEndToEndTest {

    @Autowired
    public ExampleEndtoEndTest(JwtUtils jwtUtils, BaseFixture baseFixture) {
        super(jwtUtils, baseFixture);
    }

    @Test
    void getProtectedRoute_authenticated_succeeds() {
        RestAssured.given()
                .spec(authenticatedRequest(getBaseFixture().adminUser))
                .when()
                .get("/example/protected-route")
                .then()
                .statusCode(200)
                .body(equalTo("You can only access this with the right credentials"));
    }

    @Test
    void getProtectedRoute_unauthenticated_fails() {
        RestAssured.given()
                .spec(anonymousRequest())
                .when()
                .get("/example/protected-route")
                .then()
                .statusCode(401);
    }

    @Test
    void getAdminOnly_asAdmin_succeeds() {
        RestAssured.given()
                .spec(authenticatedRequest(getBaseFixture().adminUser))
                .when()
                .get("/example/admin-only")
                .then()
                .statusCode(200)
                .body(equalTo("Only an admin can see this"));
    }

    @Test
    void getAdminOnly_asCoach_fails() {
        RestAssured.given()
                .spec(authenticatedRequest(getBaseFixture().coachUser))
                .when()
                .get("/example/admin-only")
                .then()
                .statusCode(403);
    }

    @Test
    void postCsrf_withCsrf_succeeds() {
        RestAssured.given()
                .spec(authenticatedRequestWithCsrf(getBaseFixture().adminUser))
                .when()
                .post("/example/protected-route")
                .then()
                .statusCode(200)
                .body(equalTo("This should be protected by CSRF"));
    }

    @Test
    void postCsrf_witouthCsrf_fails() {
        RestAssured.given()
                .spec(authenticatedRequest(getBaseFixture().adminUser))
                .when()
                .post("/example/protected-route")
                .then()
                .statusCode(403);

    }
}
