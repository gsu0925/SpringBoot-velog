package com.springboot.velog.core.FileBoard.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileMultipartVO {
	
	// 파일번호
	private int f_no;
	// 게시판 번호화 동기화
	private int b_no;
	// 저장할 때
	private String filenm;
	// 받아올 때 파일 이름
	private String originfilenm;
	// 저장 및 불러온 경로
	private String fileurl;
}
