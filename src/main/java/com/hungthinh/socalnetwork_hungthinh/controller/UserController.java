package com.hungthinh.socalnetwork_hungthinh.controller;

import com.hungthinh.socalnetwork_hungthinh.dto.request.LoginRequest;
import com.hungthinh.socalnetwork_hungthinh.dto.request.RegisterRequest;
import com.hungthinh.socalnetwork_hungthinh.dto.response.ApiResponse;
import com.hungthinh.socalnetwork_hungthinh.dto.response.JwtResponse;
import com.hungthinh.socalnetwork_hungthinh.exception.AlreadyExistsException;
import com.hungthinh.socalnetwork_hungthinh.security.jwt.JwtUtils;
import com.hungthinh.socalnetwork_hungthinh.security.user.UPCUserDetails;
import com.hungthinh.socalnetwork_hungthinh.service.user.IUserService;
import com.hungthinh.socalnetwork_hungthinh.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final AuthenticationManager authenticationManager;

    private final IUserService userService;

    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerUser(RegisterRequest request) {
        try{
            String respoEntity = userService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>("success", respoEntity));
        }catch (AlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("success", null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> loginUser(@RequestBody LoginRequest request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            String jwt = jwtUtils.generateTokenForUser(auth);
            UPCUserDetails userDetails = (UPCUserDetails) auth.getPrincipal();

            return ResponseEntity.ok(new ApiResponse<>("Đăng nhập thành công",
                    new JwtResponse(userDetails.getId(), jwt)));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }
    }


}
