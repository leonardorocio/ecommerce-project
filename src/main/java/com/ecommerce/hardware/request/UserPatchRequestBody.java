package com.ecommerce.hardware.request;

import lombok.Data;

import java.util.Date;

@Data
public class UserPatchRequestBody {

    Integer user_id;

    String name;

    Date date;

    String cep;

}
