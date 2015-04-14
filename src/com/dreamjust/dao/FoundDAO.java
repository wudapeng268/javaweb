package com.dreamjust.dao;

import java.util.List;

import com.dreamjust.model.Address;
import com.dreamjust.model.FoundThing;
import com.dreamjust.model.LostThing;

public interface FoundDAO {

	public List<FoundThing> getFoundThingsByAddressAndColor(
			Address address, String color, String name);

	public FoundThing getFoundThingById(int id);

	public boolean updateFoundThing(FoundThing found);

	public int insertFoundThing(FoundThing found);

	public List<FoundThing> getFoundThingUnmate(int num);

	List<FoundThing> getFoundThingByUserId(int userid);

	List<FoundThing> getFoundThingUnmate(int num,
			int startid);


}
