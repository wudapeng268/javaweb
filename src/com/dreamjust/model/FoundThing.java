package com.dreamjust.model;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;



@Entity
@Table(name="foundthing")
public class FoundThing {

	private int id;
	private String name;
	private String description;
	
	private Date datetime;
	private Address address;
	private String color;
	private int userid;
	
	private int otherThingid;
	
	@Column(name="lostthingid")
	public int getOtherThingid() {
		return otherThingid;
	}
	public void setOtherThingid(int otherThingid) {
		this.otherThingid = otherThingid;
	}

	private String[] allphotos;
	
	public FoundThing() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FoundThing(String name, String description,
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Transient
	public String[] getAllphotos() {
		return allphotos;
	}
	public void setAllphotos(String[] allphotos) {
		this.allphotos = allphotos;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof FoundThing) {
			FoundThing foundThing=(FoundThing) obj;
			if (foundThing.getId()==this.id) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return id;
	}
	@Override
	public String toString() {
		return "FoundThing [id=" + id + ", name=" + name
				+ ", description=" + description
				+ ", datetime=" + datetime + ", address="
				+ address + ", color=" + color
				+ ", userid=" + userid + ", otherThingid="
				+ otherThingid + ", allphotos="
				+ Arrays.toString(allphotos) + "]";
	}
	
	
}
