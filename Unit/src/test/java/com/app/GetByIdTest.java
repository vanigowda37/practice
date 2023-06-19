package com.app;

import static org.mockito.Mockito.times;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.mockito.MockitoAnnotations;

import com.app.controller.UserController;
import com.app.entity.User;
import com.app.repository.UserRepository;
import com.app.service.UserService;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DbUnitConfiguration(dataSetLoader = FlatXmlDataSetLoader.class)
//@WebMvcTest(UserControllerTest.class)
public class GetByIdTest {

    private UserController userController;

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private UserService userService;

	private List<User> users;

	@BeforeEach
	void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService);
		User user1 = new User(1L, "Pragathi", "Praga@gmail.com");
		User user2 = new User(2L, "Monu", "Monu@gmail.com");
		users = Arrays.asList(user1, user2);
	}

	@Test
	@DatabaseSetup("users.xml")
	@DatabaseTearDown("users.xml")
	public void testGetUserById() throws Exception {
	    // Set up the ID of the user you want to retrieve
	    Long userId = 2L;
	    
	    // Create a user object for the expected result
	    User expectedUser = new User(userId, "Monu", "Monu@gmail.com");
	    
	    // Mock the userService.getUserById() method to return the expected user
	    when(userService.getUserById(userId)).thenReturn(expectedUser);
	    
	    // Perform the GET request to retrieve the user by ID
	    ResultActions resultActions = mockMvc.perform(get("/users/{id}", userId))
	            .andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	            .andExpect(jsonPath("$.id").value(expectedUser.getId()))
	            .andExpect(jsonPath("$.username").value(expectedUser.getUsername()))
	            .andExpect(jsonPath("$.email").value(expectedUser.getEmail()));
	    
	    // Verify that the userService.getUserById() method was called
	    verify(userService, times(1)).getUserById(userId);
	    verifyNoMoreInteractions(userService);
	    
	    // Return the ResultActions to allow further assertions if needed
	    resultActions.andReturn();
	}
	
}
