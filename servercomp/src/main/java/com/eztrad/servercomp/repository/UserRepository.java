package com.eztrad.servercomp.repository;

import com.eztrad.servercomp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// step - 10 -> create user repository interface to talk with user db
// <User, Long>  -> User Model and it's unique identifier type is Long(id's)
public interface UserRepository extends JpaRepository<User, Long> {
}
