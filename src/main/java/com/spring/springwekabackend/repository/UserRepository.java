package com.spring.springwekabackend.repository;

import com.spring.springwekabackend.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(value = "SELECT * from users u where u.email = ?1", nativeQuery = true)
    List<User> findByEmail(String email);
}
