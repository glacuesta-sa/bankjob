package com.tpo.bankjob.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import com.tpo.bankjob.model.dao.EmpresaDao;
import com.tpo.bankjob.model.observer.IObservable;
import com.tpo.bankjob.model.observer.IObserver;
import com.tpo.bankjob.model.utils.StrategyFactory;
import com.tpo.bankjob.model.utils.View;
import com.tpo.bankjob.strategy.Notificador;

@Component
@Entity
@Table(name = "empresa")
@JsonRootName(value = "empresa")
@JsonView(View.Public.class)
public class Empresa implements UserDetails, IObserver {

	private static final long serialVersionUID = 4384739614806100984L;
	
	@Autowired
	@Transient
	private EmpresaDao empresaDao;

	@JsonView(View.Public.class)
	@JsonProperty("id")
	@Column(name = "id")
	private @Id String id;
	
	@JsonView(View.Public.class)
	@JsonProperty("razon_social")
	@Column(name = "razon_social")
	private String razonSocial;
	
	@JsonView(View.ExtendedPublic.class)
	@JsonProperty("publicaciones")
	@Column(name = "publicaciones")
	@OneToMany(mappedBy = "empresa",fetch = FetchType.EAGER)
	private List<Publicacion> publicaciones;
	
	@JsonView(View.Internal.class)
	@JsonProperty("username")
	@Column(name = "username")
	private String username;
	
	@JsonView(View.Internal.class)
	@JsonProperty("password")
	@Column(name = "password")
	private String password;
	
	@JsonView(View.Public.class)
	@Column(name = "canal_notificacion")
	@JsonProperty("canal_notificacion")
	private CanalNotificacion canalNotificacion;
	
	public Empresa() {
		this.publicaciones = new ArrayList<>();
		this.canalNotificacion = CanalNotificacion.MAIL;
	}

	public Empresa(String id, String username, String password) {
		this();
		this.id = id;
		this.razonSocial = "";
		this.username = username;
		this.password = password;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public void setPublicaciones(List<Publicacion> publicaciones) {
		this.publicaciones = publicaciones;
	}

	/**
	 * @return the publicaciones
	 */
	public List<Publicacion> getPublicaciones() {
		return publicaciones;
	}
	
	public void addPublicacion(Publicacion publicacion) {
		this.publicaciones.add(publicacion);
	}
	
	public boolean equals(Empresa other) {
		return this.id == other.getId();
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

	public CanalNotificacion getCanalNotificacion() {
		return canalNotificacion;
	}

	public void setCanalNotificacion(CanalNotificacion canalNotificacion) {
		this.canalNotificacion = canalNotificacion;
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
		
	public String register(Empresa empresaVO) {
		return empresaDao.register(empresaVO);
	}

	public Optional<Empresa> get(String id) {
		return empresaDao.findById(id);
	}

	// (#ADOO) STRATEGY & ADAPTER
	@JsonView(View.Internal.class)
	@Override
	public void notificarPostulacion(IObservable observable) {
		
		Notificacion notificacion = new Notificacion("Ha recibido una postulacion nueva "
				+ "en la publicacion \"" + ((Publicacion)observable).getTitulo() + "\"");
	
		Notificador notificador = new Notificador(StrategyFactory.getStrategy(canalNotificacion));		
		notificador.send(notificacion);
	}
}
