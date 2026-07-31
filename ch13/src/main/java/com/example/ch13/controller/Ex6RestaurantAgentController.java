package com.example.ch13.controller;

import java.util.List;

import com.example.ch13.agent.Ex6RestaurantAgent;
import com.example.ch13.dto.Restaurant;
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
public class Ex6RestaurantAgentController {
	
	private final Ex6RestaurantAgent agent;

	@GetMapping("/ai/exam06-restaurant-agent")
	public String template() {
		return "/exam06-restaurant-agent";
	}
	
	@ResponseBody	
	@PostMapping("/ai/exam06-restaurant-agent")
	public List<Restaurant> post(@RequestParam("question") String question) {
		List<Restaurant> attractionList = agent.execute(question);
		return attractionList;
	}
	
}









