package com.tpo.bankjob.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public interface UserCrudService {

	UserDetails save(String id, UserDetails user);

	Optional<UserDetails> find(String id);

	Optional<UserDetails> findByUsername(String username);
}