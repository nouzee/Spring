package com.mvidosa.ah;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mvidosa.man.Manager;
import com.mvidosa.service.AuctionService;
import com.mvidosa.service.ItemService;
import com.mvidosa.service.NewItemService;
import com.mvidosa.service.UserService;
import com.mvidosa.usr.User;

/**
 * Handles requests for the application home page.
 */
@Controller
@SessionAttributes("user")
public class HomeController {
	
	private Manager manager = new Manager();

	@Autowired(required = true)
	@Qualifier(value = "auctionService")
	public void setAuctionService(AuctionService as) {
		this.manager.setAuctionService(as);
	}
	@Autowired(required = true)
	@Qualifier(value = "newItemService")
	public void setNewItemService(NewItemService nis) {
		this.manager.setNewItemService(nis);
	}
	@Autowired(required = true)
	@Qualifier(value = "itemService")
	public void setItemService(ItemService is) {
		this.manager.setItemService(is);
	}
	@Autowired(required = true)
	@Qualifier(value = "userService")
	public void setUserService(UserService us) {
		this.manager.setUserService(us);
	}
	
	@RequestMapping(value = "/")
	public String index(Model model) {
		if (model.containsAttribute("user")) {
			return "mainpage";
		}
		model.addAttribute("information", " ");
		return "login";
	}
	
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session, Model model) {
		model.asMap().clear();
		model.addAttribute("information", " ");
		session.invalidate();
		return "login";
	}
	
	@RequestMapping(value = "/register")
	public String register(Model model, @RequestParam(value="username", required=false) String username, @RequestParam(value="password", required=false) String password) {
		if (manager.isUser(username)) {
			model.addAttribute("information", "Username already exists!");
		}
		else {
			manager.newUser(username, password);
			model.addAttribute("information", "Account created!");
		}
		return "login";
	}
	
	@RequestMapping(value = "/login")
	public ModelAndView longinTry(Model model, @RequestParam(value="username", required=false) String username, @RequestParam(value="password", required=false) String password) {
		int loginVar = this.manager.validUser(username, password);
		if (loginVar!=-1) {
			manager.salaryTime(loginVar);
			ModelAndView modelAndView = new ModelAndView();
			User user = manager.getUserById(loginVar);
			modelAndView.addObject("user", user);
			modelAndView.setViewName("mainpage");
			manager.manual();
			return modelAndView;
		}
		else {
			if (model.containsAttribute("user")) {
				return new ModelAndView("mainpage");
			}
			model.addAttribute("information", "Incorrect data!");
			return new ModelAndView("login");
		}	
	}

	/*****************************AJAX*LEKÉRÉSEK*********************************/
	@RequestMapping(value = "/useritems/{userId}", method = RequestMethod.GET)
	public @ResponseBody String listUserItems(@PathVariable("userId") int userId) {
		return manager.textifyItemsHTML(manager.listUserItems(userId));
	}
	
	@RequestMapping(value = "/userauctions/{userId}", method = RequestMethod.GET)
	public @ResponseBody String listUserAuctionsItems(@PathVariable("userId") int userId) {
		return manager.textifyUserAuctionsHTML(manager.listUserAuctions(userId));
	}
	
	@RequestMapping(value = "/otherauctions/{userId}", method = RequestMethod.GET)
	public @ResponseBody String listOtherAuctionsItems(@PathVariable("userId") int userId) {
		return manager.textifyOtherAuctionsHTML(manager.listOtherAuctions(userId));
	}
	
	@RequestMapping(value = "/othernewitems/{userId}", method = RequestMethod.GET)
	public @ResponseBody String listNewItemsAuctionsItems(@PathVariable("userId") int userId) {
		return manager.textifyNewitemsHTML(manager.listOtherNewitems(userId));
	}
	
	/***************************AJAX*MÓDOSÍTÁSOK***********************************/
	@RequestMapping(value = "/createAuction/{userId}/{itemId}/{price}")
	public @ResponseBody boolean createAuction(@PathVariable("userId") int userId, @PathVariable("itemId") int itemId, @PathVariable("price") int price) {
		return manager.addAuction(userId, itemId, price);
	}
	
	@RequestMapping(value = "/suggestNewitem/{name}/{description}/{price}/{userId}")
	public @ResponseBody boolean suggestNewitem(@PathVariable("name") String name, @PathVariable("description") String description, @PathVariable("price") int price, @PathVariable("userId") int userId) {
		return manager.suggestNewitem(name, description, price, userId);
	}
	
	@RequestMapping(value = "/sellAuction/{auctionId}")
	public @ResponseBody boolean sellAuction(@PathVariable("auctionId") int auctionId) {
		return manager.sellAuction(auctionId);
	}
	
	@RequestMapping(value = "/bidAuction/{auctionId}/{userId}/{myBid}")
	public @ResponseBody boolean bidAuction(@PathVariable("auctionId") int auctionId, @PathVariable("userId") int userId, @PathVariable("myBid") int myBid) {
		return manager.bidAuction(auctionId, userId, myBid);
	}
	
	@RequestMapping(value = "/voteNewitem/{value}/{userId}/{itemId}")
	public @ResponseBody boolean voteNewitem(@PathVariable("value") boolean value, @PathVariable("userId") int userId, @PathVariable("itemId") int itemId) {
		return manager.voteNew(value, userId, itemId);
	}
	
	@RequestMapping(value = "/deleteAuction/{auctionId}")
	public @ResponseBody boolean deleteAuction(@PathVariable("auctionId") int auctionId) {
		manager.deleteAuction(auctionId);
		return true;
	}
}
