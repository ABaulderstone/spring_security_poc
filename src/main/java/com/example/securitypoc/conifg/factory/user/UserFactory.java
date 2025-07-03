package com.example.securitypoc.conifg.factory.user;

import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.securitypoc.auth.role.Role;
import com.example.securitypoc.conifg.factory.BaseFactory;
import com.example.securitypoc.user.UserRepository;
import com.example.securitypoc.user.entities.User;

@Component
@Profile({ "dev", "test" })
public class UserFactory extends BaseFactory {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserFactory(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(UserFactoryOptions options) {
        User user = new User();
        user.setRole(options.role != null ? options.role : Role.CANDIDTATE);
        user.setEmail(options.email != null ? options.email : getUniqueEmail());
        user.setPassword(passwordEncoder.encode(options.rawPassword != null ? options.rawPassword : "password123"));
        return user;
    }

    public boolean repoEmpty() {
        return userRepo.count() == 0;
    }

    public User create() {
        return create(new UserFactoryOptions());
    }

    public User createAndPersist(UserFactoryOptions options) {
        User user = create(options);
        return userRepo.save(user);
    }

    public User createAndPersist() {
        User user = create();
        return userRepo.save(user);
    }

    private String getUniqueEmail() {
        String[] emailArr = faker().internet().emailAddress().split("@");
        return String.format("%s_%s@%s", emailArr[0], incrementAndGet(), emailArr[1]);

    }

}
