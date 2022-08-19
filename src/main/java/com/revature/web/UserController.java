package com.revature.web;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dto.Credentials;
import com.revature.models.User;
import com.revature.service.UserService;

@RestController
@CrossOrigin(origins="*", allowedHeaders="*")
@RequestMapping("/users")
public class UserController {
	
	private UserService userv;

	@Autowired
	public UserController(UserService userv) {
		super();
		this.userv = userv;
	}
	
	@PostMapping("/login")
	public User login(@RequestBody Credentials creds, HttpServletResponse response) {
		
		User user = userv.authenticate(creds);
		
		if (user != null) {
			// return the user as JSON
			return user;
		
		// this isn't being hit because the error is being triggered in the service layer
		} else {
			// 3. otherwise deny and send 401 status
			response.setStatus(401); // 401 is an UNAUTHORIZED status
			return null; // TODO: maybe return User object with ID of 0
		}
	}
	
	@GetMapping // return all users by sending a GET request to http://localhost:5000/api/users
	public ResponseEntity<Set<User>> getAll() {
		return ResponseEntity.ok(userv.findAll());	
	}
	
	@PostMapping("/add") // http://localhost:5000/api/users/add
	public ResponseEntity<User> addUser(@Valid @RequestBody User u) {
		return  ResponseEntity.ok(userv.add(u));
	}

	@GetMapping("/{id}") // allows the client to send the request http://localhost:5000/api/users/2
	public ResponseEntity<User> findUserById(@PathVariable("id") int id) {
		return ResponseEntity.ok(userv.getById(id));
	}
	
	@GetMapping("/{username}") // allows the client to send the request http://localhost:5000/api/users/thehulk
	public ResponseEntity<User> findUserByUsername(@PathVariable("username") String username) {
		return ResponseEntity.ok(userv.getByUsername(username));
	}
	
	@DeleteMapping("/{id}")
	public void removeUser(@PathVariable("id") int id) {
		userv.remove(id);
	}

}
