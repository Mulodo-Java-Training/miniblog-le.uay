package com.mulodo.miniblog.constraints;

public enum ConstraintsPostError {

	CODE_2500(2500, "Have some thing error--Try again later."),
	CODE_2501(2501, "Post is  invalid."),
	CODE_2502(2502, "Title is required."),
	CODE_2503(2503, "Content is required."),
	CODE_2504(2504, "Invalid title - Less than 150 character."),
	CODE_2505(2505, " Invalid content - Less than 3000 character."),
	CODE_2506(2506, "Author is required."),
	CODE_2507(2507, "Status is invalid format -- Must 0 or 1."),
	CODE_2508(2508, "Post id is invalid format -- Must 0 or 1."),
	CODE_2509(2509, "You don't have permission on this post."),
	CODE_2510(2510, "Page number is invalid number.");
	
	private int key;
	private String value;
	
	ConstraintsPostError(int key, String value ) {
		this.key = key;
		this.value = value;
	}

	public int getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
}
