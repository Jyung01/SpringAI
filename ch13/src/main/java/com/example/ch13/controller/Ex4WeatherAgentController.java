package com.example.ch13.controller;

import com.example.ch13.agent.Ex4WeatherAgent;
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
public class Ex4WeatherAgentController {
	
	private final Ex4WeatherAgent agent;

	@GetMapping("/ai/exam04-weather-agent")
	public String template() {
		return "/exam04-weather-agent";
	}
	
	@ResponseBody	
	@PostMapping("/ai/exam04-weather-agent")
	public String post(@RequestParam("question") String question, HttpSession session) {
		String answer = agent.execute(question, session.getId());
		return answer;
	}
	
}









