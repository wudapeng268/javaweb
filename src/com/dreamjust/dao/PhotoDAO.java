package com.dreamjust.dao;

import com.dreamjust.model.Photo;

public interface PhotoDAO {

	/**
	 * ����ͼƬ
	 * @param photo
	 * @return
	 */
	public int insertPhoto(Photo photo);

	/**
	 * ���ݶ�ʧ�Ķ���,�õ�ͼƬ
	 * @return
	 */
	public String[] getPhotoByLostThing(int lostThing);
	
	/**
	 * �����ҵ��Ķ���,�õ�ͼƬ
	 * @return
	 */
	public String[] getPhotoByFoundThing(int foundThing);
	
}
