package com.tickets.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tickets.entity.User;

/** 
 * <h1>This repository class is used for user entity related interactions</h1>  
 * @author  Joby Chacko 
 * @version 1.0 
 * @since   2020-04-04
 */
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUserName(String userName);
	
}
