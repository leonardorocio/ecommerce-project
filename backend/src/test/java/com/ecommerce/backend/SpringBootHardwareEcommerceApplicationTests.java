package com.ecommerce.backend;


import com.ecommerce.backend.models.Product;
import com.ecommerce.backend.models.ProductCategory;
import com.ecommerce.backend.models.User;
import com.ecommerce.backend.payload.OrderRequestBody;
import com.ecommerce.backend.payload.PasswordRequestBody;
import com.ecommerce.backend.services.ProductService;
import com.ecommerce.backend.services.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;
import java.util.Random;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@Log4j2
class SpringBootHardwareEcommerceApplicationTests {


}
