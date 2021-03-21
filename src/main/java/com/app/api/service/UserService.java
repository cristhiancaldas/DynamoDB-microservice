package com.app.api.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.api.entity.User;
import com.app.api.repository.UserRepository;

@Service
public class UserService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserRepository userRepository;

	public Optional<User> getUserByID(String dni) {
		log.trace("Metodo Service getUserID");
		return userRepository.getUSer(dni);
	}

	public Optional<User> createUser(User user) {
		log.trace("Metodo service createUser");
		userRepository.save(user);
		return Optional.of(user);
	}

	public boolean deleteUser(String dni) {

		log.trace("Metodo Service deleteUser");
		//User userDelete=userRepository.getUSer(dni);
		if (!userRepository.getUSer(dni).isPresent()) {
			return false;
		}
		userRepository.deleteUser(dni);
		return true;
	}

	public List<User> getListUsers() {
		log.trace("Metodo Service getLIstUsers");
		return userRepository.getLstUsers();
	}

}
