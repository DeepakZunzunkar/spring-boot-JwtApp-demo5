package com.dz.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	
	@GetMapping("/")
	public String home() {
		
		System.out.println("this is private page ..");
		
		return "welcome to home page..";
	}
	
	@GetMapping("/test")
	public String test() {
		
		System.out.println("this is private page ..");
		
		return "welcome to test ..";
	}
}
