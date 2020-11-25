package com.springboot.velog.core.User.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.velog.core.User.mapper.UserMapper;
import com.springboot.velog.core.User.vo.UserVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	@Autowired
	UserMapper usrMapper;
	
	public List<UserVO> getUsrList() {
		return usrMapper.getUsrList();
	}
	
	public UserVO getUsrInfo(String uid) throws Exception {
		return usrMapper.getUsrInfo(uid);
	}
	
	public int insertUsr(UserVO userVO) throws Exception {
		return usrMapper.insertUsr(userVO);
	}
	
	public int updateUsr(UserVO userVO) throws Exception {
		return usrMapper.updateUsr(userVO);
	}
	
	public int deleteUsr(String uid) throws Exception {
		return usrMapper.deleteUsr(uid);
	}
	
}
