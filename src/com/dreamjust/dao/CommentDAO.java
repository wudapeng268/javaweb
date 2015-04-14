package com.dreamjust.dao;

import java.util.List;

import com.dreamjust.model.Comment;
import com.dreamjust.model.FoundThing;
import com.dreamjust.model.LostThing;

public interface CommentDAO {

	/**
	 * 插入评论
	 * @param comment
	 * @return 插入的序号
	 */
	public int insertComment(Comment comment);
	
	/**
	 * 根据丢失的物品id和已找到的物品id来得到所有评论
	 * @param lostId
	 * @param foundId
	 * @return 包含所有评论的集合
	 */
	public List<Comment> getComments(int lostThingid, int foundThingid);

	public List<Comment> getCommentsByUser(int userid);


	
}
