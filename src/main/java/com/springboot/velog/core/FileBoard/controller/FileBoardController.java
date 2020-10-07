package com.springboot.velog.core.FileBoard.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.omg.CORBA.portable.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.velog.core.FileBoard.service.FileBoardService;
import com.springboot.velog.core.FileBoard.vo.FileBoardVO;
import com.springboot.velog.core.FileBoard.vo.FileMultipartVO;

@Controller
@RequestMapping("/fileBoard")
public class FileBoardController {
	private final Logger LOGGER = LoggerFactory.getLogger(FileBoardController.class);

	
	@Autowired
	private FileBoardService fbservice;
	
	// 1.목록 조회
	@RequestMapping(value="/list")
	public String fileBoardList (Model model, HttpServletRequest request) {
		
		List<FileBoardVO> testList = new ArrayList<>();
		testList = fbservice.getFileBoardList();
		model.addAttribute("testList", testList);
		return "fileboard/list";
	}
	
	// 2.상세 조회
	@RequestMapping(value="/detail/{b_no}")
	public String fileBoardDetail (@PathVariable("b_no") int b_no, Model model) {
		model.addAttribute("detail", fbservice.fileBoardDetail(b_no));
		// 게시글을 클릭했을 때 업로드한 파일명을 볼 수있도록 db에서 가져오기.
		if(fbservice.fileDetail(b_no) == null) {
			return "fileBoard/detail";
		} else {
			model.addAttribute("file", fbservice.fileDetail(b_no));
			return "fileboard/detail";
		}
	}

	// 3.글 등록
	@RequestMapping(value="/insert")
	public String insertFileBoardForm(@ModelAttribute FileBoardVO fb) {
		return "fileboard/insert";
	}
	//파일 업로드 포함
	@RequestMapping(value="/insertProc")
	public String insertFileBoardProc(@ModelAttribute FileBoardVO fb, @RequestPart MultipartFile files, 
			HttpServletRequest request) throws IllegalStateException, IOException, Exception {
		
		// 첨부파일이 없으면
		if (files.isEmpty()) {
			fbservice.insertFileBoard(fb);
		} else {
			String fileNm = files.getOriginalFilename(); 								 // 사용자 컴에 저장된 파일명 그대로 가져온다.
			String fileNameExtension = FilenameUtils.getExtension(fileNm).toLowerCase(); //확장자
			File destinationFile;  //DB에 저장할 파일 고유명
			String destinationFileName;  
			String fileUrl = "D:\\A0000.개인\\E1000.토이프로젝트\\E1200.스프링부트\\filedown\\"; //절대 경로 설정 해주지않으면 마음애도 들어가버려서 절대경로를 입력해줌.
			
			do { // 우선 실행 후
				//고유명 생성 RandomStringUtils
				destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension;
				destinationFile = new File(fileUrl + destinationFileName);  // 합쳐줌
			} while (destinationFile.exists());
			
			Boolean des = destinationFile.getParentFile().mkdirs(); //디렉토리
			files.transferTo(destinationFile);
			
			fbservice.insertFileBoard(fb);
			
			FileMultipartVO file = new FileMultipartVO();
			file.setB_no(fb.getB_no());
			file.setFilenm(destinationFileName);
			file.setOriginfilenm(fileNm);
			file.setFileurl(fileUrl);
			
			fbservice.insertFile(file);
		}
		return "forward:/fileBoard/list"; //객체 재사용
	}

	// 4.글 수정
	@RequestMapping(value="/update/{b_no}")
	public String updateFileBoardForm(@PathVariable("b_no") int b_no, Model model) {
		model.addAttribute("detail", fbservice.fileBoardDetail(b_no));
		return "fileboard/update";
	}
	@RequestMapping(value="/updateProc")
	public String updateFileBoardProc(@ModelAttribute FileBoardVO fb) {
		fbservice.updateFileBoard(fb);
		int bno = fb.getB_no();
		String b_no = Integer.toString(bno);
		
//		return "redirect:/fileBoard/list";
		return "redirect:/fileBoard/detail/"+b_no;
	}
	
	// 5. 글 삭제
	@RequestMapping(value="/delete/{b_no}")
	public String deleteFileBoard(@PathVariable("b_no") int b_no) {
		fbservice.deleteFileBoard(b_no);
		return "redirect:/fileBoard/list";
	}
	
	// 6. 파일 다운로드
	@RequestMapping(value="/fileDown/{b_no}")
	public void fileDown(@PathVariable("b_no") int b_no, HttpServletRequest request, 
			HttpServletResponse response) throws UnsupportedEncodingException, Exception {
		
		request.setCharacterEncoding("UTF-8");
		FileMultipartVO file = fbservice.fileDetail(b_no);
		
		// 파일 업로드 경로
		try {
			String fileUrl = file.getFileurl();
			LOGGER.info("FileUrl : " + fileUrl);
			fileUrl += "\\";
			String savePath = fileUrl;
			String fileNm = file.getFilenm();
			
			//실제 내보낼 파일명
			String originFileNm = file.getOriginfilenm();
			FileInputStream in = null;
			OutputStream os = null;
			File files = null;
			Boolean skip = false;
			String client = "";
			
			//파일을 읽어 스트림에 담기
			try {
				files = new File(savePath, fileNm);
				in = new FileInputStream(files);
			} catch (FileNotFoundException fe) {
				skip = true;
			}
			
			client = request.getHeader("User-Agent");
			
			//파일 다운로드 헤더 지정
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Description", "HTML Generated Data");
			
			if(!skip) {
				//IE
				if(client.indexOf("MSIE") != -1) {
					response.setHeader("Content-Disposition", "attachment; filename=\""
						+ java.net.URLEncoder.encode(originFileNm, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
				//IE 11이상
				}else if (client.indexOf("Trident") != -1) {
					response.setHeader("Content-Disposition", "attachment; filename=\""
						+ java.net.URLEncoder.encode(originFileNm, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
				// 한글 파일명 처리
				}else {
					response.setHeader("Content-Disposition", "attachment; filename=\"" +
						new String(originFileNm.getBytes("UTF-8"), "ISO8859_1") + "\"");
					response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
				}
				
				response.setHeader("Content-Length", ""+files.length());
				os = response.getOutputStream();
				byte b[] = new byte[(int) files.length()];
				int leng = 0;
				
				while ((leng = in.read(b)) > 0) {
					os.write(b, 0, leng);
				}
			}else {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script> alert('파일을 찾을 수 없습니다.'); history.back(); </script>");
				out.flush(); // flush(): 현재 버퍼에 저장되어 있는 내용을 클라이언트로 전송하고 버퍼를 비운다.
			}
			
			in.close();
			os.close();
		} catch (Exception e) {
			LOGGER.error("Error : " + e.getStackTrace());
		}
		
	}
	
}
