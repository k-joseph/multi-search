package com.k_joseph.apps.multisearch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.k_joseph.apps.multisearch.installer.RunInstaller;

@Controller
@RequestMapping(value = "/")
/*
 * TODO this class is not working yet, fails to get the index mapping; look into fixing it
 */
public class IndexController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView generateIndexPage() {
		ModelAndView modelAndView = new ModelAndView("/");
		RunInstaller inst = new RunInstaller();
		
		if (inst.checkWhetherInstalled()) {
			modelAndView.addObject("installedAlready", inst.checkWhetherInstalled());
		}
		return modelAndView;
	}
}
