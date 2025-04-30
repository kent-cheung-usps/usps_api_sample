package com.gundam.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.gundam.controller.GreetingController;

@WebMvcTest(GreetingController.class)
public class GreetingControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
    public void testGreetingGet() throws Exception {
        mockMvc.perform(get("/greeting").param("name", "John"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"content\":\"Hello, John!\"}"));
    }

    @Test
    public void testPostGreeting() throws Exception {
        String requestBody = "{\"name\":\"Jane\"}";
        mockMvc.perform(post("/PostGreeting")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":2,\"content\":\"Hello, Jane!\"}"));
    }

    @Test
    public void testPostParam() throws Exception {
        mockMvc.perform(post("/PostParam")
                .param("param1", "Alice")
                .param("param2", "Bob"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello Alice. Let's Do IT!!!! Bob"));
    }
}