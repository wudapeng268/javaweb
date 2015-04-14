package com.dreamjust.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="city")
public class City {

	private int id;
	private int cityID;
	private String cityName;
	private int cityFather;
	public int getCityFather() {
		return cityFather;
	}
	public void setCityFather(int cityFather) {
		this.cityFather = cityFather;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Id
	public int getCityID() {
		return cityID;
	}
	public void setCityID(int cityID) {
		this.cityID = cityID;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}


}
