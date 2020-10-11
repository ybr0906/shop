package com.ibaji.shop.board.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ibaji.shop.board.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	BoardService service;
	
	@GetMapping(value="/")
	public String read() {
		return "/board/read";
	}
	
	@PostMapping(value = "/create")
	public String create(@RequestParam HashMap<String, Object> map) {
		
		System.out.println("map:"+map);
		
		service.create(map);
		return "/board/create";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update() {
		return "/board/create";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete() {
		return "/board/create";
	}
	
}

