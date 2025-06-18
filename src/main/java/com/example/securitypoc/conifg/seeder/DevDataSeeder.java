package com.example.securitypoc.conifg.seeder;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.securitypoc.auth.role.Role;
import com.example.securitypoc.user.UserRepository;
import com.example.securitypoc.user.entities.User;

@Component()
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
        System.out.println("🌱 Running DevDataSeeder...");
        if (userRepo.findByEmail("admin@test.com").isEmpty()) {
            User admin = new User();
            admin.setEmail("admin@test.com");
            admin.setRole(Role.ADMIN);
            admin.setPassword(passwordEncoder.encode("admin123"));

            User student = new User();
            student.setEmail("student@test.com");
            student.setRole(Role.STUDENT);
            student.setPassword(passwordEncoder.encode("student123"));

            userRepo.saveAll(List.of(admin, student));
        }
    }

}
