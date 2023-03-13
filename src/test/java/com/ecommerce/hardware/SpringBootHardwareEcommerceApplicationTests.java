package com.ecommerce.hardware;


import com.ecommerce.hardware.models.Product;
import com.ecommerce.hardware.models.User;
import com.ecommerce.hardware.request.OrderRequestBody;
import com.ecommerce.hardware.request.PasswordRequestBody;
import com.ecommerce.hardware.services.ProductService;
import com.ecommerce.hardware.services.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
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
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Random;
import java.util.stream.Collectors;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@Log4j2
class SpringBootHardwareEcommerceApplicationTests {

	private RestTemplate restTemplate = new RestTemplate();
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProductService productService;

	@Test
	void getUsers() throws Exception {
		mockMvc.perform(get("/users")
				.contentType("application/json"))
				.andExpect(status().isOk());
	}

	@Test
	void getUserById() throws Exception {
		 mockMvc.perform(get("/users/{id}", 20)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
	}

	@Test
	void postUser() throws Exception {
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt();
		User user = User.builder()
				.user_id(randomInt)
				.email("gato.boulos" + randomInt + "@gmail.com")
				.name("Gata Bulicas")
				.date(new Date())
				.cep("89233740")
				.build();

		String passwordField = ",\"password\": \"Stringteste\"}";
		String requestBody = objectMapper.writeValueAsString(user).replace("}", passwordField);

		mockMvc.perform(post("/users")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(requestBody))
				.andExpect(status().isCreated());
	}

	@Test
	void updateUser() throws Exception {
		User updatedUser = User.builder()
				.user_id(20)
				.cep("89235890")
				.date(new Date())
				.name("Bartico Liberal")
				.build();

		MvcResult result = mockMvc.perform(patch("/users")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(updatedUser)))
				.andExpect(status().isOk()).andReturn();

		JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
		Assertions.assertEquals(jsonObject.get("cep"), updatedUser.getCep());
	}

	@Test
	void addOrderForUser() throws Exception {
		MvcResult result = mockMvc.perform(patch("/users/order")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(new OrderRequestBody(21, 4))))
				.andExpect(status().isOk()).andReturn();

		JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
		Assertions.assertEquals(jsonObject.get("name"), "Gata Bulicas");

	}

	@Test
	void deleteOrderForUser() throws Exception {
		mockMvc.perform(patch("/users/order/edit")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(new OrderRequestBody(21, 4))))
				.andExpect(status().isOk());
	}

	@Test
	void changePassword() throws Exception {
		PasswordRequestBody passwordRequestBody = new PasswordRequestBody(
				21 , "gatica.bulicas@gmail.com", "funciona");
		mockMvc.perform(patch("/users/change_password")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(passwordRequestBody)))
				.andExpect(status().isOk());
	}

	@Test
	void login() throws Exception {
		PasswordRequestBody passwordRequestBody = new PasswordRequestBody(
				"jwt.token@gmail.com", "string"
		);

		mockMvc.perform(post("/users/login")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(passwordRequestBody)))
				.andExpect(status().isOk());
	}

	@Test
	void postProduct() throws Exception {
		Product product = Product.builder()
				.name("Galica Bulicas")
				.discount(1.0)
				.stock(1)
				.price(0)
				.description("Uma gatica reclamona")
				.product_id(99)
				.build();
		mockMvc.perform(post("/products")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(product)))
				.andExpect(status().isCreated());
	}

	@Test
	void postComment() throws Exception {

	}

}
