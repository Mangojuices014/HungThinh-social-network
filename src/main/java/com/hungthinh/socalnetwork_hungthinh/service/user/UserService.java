package com.hungthinh.socalnetwork_hungthinh.service.user;

import com.hungthinh.socalnetwork_hungthinh.dto.request.RegisterRequest;
import com.hungthinh.socalnetwork_hungthinh.exception.AlreadyExistsException;
import com.hungthinh.socalnetwork_hungthinh.model.User;
import com.hungthinh.socalnetwork_hungthinh.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override
    public String register(RegisterRequest request) {
        Optional<User> user = userRepository.findByUsername(request.getUsername());

        if (user.isPresent()) {
            throw new AlreadyExistsException("Nguười dùng đã tồn tại");
        }

        User entity = modelMapper.map(request, User.class);

        return "";
    }
}
