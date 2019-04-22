package edu.neu.cs4500.services;

import edu.neu.cs4500.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import edu.neu.cs4500.repositories.UserRepository;


import java.util.List;

/**
 * Created by Michael Goodnow on 2019-01-23.
 */

@RestController
@CrossOrigin(origins="*")
public class UserService {
	@Autowired
	UserRepository userRepository;

	@GetMapping("/api/users")
	public List<User> findAllUser() {
		return (List<User>) userRepository.findAll();
	}

	@GetMapping("/api/users/{userId}")
	public User findUserById(
			@PathVariable("userId") Integer userId) {
		return userRepository.findById(userId).get();
	}
	
	@PostMapping("/api/login")
	public User login(@RequestBody User credentials, HttpSession session) {
		
	}
}
