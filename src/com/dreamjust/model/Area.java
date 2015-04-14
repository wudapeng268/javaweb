package com.dreamjust.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="area")
public class Area {

	private int id;
	private int areaID;
	private String areaName;
	private int areaFather;
	public int getAreaFather() {
		return areaFather;
	}

	public void setAreaFather(int areaFather) {
		this.areaFather = areaFather;
	}

	@Id
	public int getAreaID() {
		return areaID;
	}

	public void setAreaID(int areaID) {
		this.areaID = areaID;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
