package com.tpo.bankjob.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tpo.bankjob.model.Interes;

@Repository
public interface InteresRepository extends JpaRepository<Interes,Long> {
	
}
