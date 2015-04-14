package com.dreamjust.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="photo")
public class Photo {

	private int id;
	private int userid;
	private String photoUrl;
	private int lostThingid;
	private int foundThingid;

	public Photo() {
		super();
	}
	
	public Photo( int userid, String photoUrl,
			int lostThingid, int foundThingid) {
		this.userid = userid;
		this.photoUrl = photoUrl;
		this.lostThingid = lostThingid;
		this.foundThingid = foundThingid;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
	@Column(name="userid")
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
//	@ManyToOne(targetEntity=LostThing.class)
	@Column(name="lostid")
	public int getLostThingid() {
		return lostThingid;
	}
	public void setLostThingid(int lostThingid) {
		this.lostThingid = lostThingid;
	}
//	@ManyToOne(targetEntity=FoundThing.class)
	@Column(name="foundid")
	public int getFoundThingid() {
		return foundThingid;
	}
	public void setFoundThingid(int foundThingid) {
		this.foundThingid = foundThingid;
	}
	
	
}
