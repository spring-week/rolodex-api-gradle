package com.revature.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dto.Credentials;
import com.revature.dto.UserDTO;
import com.revature.models.User;
import com.revature.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping("/users")
public class UserController {

	private static final ModelMapper modelMapper = new ModelMapper();
	private UserService userv;

	@Autowired
	public UserController(UserService userv) {
		super();
		this.userv = userv;
	}

	@PostMapping("/login")
	public User login(@RequestBody Credentials creds, HttpServletResponse response) {

		User user = userv.getByCredentials(creds);

		if (user != null) {
			// return the user as JSON
			return user;

			// this isn't being hit because the error is being triggered in the service
			// layer
		} else {
			// 3. otherwise deny and send 401 status
			response.setStatus(401); // 401 is an UNAUTHORIZED status
			return null; // TODO: maybe return User object with ID of 0
		}
	}

	/**
	 * Return all users by sending a GET request to http://localhost:5000/api/users
	 * http://localhost:5000/api/users/name?=bobthebuilder
	 * 
	 * @param username IF PRESENT, will only return the user associates with that User
	 * @exception If a UserNotFound exception is thrown, the errorhandling aspect will intercept
	 * @return a ResponseEntity with Type ? for WildCard due to the ambiguous
	 *         situation of returning EITHER a set or 1 user.
	 */
	@GetMapping
	public ResponseEntity<?> getUsers(@RequestParam(value = "username", required=false) final String username) {

		// If there is no query parameter for the username, return all users
		if (username == null || username.isEmpty()) {
			return ResponseEntity.ok(userv.getAll());
		} else {
			return ResponseEntity.ok(userv.getByUsername(username));
			// in the case that the UserService's getByUsename() method throws a UserNotFound error,
			// the custom error handling will handle this
		}
	}

	@PostMapping("/add") // http://localhost:5000/api/users/add
	public ResponseEntity<User> addUser(@Valid @RequestBody UserDTO userDto) {
		
		User user = modelMapper.map(userDto, User.class);
		return ResponseEntity.ok(userv.add(user));
	}

	@GetMapping("/{id}") // allows the client to send the request http://localhost:5000/api/users/2
	public ResponseEntity<User> findUserById(@PathVariable("id") int id) {
		return ResponseEntity.ok(userv.getById(id));
	}

	@DeleteMapping("/{id}")
	public void removeUser(@PathVariable("id") int id) {
		userv.remove(id);
	}

}
