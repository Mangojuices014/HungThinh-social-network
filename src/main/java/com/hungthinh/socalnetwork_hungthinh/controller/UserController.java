package com.hungthinh.socalnetwork_hungthinh.controller;

import com.hungthinh.socalnetwork_hungthinh.dto.request.RegisterRequest;
import com.hungthinh.socalnetwork_hungthinh.dto.response.ApiResponse;
import com.hungthinh.socalnetwork_hungthinh.exception.AlreadyExistsException;
import com.hungthinh.socalnetwork_hungthinh.service.user.IUserService;
import com.hungthinh.socalnetwork_hungthinh.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final IUserService userService;

    @PostMapping
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

}
