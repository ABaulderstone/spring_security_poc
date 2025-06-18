package com.example.securitypoc.auth.csrf;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/csrf")
public class CsrfController {
    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(CsrfToken token) {
        return token;
    }

}
