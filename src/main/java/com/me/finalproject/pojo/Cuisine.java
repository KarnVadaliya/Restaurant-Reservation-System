package com.me.finalproject.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Cuisine {
	
	@Id
	@Column(name="CuisineID", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long cuisineId;
	
	@Basic
	@Column(name="CuisineName")
	private String cuisineName;
	
	@ManyToMany(mappedBy="cuisines")
	private Set<Restaurant> restaurants = new HashSet<Restaurant>();
	
	public Cuisine() {
		
	}

	public long getCuisineId() {
		return cuisineId;
	}

	public void setCuisineId(long cuisineId) {
		this.cuisineId = cuisineId;
	}

	public String getCuisineName() {
		return cuisineName;
	}

	public void setCuisineName(String cuisineName) {
		this.cuisineName = cuisineName;
	}

	public Set<Restaurant> getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(Set<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}
	
	
	
	
	
}
