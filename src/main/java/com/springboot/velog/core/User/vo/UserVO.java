package com.springboot.velog.core.User.vo;

import java.sql.Timestamp;

import org.hibernate.annotations.ColumnDefault;

import lombok.Data;

@Data
//@Entity // user 클래스가 Mysql에 테이블이 생성이 된다.
public class UserVO {

	private int uid; 		// seq, auto_increment
	
	private String usrnm;
	
	private String pwd;
	
	private String position; 
	
	private String email;
	
	private String phone;
	
	@ColumnDefault("user")
	private String role; // Enum 권장 (admin, user, manager)
	
	private Timestamp reg_dt;
}
