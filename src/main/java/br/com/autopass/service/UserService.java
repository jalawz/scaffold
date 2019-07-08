package br.com.autopass.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.autopass.exceptions.UserNotFoundException;
import br.com.autopass.model.User;
import br.com.autopass.repository.UserRepository;

@Service
public class UserService {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserRepository repository;
	
	@Cacheable(value = "user", key = "#id")
	public User findUser(String id) {
		log.info("Starting to fetch user {}", id);
		Optional<User> user = repository.findById(id);
		return user.orElseThrow(() ->
			new UserNotFoundException(String.format("User: %s not found", id))
		);
	}
	
	@Cacheable(value = "users", key = "'all-users'")
	public List<User> findUsers() {
		return repository.findAll();
	}
	
	@CacheEvict(value = "users", allEntries=true)
	public User insertUser(User user) {
		return repository.save(user);
	}
	
	@CachePut(value = "users", key = "#id")
	public User updateUser(String id, User user) {
		User foundUser = findUser(id);
		user.setId(foundUser.getId());
		return repository.save(user);
	}
	
	@CacheEvict(value = "users", allEntries=true)
	public void removeUser(String id) {
		findUser(id);
		repository.deleteById(id);
	}
}
