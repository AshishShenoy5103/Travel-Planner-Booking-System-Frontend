package com.travelbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.travelbooking.model.Profile;
import org.springframework.stereotype.Repository;


/*
    ProfileRepository extends JPARepository
*/

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
