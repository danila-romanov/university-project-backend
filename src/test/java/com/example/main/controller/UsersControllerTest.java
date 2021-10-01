package com.example.main.controller;

import com.example.main.dto.UserDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UsersControllerTest {

  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;

  @Test
  void getUsersListOfUsersTest() throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users"))
      .andExpect(status().isOk())
      .andReturn();
    String contentAsString = result.getResponse().getContentAsString();
    List<UserDto> user = objectMapper.readValue(contentAsString, new TypeReference<>() {});
    assertEquals(1, user.size());
  }

}