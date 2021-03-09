package com.cos.myjpa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.myjpa.domain.user.User;
import com.cos.myjpa.domain.user.UserRepository;
import com.cos.myjpa.web.user.dto.UserJoinReqDto;
import com.cos.myjpa.web.user.dto.UserLoginReqDto;
import com.cos.myjpa.web.user.dto.UserRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;

	@Transactional(readOnly = true)	// 변경감지 안함.
	public List<UserRespDto> 전체찾기() {
		List<User> usersEntity = userRepository.findAll();
		
		List<UserRespDto> userRespDtos = new ArrayList<>();
		for (User user : usersEntity) {
			userRespDtos.add(new UserRespDto(user));
		}
		
//		List<UserRespDto> userRespDtos = new ArrayList<>();
//		usersEntity.stream().forEach((u)->{
//			userRespDtos.add(new UserRespDto(u));
//		});
		
//		List<UserRespDto> userRespDtos = usersEntity.stream().map((u)->{
//			return new UserRespDto(u);
//		}).collect(Collectors.toList());
		
		return userRespDtos;
	}
	
	@Transactional(readOnly = true)	// 변경감지 안함.
	public UserRespDto 한건찾기(Long id) {
		User userEntity = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("id를 찾을 수 없습니다.");
		});
		UserRespDto userRespDto = new UserRespDto(userEntity);
		
		return userRespDto;
	}
	
	@Transactional(readOnly = true)	// 변경감지 안함.
	public User 프로파일(Long id) {
		User userEntity = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("id를 찾을 수 없습니다.");
		});
		return userEntity;
	}
	
						// => 한명이 DB에 write하고 있을 때, 다른 한명이 write 하지 못하게 락을 건다 => 동시접근을 막는다. /  회원가입이 두개가 동시에 들어왔는데 하나라도 실패하면 다시 롤백시켜준다.
	@Transactional		// 		ex) 친구에게 돈을 송금했을 때, 친구의 계좌도 업데이트 되야하고 내 계좌도 업데이트 되야하는데, 둘 중 하나라도 실패하면 롤백 해준다.
	public User 회원가입(UserJoinReqDto userJoinReqDto) {
		User userEntity = userRepository.save(userJoinReqDto.toEntity());
		return userEntity;
	}
	
	@Transactional(readOnly = true)	// 변경감지 안함.
	public User 로그인(UserLoginReqDto userLoginReqDto) {
		User userEntity = userRepository.findByUsernameAndPassword(userLoginReqDto.getUsername(), userLoginReqDto.getPassword());
		return userEntity;
	}
	
	
}
