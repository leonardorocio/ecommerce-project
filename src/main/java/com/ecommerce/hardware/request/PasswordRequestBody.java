package com.ecommerce.hardware.request;

import lombok.Data;

@Data
public class PasswordRequestBody {

    Integer id;
    String email;
    String password;
}
