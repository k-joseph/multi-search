package com.k_joseph.apps.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {
	
	String message = "Welcome to k-joseph's exploration of Spring MVC!";
	
	@RequestMapping("/hello")
	public ModelAndView showMessage(HttpServletRequest request) {
		System.out.println("in controller");
		String name = request.getParameter("name");
		ModelAndView mv = new ModelAndView("helloworld");
		mv.addObject("message", message);
		mv.addObject("name", name);
		return mv;
	}
}
