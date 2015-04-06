package com.k_joseph.apps.multisearch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.k_joseph.apps.multisearch.installer.RunInstaller;

@Controller
@RequestMapping(value = "/index.jsp")
public class IndexController {
	
	@RequestMapping(value = "/index.jsp", method = RequestMethod.GET)
	public ModelAndView generateIndexPage() {
		ModelAndView modelAndView = new ModelAndView("/");
		RunInstaller inst = new RunInstaller();
		
		if (inst.checkWhetherInstalled()) {
			modelAndView.addObject("installedAlready", "yess");
		}
		return modelAndView;
	}
}
