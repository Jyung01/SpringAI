package com.example.ch13.controller;

import com.example.ch13.agent.Ex3WeatherAgent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Controller
public class Ex3WeatherAgentController {
	
	private final Ex3WeatherAgent agent;

	@GetMapping("/ai/exam03-weather-agent")
	public String template() {
		return "/exam03-weather-agent";
	}
	
	@ResponseBody	
	@PostMapping("/ai/exam03-weather-agent")
	public String post(@RequestParam("question") String question) {
		String answer = agent.execute(question);
		return answer;
	}
	
}
