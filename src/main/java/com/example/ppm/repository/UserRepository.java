package com.example.ppm.repository;

import com.example.ppm.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    User findUserByUsername(String username);
    User getById( Long id);
}
