package com.mulodo.miniblog.service;

import com.mulodo.miniblog.object.ResponseData;

public interface PostService {

	ResponseData getAllPost(String accessKey, int pageNum, String description);

	ResponseData addPost(String accessKey, String title, String content);

	ResponseData deletePost(String accessKey, int postId);
	
	ResponseData editPost(String accessKey, int postId, String title, String content, int status);
	
	ResponseData activeDeactivePost(String accessKey, int postId, int status);

	ResponseData getAllPostForUser(String accessKey, int pageNum, int userId, String description);
	
	ResponseData getPostInfor(String accessKey, int postId);

}
