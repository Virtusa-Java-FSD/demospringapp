package com.revature.ecommerceapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ecommerceapp.dto.ProductDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    private ObjectMapper objectMapper = new ObjectMapper();

    record LoginRequest(String username, String password) {

    }
    record LoginResponse(String token) {}

    private String getAccessToken() throws Exception {
        LoginRequest request = new LoginRequest("Jack","Jackma@123");

        MvcResult result = mockMvc.perform(
                post("/auth/login").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isOk()
        ).andReturn();

        LoginResponse res = objectMapper.readValue(
                result.getResponse().getContentAsString(), LoginResponse.class
        );

        return res.token;
    }

    @Test
    void testGetAllPRroducts() throws Exception{
        String accesstoken = getAccessToken();
        mockMvc.perform(
                get("/api/products").header(
                        "Authorization", "Bearer "+ accesstoken
                )
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].productid").value(3));
    }

    @Test
    void testCreateProducts() throws Exception{
        ProductDTO dto = new ProductDTO();
        dto.setName("tablet");
        dto.setColor("gray");
        dto.setPrice(250000);

        String accesstoken = getAccessToken();

        mockMvc.perform(
                post("/api/products").header(
                        "Authorization", "Bearer "+ accesstoken
                ).header("Content-Type","application/json")
                        .content(objectMapper.writeValueAsString(dto))
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.productid").exists());
    }
}
