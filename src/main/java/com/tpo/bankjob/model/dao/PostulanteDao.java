package com.tpo.bankjob.model.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tpo.bankjob.model.Interes;
import com.tpo.bankjob.model.Postulante;
import com.tpo.bankjob.model.exception.AlreadyRegisteredUserException;
import com.tpo.bankjob.model.exception.PostulanteNotFoundException;
import com.tpo.bankjob.model.repository.InteresRepository;
import com.tpo.bankjob.model.repository.PostulanteRepository;
import com.tpo.bankjob.model.repository.SkillRepository;
import com.tpo.bankjob.security.RequestTokenService;
import com.tpo.bankjob.security.UserCrudService;

@Component
public class PostulanteDao {
	
	@Autowired
	UserCrudService users;
	
	@Autowired
	PostulanteRepository postulanteRepository;
	
	@Autowired
	SkillRepository skillRepository;
	
	@Autowired
	InteresRepository interesRepository;

	public String register(Postulante postulanteVO) {
		
		Postulante postulante =  postulanteRepository.findByUsername(postulanteVO.getUsername());
		if(postulante != null) 
			throw new AlreadyRegisteredUserException(postulante.getUsername());
		
		postulanteVO.setId(UUID.randomUUID().toString());
		postulanteVO.getSkills().stream().forEach((s) -> s.setOwnerId(postulanteVO.getId()));
		postulanteVO.getIntereses().stream().forEach((i) -> i.setIdPostulante(postulanteVO.getId()));
		interesRepository.saveAllAndFlush(postulanteVO.getIntereses());
		skillRepository.saveAllAndFlush(postulanteVO.getSkills());
		postulanteRepository.saveAndFlush(postulanteVO);
		users.save(postulanteVO.getId(), postulanteVO);
		return postulanteVO.getId();
	}

	public List<Postulante> findAll() {
		return postulanteRepository.findAll();
	}

	public Optional<Postulante> findById(String id) {
		return postulanteRepository.findById(id);
	}

	public Interes addInteres(Interes interes) {
					
		// el postulante debe existir
		Optional<Postulante> opt = postulanteRepository.findById(
				RequestTokenService.getRequestToken());
		if(!opt.isPresent()) {
			throw new PostulanteNotFoundException(null);
		}
		
		Postulante postulanteVO = opt.get();
		interes.setIdPostulante(RequestTokenService.getRequestToken());
		postulanteVO.getIntereses().add(interes);
		
		interesRepository.saveAndFlush(interes);
		postulanteRepository.saveAndFlush(postulanteVO);
		
		return interes;
	}

}
