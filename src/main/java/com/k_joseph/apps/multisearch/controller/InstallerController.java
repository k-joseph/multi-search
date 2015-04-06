package com.k_joseph.apps.multisearch.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.k_joseph.apps.multisearch.installer.RunInstaller;

@Controller
public class InstallerController {
	
	RunInstaller installer = new RunInstaller();
	
	@RequestMapping(value = "/installer", method = RequestMethod.GET)
	public ModelAndView launcheInstaller() {
		ModelAndView mv;
		Boolean alreadyRanInstaller = installer.checkWhetherInstalled();
		if (alreadyRanInstaller) {
			mv = new ModelAndView(new RedirectView("/multisearch"));
		} else {
			mv = new ModelAndView("installer");
		}
		
		return mv;
	}
	
	@RequestMapping(value = "/installer", method = RequestMethod.POST)
	public ModelAndView installMultiSearchApp(HttpServletRequest req, ModelAndView modelAndView) {
		RedirectView redirectView = new RedirectView("/multisearch");
		ModelAndView mv = new ModelAndView("/installer");
		String dbName = req.getParameter("databaseName");
		String dbUser = req.getParameter("databaseUser");
		String dbPsswd = req.getParameter("databaseUserPassword");
		
		if (!StringUtils.isBlank(dbName) && !StringUtils.isBlank(dbUser)) {
			if (!installer.checkWhetherInstalled()) {
				JSONObject props = installer.runNewInstallation(dbName, dbUser, dbPsswd);
				mv.setView(redirectView);
			}
		}
		
		return mv;
	}
}
