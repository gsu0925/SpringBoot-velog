package com.springboot.velog.core.User.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.springboot.velog.core.User.vo.UserVO;

@Mapper
//@Repository
public interface UserMapper {

	//DAO 
	// bean으로 등록이 되는가? 스프링 IOC에서 객체로 가지고 있나?
	public List<UserVO> getUsrList();
	
	public UserVO getUsrInfo(String uid) throws Exception;

	public int insertUsr(UserVO userVO) throws Exception;

	public int updateUsr(UserVO userVO) throws Exception;

	public int deleteUsr(String uid) throws Exception;

}
