package com.tpo.bankjob.model.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import com.tpo.bankjob.model.utils.View;

@Entity
@Table(name = "postulante")
@JsonRootName(value = "postulante")
@JsonView(View.Public.class)
public class PostulanteVO implements UserDetails {
	
	private static final long serialVersionUID = 1125979622368767166L;

	@JsonView(View.Public.class)
	@JsonProperty("id")
	@Column(name = "id")
	private @Id String id;
	
	@JsonView(View.Internal.class)
	@JsonProperty("username")
	@Column(name = "username")
	private String username;
	
	@JsonView(View.Internal.class)
	@JsonProperty("password")
	@Column(name = "password")
	private String password;
	
	@JsonView(View.Public.class)
	@JsonProperty("nombre")
	@Column(name = "nombre")
	private String nombre;
	
	@JsonView(View.Public.class)
	@JsonProperty("apellido")
	@Column(name = "apellido")
	private String apellido;
	
	@JsonView(View.Public.class)
	@Column(name = "canal_notificacion")
	@JsonProperty("canal_notificacion")
	private CanalNotificacionEnum canalNotificacion;
	
	@JsonView(View.Public.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ", timezone="America/Buenos Aires")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@JsonProperty("fecha_nacimiento")
	@Column(name = "fecha_nacimiento")
	private DateTime fechaNacimiento;
	    
	@JsonView(View.Public.class)
	@JsonProperty("skills")
	@Column(name = "skills")	
    @OneToMany(mappedBy = "ownerId")
	@LazyCollection(LazyCollectionOption.FALSE)
    private List<SkillVO> skills;
	
	@JsonView(View.Public.class)
	@JsonProperty("intereses")
	@Column(name = "intereses")	
    @OneToMany(mappedBy = "idPostulante")
	@LazyCollection(LazyCollectionOption.FALSE)
    private List<InteresVO> intereses;
	
	@JsonView(View.Public.class)
	@JsonProperty("postulaciones")
    @OneToMany(mappedBy = "postulante")
    private List<PostulacionVO> postulaciones;
	
	public PostulanteVO() {
		this.postulaciones = new ArrayList<>();
		this.skills = new ArrayList<>();
		this.intereses = new ArrayList<>();
	}
	
	public PostulanteVO(String id, String username,
			String password, String nombre, String apellido, 
			DateTime fechaNacimiento) {
		this();
		this.id = id;
		this.username = username;
		this.password = password;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public DateTime getFechaNacimiento() {
		return fechaNacimiento;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setFechaNacimiento(DateTime fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public List<PostulacionVO> getPostulaciones() {
		return postulaciones;
	}

	public void setPostulaciones(List<PostulacionVO> postulaciones) {
		this.postulaciones = postulaciones;
	}

	public List<SkillVO> getSkills() {
		return skills;
	}

	public void setSkills(List<SkillVO> skills) {
		this.skills = skills;
	}
	
	public CanalNotificacionEnum getCanalNotificacion() {
		return canalNotificacion;
	}

	public void setCanalNotificacion(CanalNotificacionEnum canalNotificacion) {
		this.canalNotificacion = canalNotificacion;
	}

	public void setIntereses(List<InteresVO> intereses) {
		this.intereses = intereses;
	}
	
	@JsonView(View.Internal.class)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>();
	}

	@JsonView(View.Internal.class)
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonView(View.Internal.class)
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonView(View.Internal.class)
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonView(View.Internal.class)
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public List<InteresVO> getIntereses() {
		return this.intereses;
	}

	@JsonView(View.Internal.class)
	public void notificarNovedadesIntereses(List<PublicacionVO> novedadesIntereses) {
		// TODO #ADOO STRATEGY + ADAPTER
		
	}

	@JsonView(View.Internal.class)
	public void addInteres(InteresVO interes) {
		this.intereses.add(interes);
	}	
}
