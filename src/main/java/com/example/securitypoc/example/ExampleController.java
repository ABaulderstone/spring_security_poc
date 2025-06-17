package com.example.securitypoc.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/example")
public class ExampleController {

    @GetMapping("/protected-route")
    public String getMethodName() {
        return "You can only access this with the right credentials";
    }

    @PostMapping("/protected-route")
    public String postMethodName() {
        return "This should be protected by CSRF";
    }

}
