package com.example.securitypoc.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.securitypoc.auth.role.AllowedRoles;
import com.example.securitypoc.auth.role.Role;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

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

    @AllowedRoles({ Role.ADMIN })
    @GetMapping("/admin-only")
    public String getMethodName(@RequestParam String param) {
        return "Only an admin can see this";
    }

}
