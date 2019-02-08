package edu.neu.cs4500.services;

import edu.neu.cs4500.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import edu.neu.cs4500.repositories.UserRepository;


import java.util.List;

/**
 * Created by Michael Goodnow on 2019-01-23.
 */

@RestController
public class UserService {
	@Autowired
	UserRepository userRepository;



	@GetMapping("/api/user")
	public List<User> findAllUsers() {

		return userRepository.findAllUsers();
	}

	@GetMapping("/api/user/{userId}")
	public User findUserById(
			@PathVariable("userId") Integer id) {
		return userRepository.findUserById(id);
	}

	@GetMapping("api/user/{username}")
	public User findUserByName(
					@PathVariable("username") String name)
	{
		return userRepository.findByUsername(name);
	}

	@PostMapping("api/user")
	public User createUser(
					@RequestBody User newUser
	) {
		return userRepository.save(newUser);
	}

	@PutMapping("api/user/{userId}")
	public User updateUser(
					@PathVariable("userId") Integer id,
					@RequestBody User updateUser
	)
	{
		User find = userRepository.findUserById(id);
		find.setId(updateUser.getId());
		find.setAnswers(updateUser.getAnswers());
		find.setFirstName(updateUser.getFirstName());
		find.setLastName(updateUser.getLastName());
		find.setPassword(updateUser.getPassword());
		find.setRole(updateUser.getRole());
		find.setUsername(updateUser.getUsername());
		return find;
	}

	@DeleteMapping("api/user/{userId}")
	public void removeAUser(
					@PathVariable("userID") Integer id
	) {
		userRepository.deleteById(id);
	}
}
