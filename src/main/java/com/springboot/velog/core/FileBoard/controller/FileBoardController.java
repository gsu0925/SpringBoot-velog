package com.springboot.velog.core.FileBoard.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springboot.velog.core.FileBoard.service.FileBoardService;
import com.springboot.velog.core.FileBoard.vo.FileBoardVO;

@Controller
@RequestMapping("/fileBoard")
public class FileBoardController {

	@Autowired
	private FileBoardService fbservice;
	
	// 목록 조회
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String fileBoardList (Model model, HttpServletRequest request) {
		
		List<FileBoardVO> testList = new ArrayList<>();
		testList = fbservice.getFileBoardList();
		model.addAttribute("testList", testList);
		return "fileboard/list";
	}
	
	// 상세 조회
	@RequestMapping(value="/detail/{b_no}", method=RequestMethod.GET)
	public String fileBoardDetail (@PathVariable("b_no") int b_no, Model model) {
		model.addAttribute("detail", fbservice.fileBoardDetail(b_no));
		return "fileboard/detail";
	}

	//글 등록
	@RequestMapping(value="/add")
	public String insertFileBoardForm(@ModelAttribute FileBoardVO fb) {
		return "fileboard/insert";
	}
	@RequestMapping(value="/addProc", method=RequestMethod.POST)
	public String insertFileBoardProc(@ModelAttribute FileBoardVO fb, HttpServletRequest request) {
		fbservice.insertFileBoard(fb);
		return "forward:/fileboard/list"; //객체 재사용
	}

	// 글 수정
	@RequestMapping(value="/update/{b_no}")
	public String updateFileBoardForm(@PathVariable("b_no") int b_no, Model model) {
		model.addAttribute("detail", fbservice.fileBoardDetail(b_no));
		return "fileboard/update";
	}
	@RequestMapping(value="/updateProc", method=RequestMethod.PUT)
	public String updateFileBoardProc(@ModelAttribute FileBoardVO fb) {
		fbservice.updateFileBoard(fb);
		int bno = fb.getB_no();
		String b_no = Integer.toString(bno);
		
		return "redirect:/fileboard/detail/"+b_no;
	}
	
	// 글 삭제
	@RequestMapping(value="/delete/{b_no}", method=RequestMethod.POST)
	public String deleteFileBoard(@PathVariable("b_no") int b_no) {
		fbservice.deleteFileBoard(b_no);
		return "redirect:/fileboard/list";
	}
}
