package io.baufest.ppmtool.repositories;

import org.springframework.data.repository.CrudRepository;

import io.baufest.ppmtool.domain.User;

public interface UserRepository extends CrudRepository<User, Long>{
	User findByUsername(String username);
	User getById(Long id);
}
