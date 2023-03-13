package com.ecommerce.hardware.request;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Access;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class UserPostRequestBody {

    @NotNull
    @NotBlank
    String name;

    @NotNull
    @NotBlank
    @Column(unique = true)
    String email;

    @NotNull
    Date date;

    @NotNull
    String cep;

    @NotNull
    String password;

}
