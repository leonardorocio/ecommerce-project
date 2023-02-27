package com.ecommerce.hardware.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "user")
public class User  {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="user_id")
    private Integer user_id;

    @JsonIgnore
    private String password;

    private String name;

    @Column(unique = true)
    private String email;

    private Date date;

    private String cep;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "orders",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> productList;

    @OneToMany(mappedBy = "user_owner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> comments;

}
