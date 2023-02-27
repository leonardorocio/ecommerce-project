package com.ecommerce.hardware.request;

import lombok.Data;

import java.util.Date;

@Data
public class UserPutRequestBody {

    Integer user_id;

    String name;

    Date date;

    String cep;

}
