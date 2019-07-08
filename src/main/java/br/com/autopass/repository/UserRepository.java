package br.com.autopass.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.autopass.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

}
