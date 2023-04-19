package com.ecommerce.backend.payload;

import lombok.Data;

import java.util.Date;

@Data
public class UserPatchRequestBody {

    String name;

    Date date;

    String cep;

}
