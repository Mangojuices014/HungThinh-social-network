package com.hungthinh.socalnetwork_hungthinh.util.dataDefault;

import com.hungthinh.socalnetwork_hungthinh.model.Role;
import com.hungthinh.socalnetwork_hungthinh.model.User;
import com.hungthinh.socalnetwork_hungthinh.repository.RoleRepository;
import com.hungthinh.socalnetwork_hungthinh.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Component
public class DataSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.save(new Role("ROLE_ADMIN"));
            Role userRole = roleRepository.save(new Role("ROLE_USER"));

            User hoainam10th = userRepository.save(new User("Phan Hoai Phong", "hphong", passwordEncoder.encode("1234"), Instant.now()));
            User ubuntu = userRepository.save(new User("Linux Phan", "linux", passwordEncoder.encode("1234"), Instant.now()));
            User admin = userRepository.save(new User("Administrator", "admin", passwordEncoder.encode("1234"), Instant.now()));

            hoainam10th.getRoles().add(userRole);
            ubuntu.getRoles().add(userRole);
            admin.getRoles().add(userRole);
            admin.getRoles().add(adminRole);

            userRepository.save(hoainam10th);
            userRepository.save(ubuntu);
            userRepository.save(admin);
        }
    }
}

