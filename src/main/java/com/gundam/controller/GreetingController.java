package com.gundam.controller;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.gundam.service.Greeting;
import com.gundam.service.PostGreetingRequest;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
		
	@PostMapping("/PostGreeting")
	public Greeting greeting(@RequestBody PostGreetingRequest request) {
	    return new Greeting(counter.incrementAndGet(), String.format(template, request.getName()));
	}

	@PostMapping("/PostParam")
	public String handlePostRequest(@RequestParam String param1, @RequestParam String param2) {
		String msg = "Hello " + param1 + ". Let's Do IT!!!! " + param2;
		return msg;
	}
}
