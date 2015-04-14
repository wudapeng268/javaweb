package com.dreamjust.model;

import javax.persistence.Column;
import javax.persistence.Transient;

public class Address {
	private int provinceid;
	private int cityid;
	private int areaid;
	private String detailAddr;
	private String provinceName;
	private String cityName;
	private String areaName;
	
	@Transient
	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	@Transient
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	@Transient
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Address(int provinceid, int cityid, int areaid,
			String detailAddr) {
		super();
		this.provinceid = provinceid;
		this.cityid = cityid;
		this.areaid = areaid;
		this.detailAddr = detailAddr;
	}

	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Column(name="provinceid")
	public int getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(int provinceid) {
		this.provinceid = provinceid;
	}

	@Column(name="cityid")
	public int getCityid() {
		return cityid;
	}

	public void setCityid(int cityid) {
		this.cityid = cityid;
	}

	@Column(name="areaid")
	public int getAreaid() {
		return areaid;
	}

	public void setAreaid(int areaid) {
		this.areaid = areaid;
	}

	@Column(name="detailaddr")
	public String getDetailAddr() {
		return detailAddr;
	}
	public void setDetailAddr(String detailAddr) {
		this.detailAddr = detailAddr;
	}
	
	
}
