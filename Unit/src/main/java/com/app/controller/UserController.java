package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.app.entity.User;
import com.app.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	public UserController(UserService userService) {
        this.userService = userService;
	}

	@GetMapping
	public List<User> getAllUsers() {
		if (userService != null) {
			return userService.getAllUsers();
		} else {
			// Handle the case where userService is null
			throw new IllegalStateException("userService is not initialized");
		}
//		return userService.getAllUsers();
	}

	@GetMapping("/{id}")
	public User getUserById(@PathVariable Long id) {
		return userService.getUserById(id);
	}

	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public User createUser(@RequestBody User user) {
		return userService.createUser(user);
	}

	@PutMapping("/{id}")
	public User updateUser(@PathVariable Long id, @RequestBody User user) {
		return userService.updateUser(id, user);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
	}
}
