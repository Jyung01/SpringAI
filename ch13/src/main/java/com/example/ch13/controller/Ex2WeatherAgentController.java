package com.example.ch13.controller;

import com.example.ch13.agent.Ex2WeatherAgent;
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
public class Ex2WeatherAgentController {
	
	private final Ex2WeatherAgent agent;

	@GetMapping("/ai/exam02-weather-agent")
	public String template() {
		return "/exam02-weather-agent";
	}
	
	@ResponseBody	
	@PostMapping("/ai/exam02-weather-agent")
	public String post(@RequestParam("question") String question) {
		String answer = agent.execute(question);
		return answer;
	}
	
}
