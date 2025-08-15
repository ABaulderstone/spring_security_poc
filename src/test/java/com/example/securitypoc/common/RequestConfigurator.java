package com.example.securitypoc.common;

import com.example.securitypoc.auth.jwt.JwtUtils;
import com.example.securitypoc.user.entities.User;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSender;
import io.restassured.specification.RequestSpecification;

public class RequestConfigurator {

    private final RequestSpecBuilder specBuilder;
    private final JwtUtils jwtUtils;

    public RequestConfigurator(String baseUri, int port, JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
        this.specBuilder = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setPort(port)
                .setContentType(ContentType.JSON);
    }

    public RequestConfigurator withJwt(User user) {
        String jwt = jwtUtils.generateJwt(user);
        specBuilder.addCookie("jwt", jwt);
        return this;
    }

    public RequestConfigurator withCsrf() {
        Response csrfResponse = RestAssured.given()
                .spec(specBuilder.build())
                .get("/csrf/csrf-token")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String csrfToken = csrfResponse.path("token");
        String csrfCookie = csrfResponse.getCookie("XSRF-TOKEN");

        specBuilder.addCookie("XSRF-TOKEN", csrfCookie)
                .addHeader("X-XSRF-TOKEN", csrfToken);

        return this;
    }

    public RequestConfigurator withBody(Object body) {
        specBuilder.setBody(body);
        return this;
    }

    public RequestConfigurator withContentTypeJson() {
        specBuilder.setContentType(ContentType.JSON);
        return this;
    }

    public RequestSpecification build() {
        return RestAssured.given(specBuilder.build());
    }

}
