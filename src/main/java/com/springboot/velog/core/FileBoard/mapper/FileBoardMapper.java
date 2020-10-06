package com.springboot.velog.core.FileBoard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.springboot.velog.core.FileBoard.vo.FileBoardVO;
import com.springboot.velog.core.FileBoard.vo.FileMultipartVO;

@Mapper
public interface FileBoardMapper {

	List<FileBoardVO> getFileBoardList(); //게시글 목록 출력
	FileBoardVO fileBoardDetail(int b_no); //게시글 세부내용
	int insertFileBoard(FileBoardVO fileBoard); //글 생성
	int updateFileBoard(FileBoardVO fileBoard); //글 수정
	int deleteFileBoard(int b_no); // 게시글 삭제
	
	int insertFile(FileMultipartVO file); 	  //파일 업로드
	FileMultipartVO fileDetail(int b_no);	  //다운로드 메서드
}
