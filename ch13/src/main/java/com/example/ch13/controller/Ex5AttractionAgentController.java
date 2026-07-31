package com.example.ch13.controller;

import java.util.List;

import com.example.ch13.agent.Ex5AttractionAgent;
import com.example.ch13.dto.Attraction;
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
public class Ex5AttractionAgentController {
	
	private final Ex5AttractionAgent agent;

	@GetMapping("/ai/exam05-attraction-agent")
	public String template() {
		return "/exam05-attraction-agent";
	}
	
	@ResponseBody	
	@PostMapping("/ai/exam05-attraction-agent")
	public List<Attraction> post(@RequestParam("question") String question) {
		List<Attraction> attractionList = agent.execute(question);
		return attractionList;
	}
	
}









