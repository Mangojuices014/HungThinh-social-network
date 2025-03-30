package com.hungthinh.socalnetwork_hungthinh.service.user;

import com.hungthinh.socalnetwork_hungthinh.dto.request.LoginRequest;
import com.hungthinh.socalnetwork_hungthinh.dto.request.RegisterRequest;
import com.hungthinh.socalnetwork_hungthinh.exception.AlreadyExistsException;
import com.hungthinh.socalnetwork_hungthinh.model.Role;
import com.hungthinh.socalnetwork_hungthinh.model.User;
import com.hungthinh.socalnetwork_hungthinh.repository.RoleRepository;
import com.hungthinh.socalnetwork_hungthinh.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;


    @Override
    public String register(RegisterRequest request) {
        // Kiểm tra username đã tồn tại chưa
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AlreadyExistsException("Username đã tồn tại");
        }

        // Kiểm tra email đã tồn tại chưa
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException("Email đã tồn tại");
        }

        // Tạo user
        User user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Lấy Role từ database
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // Gán Role vào User
        user.getRoles().add(userRole);

        // Lưu User vào database
        userRepository.save(user);

        return "Đăng ký thành công";
    }

    @Override
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

}
