package com.example.ch13.controller;

import java.util.List;

import com.example.ch13.agent.Ex8YoutubeSearchAgent;
import com.example.ch13.dto.Youtube;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Controller
public class Ex8YoutubeSearchAgentController {
	
	// 교재 p453 참고
	private final Ex8YoutubeSearchAgent agent;

	@GetMapping("/ai/exam08-youtube-search-agent")
	public String template() {
		return "/exam08-youtube-search-agent";
	}
	
	@ResponseBody	
	@PostMapping("/ai/exam08-youtube-search")
	public List<Youtube> post(@RequestParam("question") String question) {
		List<Youtube> youtubeList = agent.execute(question);
		return youtubeList;
	}
	
}









