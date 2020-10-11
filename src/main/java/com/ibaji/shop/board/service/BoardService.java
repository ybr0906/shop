package com.ibaji.shop.board.service;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibaji.shop.board.mapper.BoardMapper;


@Service
public class BoardService {
	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private BoardMapper mapper;

	public void create(HashMap<String, Object> map) {
		mapper.insertProduct(map);
	}
}
