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
}
