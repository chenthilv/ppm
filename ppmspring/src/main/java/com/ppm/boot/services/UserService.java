package com.ppm.boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ppm.boot.domain.User;
import com.ppm.boot.exceptions.UsernameAlreadyExistsException;
import com.ppm.boot.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User saveUser(User user) {
		try {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			return repository.save(user);
		}catch(Exception e) {
			throw new UsernameAlreadyExistsException("Username "+user.getUsername()+" already exists.");
		}
	}
}
