package com.hungthinh.socalnetwork_hungthinh.service.user;

import com.hungthinh.socalnetwork_hungthinh.dto.request.RegisterRequest;
import com.hungthinh.socalnetwork_hungthinh.exception.AlreadyExistsException;
import com.hungthinh.socalnetwork_hungthinh.model.User;
import com.hungthinh.socalnetwork_hungthinh.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override
    public String register(RegisterRequest request) {
        // Kiểm tra username đã tồn tại chưa
        if (userRepository.existsByUsername((request.getUsername()))){
            throw new AlreadyExistsException("Username đã tồn tại");
        }

        // Kiểm tra email đã tồn tại chưa
        if (userRepository.existsByEmail((request.getEmail()))) {
            throw new AlreadyExistsException("Email đã tồn tại");
        }

        // Nếu không bị trùng, tiếp tục tạo user
        User user = modelMapper.map(request, User.class);
        userRepository.save(user);
        return "Đăng ký thành công";
    }
}
