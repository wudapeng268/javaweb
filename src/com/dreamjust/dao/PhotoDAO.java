package com.dreamjust.dao;

import com.dreamjust.model.Photo;

public interface PhotoDAO {

	/**
	 * 插入图片
	 * @param photo
	 * @return
	 */
	public int insertPhoto(Photo photo);

	/**
	 * 根据丢失的东西,得到图片
	 * @return
	 */
	public String[] getPhotoByLostThing(int lostThing);
	
	/**
	 * 根据找到的东西,得到图片
	 * @return
	 */
	public String[] getPhotoByFoundThing(int foundThing);
	
}
