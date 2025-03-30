package com.hungthinh.socalnetwork_hungthinh.service.user;
import com.hungthinh.socalnetwork_hungthinh.dto.request.LoginRequest;
import com.hungthinh.socalnetwork_hungthinh.dto.request.RegisterRequest;
import com.hungthinh.socalnetwork_hungthinh.model.User;

import java.util.Optional;

public interface IUserService {
    String register(RegisterRequest request);
    Optional<User> findByUsername(String username);
}
