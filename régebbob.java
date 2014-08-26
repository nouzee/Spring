package com.mvidosa.ah;

import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvidosa.man.Manager;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	Manager manager;
	
	/*GO TO LOGIN------------------------------------------------------------------------*/
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, @RequestParam Map<String,String> allRequestParams) {
		manager = new Manager();
		model.addAttribute("error", "");
		return "login";
	}
	
	/*LOGIN------------------------------------------------------------------------------*/
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Locale locale, Model model, @RequestParam Map<String,String> allRequestParams) {
		
		if((allRequestParams.get("user")!=null)&&(allRequestParams.get("user")!="")&&
				(allRequestParams.get("password")!=null)&&(allRequestParams.get("password")!="")&&
				manager.isUser(allRequestParams.get("user"), allRequestParams.get("password"))) {
			model.addAttribute("user", allRequestParams.get("user"));
			model.addAttribute("userdata", manager.getUserData(allRequestParams.get("user")));
			model.addAttribute("error", "");
			logger.info(allRequestParams.get("user") + " logged in", locale);
			return "mainpage";
		}
		else
		{
			model.addAttribute("error", "Incorrect datas!");
			return "login";
		}
	}
	
	/*GO TO REGISTER------------------------------------------------------------------------*/
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(Locale locale, Model model, @RequestParam Map<String,String> allRequestParams) {
		model.addAttribute("error", "");
		return "register";
	}
	
	/*REGISTER------------------------------------------------------------------------------*/
	@RequestMapping(value = "/register_user", method = RequestMethod.POST)
	public String register_user(Locale locale, Model model, @RequestParam Map<String,String> allRequestParams) {
		
		if((allRequestParams.get("user")!=null)&&(allRequestParams.get("user")!="")&&
				(allRequestParams.get("password")!=null)&&(allRequestParams.get("password")!="")&&
				manager.userNotExists(allRequestParams.get("user"))) {
			
			model.addAttribute("user", allRequestParams.get("user"));
			model.addAttribute("error", "Successfull registration!");
			manager.addUser(allRequestParams.get("user"), allRequestParams.get("password"));
			logger.info(allRequestParams.get("user") + " registrated", locale);
			return "login";
		}
		else if(!manager.userNotExists(allRequestParams.get("user")))
		{
			model.addAttribute("error", "Username already exists!");
			return "register";
		}
		else
		{
			model.addAttribute("error", "You must fill every field!");
			return "register";
		}
	}
}
