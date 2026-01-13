package com.astropay.demo.controller.request;

import com.astropay.demo.validation.ValidEmail;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {

    @NotEmpty(message = "name must not be empty or null")
    private String name;
    @ValidEmail(message = "email not valid")
    private String email;


}
