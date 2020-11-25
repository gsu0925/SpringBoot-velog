package com.springboot.velog.core.Login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springboot.velog.core.User.vo.UserVO;

@Controller
@RequestMapping("/login")
public class LoginController {

	@RequestMapping(value = "", method = RequestMethod.GET) 
	public String loginForm(Model model) throws Exception { 
		
		model.addAttribute("userVO", new UserVO());
		return "user/signin"; 
		
	}
	
}
