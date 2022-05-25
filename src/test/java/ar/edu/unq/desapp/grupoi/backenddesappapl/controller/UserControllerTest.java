package ar.edu.unq.desapp.grupoi.backenddesappapl.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.unq.desapp.grupoi.backenddesappapl.dto.RegisterUserDTO;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes = ar.edu.unq.desapp.grupoi.backenddesappapl.SwaggerApiApplication.class)
@AutoConfigureMockMvc
public class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void registerOk() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		
		RegisterUserDTO userDTO =  new RegisterUserDTO("nameTestB", "surnameTestB", "testB@gmail.com", "addressTestB", "123Test#B");
		
		this.mockMvc.perform(post("/user/register")
			.contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userDTO))
            .accept(MediaType.APPLICATION_JSON))
        	.andExpect(status().isOk());
	}
	
	@Test
    public void registerError() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		
		RegisterUserDTO userDTO =  new RegisterUserDTO("nameTestC", "surnameTestC", "testC@gmail.com", "addressTestC", "123TestC");
    	
		this.mockMvc.perform(post("/user/register")
			.contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userDTO))
            .accept(MediaType.APPLICATION_JSON))
        	.andExpect(status().isBadRequest());
    }
	
	@Test
    public void findAll() throws Exception {
    	this.mockMvc.perform(get("/user/getAll")
    		.contentType(MediaType.APPLICATION_JSON))
        	.andExpect(status().isOk());
    }
	
	@Test
    public void findByIdOk() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		
		RegisterUserDTO userDTO =  new RegisterUserDTO("nameTest", "surnameTest", "testFindByOk@gmail.com", "addressTest", "123Test#");
		
		this.mockMvc.perform(post("/user/register")
			.contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userDTO))
            .accept(MediaType.APPLICATION_JSON));
		
    	this.mockMvc.perform(get("/user/get/{userEmail}", "testFindByOk@gmail.com")
			.contentType(MediaType.APPLICATION_JSON))
        	.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("nameTest"));
    }
	
	@Test
    public void findByIdError() throws Exception {    	
    	this.mockMvc.perform(get("/user/get/{userEmail}", "inexistent@hotmail.com")
			.contentType(MediaType.APPLICATION_JSON))
        	.andExpect(status().isBadRequest());
    }
}
