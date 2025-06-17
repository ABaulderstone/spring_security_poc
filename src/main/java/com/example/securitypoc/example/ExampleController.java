package com.example.securitypoc.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/example")
public class ExampleController {

    @GetMapping("/protected-route")
    public String getMethodName() {
        return "You can only access this with the right credentials";
    }

}
