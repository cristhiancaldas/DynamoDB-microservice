package com.app.api.controller;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.api.entity.User;
import com.app.api.service.UserService;

@RestController
@RequestMapping("v1")
public class UserController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserService userService;

	@RequestMapping(path = "/user", method = RequestMethod.POST)
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		log.trace("Entering create() with {}", user);
		return userService.createUser(user).map(newCustomer -> new ResponseEntity<>(newCustomer, OK))
				.orElse(new ResponseEntity<>(CONFLICT));
	}

	@RequestMapping(path = "/user/{dni}", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable("dni") String dni) {
		return userService.getUserByID(dni).map(newUserData -> new ResponseEntity<>(newUserData, OK))
				.orElse(new ResponseEntity<>(CONFLICT));
	}

	@RequestMapping(path = "/user/{dni}", method = RequestMethod.DELETE)
	public  ResponseEntity<Void> deleteUser(@PathVariable("dni") String dni) {		
		return userService.deleteUser(dni)?new ResponseEntity<>(HttpStatus.OK):new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(path = "/user", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listUser(){
		List<User> lstUsers=userService.getListUsers();
		if(lstUsers.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(lstUsers,OK);
	}
//
//	@PutMapping("/user/{id}")
//	public String updateUser(@PathVariable("id") String userid, @RequestBody User user) {
//		return userRepository.update(userid, user);
//
//	}
}
