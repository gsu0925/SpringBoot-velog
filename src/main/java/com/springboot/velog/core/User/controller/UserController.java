package com.springboot.velog.core.User.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.velog.core.User.service.UserService;
import com.springboot.velog.core.User.vo.UserVO;

@Controller
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired  //의존성 주입. 그렇다면, @Inject과 차이점? 
	private UserService usrService;
	
	/*
	@RequestMapping(value="/getUsrList", method = RequestMethod.GET)
	public String getUsrList(Model model) {
		
		model.addAttribute("userList", usrService.getUsrList());
		return "user/userList";
	}*/
	
	@PostMapping("/testjoin")
//	@RequestMapping(value = "/testjoin", method = RequestMethod.POST) 
	public String login(UserVO user) throws Exception { 
		
		System.out.println("uid: " + user.getUid() + ", usernm:" + user.getUsrnm() + ", pwd: " + user.getPwd() + ", email: " + user.getEmail());
		usrService.insertUsr(user);
		
		//model.addAttribute("userVO", new UserVO());
		//return "회원가입 되었습니다.";
		return "user/signin"; 
		
	}
	
	
}
