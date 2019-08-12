package com.webApp;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Fox {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
//	@Column(name="id", nullable = false, updatable = false)		//column atrribute specs
	private Long id;
	private String species;
	private String name;
	private String url;
	
	public Fox() {
	}
	
	public Fox(String species, String name, String url) {
		this.species = species;
		this.name = name;
		this.url = url;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
}
