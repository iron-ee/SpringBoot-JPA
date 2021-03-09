package com.cos.myjpa.web.user.dto;

import com.cos.myjpa.domain.user.User;

import lombok.Data;

@Data
public class UserLoginReqDto {
	private String username;
	private String password;
	
	public User toEntity() {	// 지금 필요없음. 어차피 save 할 거 아니고 select 만 할거라서
		return User.builder()
				.username(username)
				.password(password)
				.build();
	}
}
