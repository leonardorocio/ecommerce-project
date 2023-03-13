package com.ecommerce.hardware.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRequestBody {

    Integer id;
    String email;
    String password;

    public PasswordRequestBody(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
