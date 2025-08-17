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

    protected RequestConfigurator spec() {
        return new RequestConfigurator("http://localhost", port, jwtUtils);
    }
}
