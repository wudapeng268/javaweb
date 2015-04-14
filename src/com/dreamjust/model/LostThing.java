package com.dreamjust.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name="lostthing")
public class LostThing {

	private int id;
	private String name;
	private String description;
	
	private Date datetime;
	private Address address;
	private String color;
	private int userid;
	private int upNum;
	private int downNum;
	
	private int otherThingid;
	
	@Column(name="foundthingid")
	public int getOtherThingid() {
		return otherThingid;
	}

	public void setOtherThingid(int otherThingid) {
		this.otherThingid = otherThingid;
	}

	private String[] allphotos;
	public LostThing(String name, String description,
			Date datetime, Address address, String color,
			int userid) {
		super();
		this.name = name;
		this.description = description;
		this.datetime = datetime;
		this.address = address;
		this.color = color;
		this.userid = userid;
	}
	
	public LostThing() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getUpNum() {
		return upNum;
	}
	public void setUpNum(int upNum) {
		this.upNum = upNum;
	}
	public int getDownNum() {
		return downNum;
	}
	public void setDownNum(int downNum) {
		this.downNum = downNum;
	}
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	@Embedded
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	@Transient
	public String[] getAllphotos() {
		return allphotos;
	}

	public void setAllphotos(String[] allphotos) {
		this.allphotos = allphotos;
	}

	
	
	
	
}
