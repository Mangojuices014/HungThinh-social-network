package com.hungthinh.socalnetwork_hungthinh.service.user;
import com.hungthinh.socalnetwork_hungthinh.dto.request.LoginRequest;
import com.hungthinh.socalnetwork_hungthinh.dto.request.RegisterRequest;

public interface IUserService {
    String register(RegisterRequest request);
    String login(LoginRequest request);
}
