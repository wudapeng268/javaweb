package com.dreamjust.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="comment")
public class Comment {

	private int id;
	private Date datetime;
	private int foundThingid;
	private int lostUserid;
	private int foundUserid;
	private String message;
	private int lostThingid;
	private String lostUsername;
	private String foundUsername;
	private String lostUserImg;
	private String foundUserImg;
	
	

	@Transient
	public String getLostUserImg() {
		return lostUserImg;
	}

	public void setLostUserImg(String lostUserImg) {
		this.lostUserImg = lostUserImg;
	}

	@Transient
	public String getFoundUserImg() {
		return foundUserImg;
	}

	public void setFoundUserImg(String foundUserImg) {
		this.foundUserImg = foundUserImg;
	}

	public Comment(Date datetime, int foundThingid,
			int lostUserid, int foundUserid,
			String message, int lostThingid) {
		super();
		this.datetime = datetime;
		this.foundThingid = foundThingid;
		this.lostUserid = lostUserid;
		this.foundUserid = foundUserid;
		this.message = message;
		this.lostThingid = lostThingid;
	}

	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}



	

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

	@Column(name="lostuserid")
	public int getLostUserid() {
		return lostUserid;
	}

	public void setLostUserid(int lostUserid) {
		this.lostUserid = lostUserid;
	}

	@Column(name="founduserid")
	public int getFoundUserid() {
		return foundUserid;
	}

	public void setFoundUserid(int foundUserid) {
		this.foundUserid = foundUserid;
	}

	@Column(name="lostid")
	public int getLostThingid() {
		return lostThingid;
	}

	public void setLostThingid(int lostThingid) {
		this.lostThingid = lostThingid;
	}

	@Column(name="foundid")
	public int getFoundThingid() {
		return foundThingid;
	}

	public void setFoundThingid(int foundThingid) {
		this.foundThingid = foundThingid;
	}

	@Transient
	public String getLostUsername() {
		return lostUsername;
	}

	public void setLostUsername(String lostUsername) {
		this.lostUsername = lostUsername;
	}

	@Transient
	public String getFoundUsername() {
		return foundUsername;
	}

	public void setFoundUsername(String foundUsername) {
		this.foundUsername = foundUsername;
	}
	
	

}
