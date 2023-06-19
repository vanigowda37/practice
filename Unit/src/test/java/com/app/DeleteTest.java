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
public class DeleteTest {

    private UserController userController;

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private UserService userService;
	
//	@Mock
//	private UserRepository userRepository;

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
	public void testDeleteUser() throws Exception {
	    Long userId = 1L;

	    ResultActions resultActions = mockMvc.perform(delete("/users/{id}", userId))
	            .andExpect(status().isNoContent());

	    verify(userService, times(1)).deleteUser(Mockito.eq(userId));
	    verifyNoMoreInteractions(userService);

	    resultActions.andReturn();
	}

}
