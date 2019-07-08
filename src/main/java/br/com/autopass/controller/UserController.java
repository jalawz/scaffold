package br.com.autopass.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.autopass.model.User;
import br.com.autopass.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/users")
@Api(value = "User Controller")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "Retrieve a list of users")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Successful retrieved List of Users"),
		@ApiResponse(code = 500, message = "Internal server error")
	})
	@GetMapping
	public ResponseEntity<List<User>> findAllUsers() {		
		List<User> users = userService.findUsers();
		return ResponseEntity.ok(users);
	}
	
	@ApiOperation(value = "Insert a new User")
	@ApiResponses(value = {
		@ApiResponse(code = 201, message = "Successful Created"),
		@ApiResponse(code = 400, message = "Bad user request"),
		@ApiResponse(code = 500, message = "Internal server error")
	})
	@PostMapping
	public ResponseEntity<Void> insertUser(@ApiParam(value = "User object to insert", required = true) @Valid @RequestBody User user) {
		User savedUser = userService.insertUser(user);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@ApiOperation(value = "Retrieve a User by id")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Successful retrieved User"),
		@ApiResponse(code = 404, message = "User not found"),
		@ApiResponse(code = 500, message = "Internal server error")
	})
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@ApiParam(value = "User id", required = true) @PathVariable String id) {
		User foundUser = userService.findUser(id);
		return ResponseEntity.ok(foundUser);
	}
	
	@ApiOperation(value = "Update a User")
	@ApiResponses(value = {
		@ApiResponse(code = 204, message = "Successful Updated User"),
		@ApiResponse(code = 400, message = "Bad user request"),
		@ApiResponse(code = 404, message = "User not found"),
		@ApiResponse(code = 500, message = "Internal server error")
	})
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateUser(
			@ApiParam(value = "User id", required = true) @PathVariable String id, 
			@ApiParam(value = "User object to update") @Valid @RequestBody User user) {
		userService.updateUser(id, user);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Delete a User by id")
	@ApiResponses(value = {
		@ApiResponse(code = 204, message = "Successful Deleted User"),
		@ApiResponse(code = 404, message = "User not found"),
		@ApiResponse(code = 500, message = "Internal server error")
	})
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteUserById(@ApiParam(value = "User id", required = true) @PathVariable String id) {
		userService.removeUser(id);
		return ResponseEntity.noContent().build();
	}
}
