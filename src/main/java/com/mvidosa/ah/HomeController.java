package com.mvidosa.ah;

import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mvidosa.auct.Auction;
import com.mvidosa.itm.Item;
import com.mvidosa.itm.NewItem;
import com.mvidosa.service.AuctionService;
import com.mvidosa.service.ItemService;
import com.mvidosa.service.NewItemService;
import com.mvidosa.service.UserService;
import com.mvidosa.usr.User;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private AuctionService auctionService;
	private ItemService itemService;
	private NewItemService newItemService;
	private UserService userService;
	
	
	@Autowired(required = true)
	@Qualifier(value = "auctionService")
	public void setAuctionService(AuctionService as) {
		this.auctionService = as;
	}
	
	@Autowired(required = true)
	@Qualifier(value = "newItemService")
	public void setNewItemService(NewItemService nis) {
		this.newItemService = nis;
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
			
		return "index";
	}

}
