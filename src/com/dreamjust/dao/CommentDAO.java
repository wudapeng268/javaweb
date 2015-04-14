package com.dreamjust.dao;

import java.util.List;

import com.dreamjust.model.Comment;
import com.dreamjust.model.FoundThing;
import com.dreamjust.model.LostThing;

public interface CommentDAO {

	/**
	 * ��������
	 * @param comment
	 * @return ��������
	 */
	public int insertComment(Comment comment);
	
	/**
	 * ���ݶ�ʧ����Ʒid�����ҵ�����Ʒid���õ���������
	 * @param lostId
	 * @param foundId
	 * @return �����������۵ļ���
	 */
	public List<Comment> getComments(int lostThingid, int foundThingid);

	public List<Comment> getCommentsByUser(int userid);


	
}
