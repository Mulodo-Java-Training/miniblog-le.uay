package com.mulodo.miniblog.service;

import com.mulodo.miniblog.object.ResponseData;

public interface CommentService {
	
	ResponseData getAllCommentForPost(String access_key, int postId);

	ResponseData deleteComment(String accessKey, int parseInt);

	ResponseData addComment(String accessKey, int postId, String content); 
	
	ResponseData editComment(String accessKey, int commentId, String content);
	
}
