package com.travelbooking.repository;

import com.travelbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/*
    UserRepository extends JpaRepository
    it has custom method like findByEmail which search user by email.
*/

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
