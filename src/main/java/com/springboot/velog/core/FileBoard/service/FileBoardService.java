package com.springboot.velog.core.FileBoard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.velog.core.FileBoard.mapper.FileBoardMapper;
import com.springboot.velog.core.FileBoard.vo.FileBoardVO;
import com.springboot.velog.core.FileBoard.vo.FileMultipartVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileBoardService {

	@Autowired
	FileBoardMapper fileBoardMapper;
	
	public List<FileBoardVO> getFileBoardList() {
		return fileBoardMapper.getFileBoardList();
	}
	
	public FileBoardVO fileBoardDetail(int b_no) {
		return fileBoardMapper.fileBoardDetail(b_no);
	}
	
	public int insertFileBoard(FileBoardVO fileBoard) {
		return fileBoardMapper.insertFileBoard(fileBoard);
	}
	
	public int updateFileBoard(FileBoardVO fileBoard) {
		return fileBoardMapper.updateFileBoard(fileBoard);
	}
	
	public int deleteFileBoard(int b_no) {
		return fileBoardMapper.deleteFileBoard(b_no);
	}
	
	// 파일 업로드
	public int insertFile(FileMultipartVO file) {
		return fileBoardMapper.insertFile(file);
	}
	// 파일 다운로드
	public FileMultipartVO fileDetail(int b_no) {
		return fileBoardMapper.fileDetail(b_no);
	}
	
}
