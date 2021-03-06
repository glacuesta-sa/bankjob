package com.tpo.bankjob.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import com.tpo.bankjob.model.utils.View;

@Component
@Entity
@Table(name = "skill")
@JsonRootName(value = "skill")
@JsonView(View.Public.class)
public class Skill implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2600587232392521869L;

	@Id 
	@GeneratedValue
	@Column(name = "id")
	@JsonIgnore
	private Long id;
	
	@Column(name = "ownerId")
	@JsonIgnore
	private String ownerId;
	
	@JsonProperty("name")
	@Column(name = "name")
	private String name;
	
	@JsonProperty("mandatory")
	@Column(name = "mandatory")
	private boolean isMandatory;
	
	public Skill() {
		this.isMandatory = false;
	}
	
	public Skill(Long id, String name, boolean isMandatory) {
		this();
		this.id = id;
		this.name = name;
		this.isMandatory = isMandatory;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isMandatory() {
		return isMandatory;
	}
	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public Object setOwnerId(String ownerId) {
		return this.ownerId = ownerId;
	}
}
