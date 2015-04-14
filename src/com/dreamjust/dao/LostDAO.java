package com.dreamjust.dao;

import java.util.List;

import com.dreamjust.model.Address;
import com.dreamjust.model.LostThing;

public interface LostDAO {

	public List<LostThing> getLostThingsByAddressAndColor(
			Address address, String color, String name);

	public LostThing getLostThingById(int id);

	public boolean updateLostThing(LostThing lost);

	public int insertLostThing(LostThing lost);

	public List<LostThing> getLostThingUnmate(int num);
	
	public List<LostThing> getLostThingUnmate(int num,int startid);

	List<LostThing> getLostThingByUserId(int userid);

}
