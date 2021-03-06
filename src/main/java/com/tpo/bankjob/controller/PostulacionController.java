package com.tpo.bankjob.controller;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.tpo.bankjob.model.Postulacion;
import com.tpo.bankjob.model.utils.View;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/postulacion")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class PostulacionController {
	
	@Autowired
	Postulacion postulacion;
	
	@JsonView(View.Public.class)
	@PostMapping("/add")
	@ResponseBody Postulacion add(
			@RequestBody Postulacion postulacionVO,
			BindingResult bindingResult) {
		return postulacion.add(postulacionVO);
	}

	public List<Postulacion> getPostulaciones() {
		return postulacion.findAll();
	}
}
