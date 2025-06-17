package com.example.securitypoc.conifg.seeder;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.securitypoc.auth.Role;
import com.example.securitypoc.user.UserRepository;
import com.example.securitypoc.user.entities.User;

@Profile("dev")
public class DevDataSeeder implements CommandLineRunner {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public DevDataSeeder(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepo.findByEmail("admin@test.com").isEmpty()) {
            User admin = new User();
            admin.setEmail("admin@example.com");
            admin.setRole(Role.ADMIN);
            admin.setPassword(passwordEncoder.encode("admin123"));

            User student = new User();
            student.setEmail("student@example.com");
            student.setRole(Role.STUDENT);
            student.setPassword(passwordEncoder.encode("student123"));

            userRepo.saveAll(List.of(admin, student));
        }
    }

}
