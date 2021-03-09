package com.cos.myjpa.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.myjpa.domain.post.Post;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class User {	// User 1 <-> Post N  => OneToMany
	@Id	// PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Table, auto_increment, Sequence
	private Long id;
	private String username;
	private String password;
	private String email;
	@CreationTimestamp	// 자동으로 현재시간이 들어감.
	private LocalDateTime createDate;
	
	
	// 역방향 매핑
	@JsonIgnoreProperties({"user"})	// 양방향 무한 루프를 벗어나기 위해 쓰는데, post로 돌아가서 user에 getter를 하지 말라고 명령하는 것이다.
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)	// 나는 FK의 주인이 아니다. FK는 user 라는 변수명이다.
	private List<Post> post;
	
//	@Transient	// DB Table에 칼럼을 만들지 않고(영향을 주지 않고), 새로운 데이터를 만들어서 가져올때 쓰는 어노테이션 ?
//	private int value;
}
