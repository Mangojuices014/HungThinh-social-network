package com.hungthinh.socalnetwork_hungthinh.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Required name")
    private String fullName;

    @NotBlank(message = "Required email")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Required username")
    private String username;

    @NotBlank(message = "Required password")
    @Length(min = 6, message = "password min is 6 charactor")
    private String password;
}
