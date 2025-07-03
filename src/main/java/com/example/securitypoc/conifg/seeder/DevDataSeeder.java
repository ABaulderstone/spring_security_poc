package com.example.securitypoc.conifg.seeder;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.securitypoc.auth.role.Role;
import com.example.securitypoc.conifg.factory.user.UserFactory;
import com.example.securitypoc.conifg.factory.user.UserFactoryOptions;
import com.example.securitypoc.user.UserRepository;
import com.example.securitypoc.user.entities.User;

@Component()
@Profile("dev")
public class DevDataSeeder implements CommandLineRunner {
    private final UserFactory userFactory;

    public DevDataSeeder(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🌱 Running DevDataSeeder...");
        if (userFactory.repoEmpty()) {
            userFactory.createAndPersist(
                    new UserFactoryOptions()
                            .role(Role.ADMIN)
                            .email("admin@test.com")
                            .rawPassword("admin123"));

            userFactory.createAndPersist(
                    new UserFactoryOptions()
                            .role(Role.COACH)
                            .email("coach1@test.com")
                            .rawPassword("coach123"));

            userFactory.createAndPersist(
                    new UserFactoryOptions()
                            .role(Role.COACH)
                            .email("coach2@test.com")
                            .rawPassword("coach123"));

            userFactory.createAndPersist(
                    new UserFactoryOptions()
                            .role(Role.TALENT)
                            .email("talent@test.com")
                            .rawPassword("talent123"));

            for (int i = 0; i < 10; i++) {
                userFactory.createAndPersist(new UserFactoryOptions().role(Role.STUDENT));
            }
        }

    }

}
