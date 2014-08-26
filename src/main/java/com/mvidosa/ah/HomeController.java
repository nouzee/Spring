package com.mvidosa.ah;

import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mvidosa.service.AuctionService;
import com.mvidosa.service.ItemService;
import com.mvidosa.service.UserService;
import com.mvidosa.usr.User;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private AuctionService auctionService;
	private ItemService itemService;
	private UserService userService;
	
	
	@Autowired(required = true)
	@Qualifier(value = "auctionService")
	public void setAuctionService(AuctionService as) {
		this.auctionService = as;
	}
	
	@Autowired(required = true)
	@Qualifier(value = "itemService")
	public void setItemService(ItemService is) {
		this.itemService = is;
	}
	
	@Autowired(required = true)
	@Qualifier(value = "userService")
	public void setUserService(UserService us) {
		this.userService = us;
	}
	
	@RequestMapping(value = "/")
	public String index(Locale locale) {
		System.out.println("index");
		
		User user1 = new User();
		user1.setName("Admin Ferenc");
		user1.setPassword("Mojito");
		user1.setBonusPoints(0);
		user1.setLastActivity(new Date());
		this.userService.addUser(user1);
		
		return "index";
	}

}
