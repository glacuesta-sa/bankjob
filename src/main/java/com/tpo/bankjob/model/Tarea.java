package com.tpo.bankjob.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import com.tpo.bankjob.model.utils.View;

@Component
@Entity
@Table(name = "tarea")
@JsonRootName(value = "tarea")
@JsonView(View.Public.class)
public class Tarea {
	
	@Id 
	@GeneratedValue
	@Column(name = "id")
	@JsonIgnore
	private Long id;
	
	@ManyToOne
    @JoinColumn(name="id_publicacion", nullable=false)
	@JsonIgnore
	private Publicacion publicacion;
	
	@JsonProperty("name")
	@Column(name = "name")
	private String name;
	
	
	public Tarea() {}
	
	public Tarea(Long id, String name, boolean isMandatory) {
		this();
		this.id = id;
		this.name = name;
	}
	
	public Long getId() {	
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Publicacion getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
	}
	
}
