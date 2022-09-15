package io.baufest.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.baufest.ppmtool.domain.User;
import io.baufest.ppmtool.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptoPasswordEncoder;
	
	public User saveUser (User newUser) {
		newUser.setPassword(bCryptoPasswordEncoder.encode(newUser.getPassword()));
		return userRepository.save(newUser); 
	}

}
