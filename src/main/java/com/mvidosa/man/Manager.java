package com.mvidosa.man;

import java.util.Date;
import java.util.List;

import com.mvidosa.auct.Auction;
import com.mvidosa.itm.Item;
import com.mvidosa.itm.NewItem;
import com.mvidosa.service.AuctionService;
import com.mvidosa.service.ItemService;
import com.mvidosa.service.NewItemService;
import com.mvidosa.service.UserService;
import com.mvidosa.usr.User;

public class Manager {
	
	private AuctionService auctionService;
	private ItemService itemService;
	private NewItemService newItemService;
	private UserService userService;
	
	public void setAuctionService(AuctionService auctionService) {
		this.auctionService = auctionService;
	}
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}
	public void setNewItemService(NewItemService newItemService) {
		this.newItemService = newItemService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void manual() {
		
	}
	
	public void newUser(String userName, String password) {
		userService.addUser(new User(userName, password));
	}
	
	public boolean isUser(String userName) {
		for(User u : userService.listUsers()) {
			if (u.getName().equals(userName)) {
				return true;
			}
		}	
		return false;
	}
	
	public int validUser(String userName, String password) {
		
		for(User u : userService.listUsers()) {
			if (u.getName().equals(userName)&&u.getPassword().equals(password)) {
				return u.getId();
			}
		}	
		return -1;
	}
	
	public List<Item> listUserItems(int userId) {
		return itemService.listItemByUserId(userId);
	}
	
	public List<Auction> listUserAuctions(int userId) {
		return auctionService.listAuctionsByUserId(userId);
	}
	
	public List<Auction> listOtherAuctions(int userId) {
		return auctionService.listAuctionsByUserIdCompl(userId);
	}
	
	public boolean isAuction(int userId, int itemId) {
		for(Auction a : listUserAuctions(userId)) {
			if(a.getItemId() == itemId) {
				return true;
			}
		}
		return false;
	}
	
	public boolean suggestNewitem(String name, String description, int prize, int userId){
		User user = getUserById(userId);
		if(user.getCash()>=prize){
			NewItem n = new NewItem(name, description, prize, user);
			
			user.setCash(user.getCash()-prize);
			userService.updateUser(user);

			newItemService.addNewItem(n);
			return true;
		}
		return false;
	}
	
	public List<NewItem> listUserNewitems(int userId) {
		return newItemService.listNewItemsByUserId(userId);
	}
	
	public List<NewItem> listOtherNewitems(int userId) {
		return newItemService.listNewItemsByUserIdCompl(userId);
	}
	
	public User getUserById(int userId) {
		return userService.getUserById(userId);
	}
	
	public boolean addAuction(int userId, int itemId, int prize) {
		if(isAuction(userId, itemId)) {
			return false;
		}
		User user = userService.getUserById(userId);
		Auction auction = new Auction(itemId,prize,user);
		auctionService.addAuction(auction);
		return true;
	}
	
	public boolean sellAuction(int auctionId) {
		
		Auction auction = auctionService.getAuctionById(auctionId);
		User buyer = userService.getUserById(auction.getHighestBidder());
		User seller = userService.getUserById(auction.getUser().getId());
		Item item = itemService.getItemById(auction.getItemId());
		int prize = auction.getHighestBid();
		
		if(buyer.getId() != seller.getId()) {
			
			buyer.setTotalcash(buyer.getTotalcash()-prize);
			item.setUser(buyer);
			seller.setTotalcash(seller.getTotalcash()+prize);
			seller.setCash(seller.getCash()+prize);
			seller.setBonusPoints(seller.getBonusPoints()+1);
			
			itemService.updateItem(item);
			userService.updateUser(buyer);
			userService.updateUser(seller);
			auctionService.removeAuction(auctionId);
			
			return true;
		}
		
		return false;
	}
	
	public boolean bidAuction(int auctionId, int userId, int bid) {
		Auction auction = auctionService.getAuctionById(auctionId);
		User highestBidder = userService.getUserById(auction.getHighestBidder());
		User seller = userService.getUserById(auction.getUser().getId());
		User bidder = userService.getUserById(userId);
		
		if(bidder.getCash()>=bid) {
			if (bid>auction.getHighestBid()) {			
				if(seller.getId() != highestBidder.getId()) {
					highestBidder.setCash(highestBidder.getCash()+auction.getHighestBid());
				}
				
				auction.setHighestBid(bid);
				auction.setHighestBidder(userId);
				bidder.setCash(bidder.getCash()-bid);
				
				
				userService.updateUser(highestBidder);
				userService.updateUser(bidder);
				userService.updateUser(seller);
				auctionService.updateAuction(auction);
				
				return true;
			}
		}
		return false;
	}
	
	public boolean voteNew(boolean vote, int userId, int newitemId) {
		User voter = userService.getUserById(userId);
		NewItem item = newItemService.getNewItemById(newitemId);
		User owner = userService.getUserById(item.getUser().getId());

		if(!item.getVoters().contains(voter)&&voter.getId()!=owner.getId()) {
			
			if(vote) { 
				item.voteUp(voter);
			}
			else { 
				item.voteDown(voter);
			}
			
			int ups = item.getUps();
			int downs = item.getDowns();
			
			if (ups>downs&&ups>9) {
				Item item1 = new Item(item.getName(), item.getDescription(), owner);
				owner.setTotalcash(owner.getTotalcash()-item.getPrize());
				itemService.addItem(item1);
				userService.updateUser(owner);
				
				for(User u:userService.listUsers()) {
					u.getVoteditems().remove(item);
				}
				
				newItemService.removeNewItem(newitemId);
			}
			else if (downs>9) {
				owner.setCash(owner.getCash()+item.getPrize());
				userService.updateUser(owner);
				for(User u:userService.listUsers()) {
					u.getVoteditems().remove(item);
				}
				newItemService.removeNewItem(newitemId);
			}
			else {
				voter.addVotedItem(item);
				newItemService.updateNewItem(item);
			}
			
			return true;
		}
		
		return false;
	}
	
	public void salaryTime(int userId) {
		User user = getUserById(userId);
		Date actual_time = new Date();
		long payed_hours = actual_time.getTime() - user.getLastActivity().getTime();
		int played_hours_int = (int) (payed_hours / (1000));
		
		if (played_hours_int>0)
		{
			user.setLastActivity(actual_time);
			int payment = played_hours_int * 1;
			user.setCash(user.getCash()+payment);
			user.setTotalcash(user.getTotalcash()+payment);
			userService.updateUser(user);
		}
	}
	
	public void deleteAuction(int auctionId) {
		Auction auction = auctionService.getAuctionById(auctionId);
		User highestBidder = userService.getUserById(auction.getHighestBidder());
		User seller = userService.getUserById(auction.getUser().getId());
		
		if(seller.getId() != highestBidder.getId()) {
			highestBidder.setCash(highestBidder.getCash()+auction.getHighestBid());
			userService.updateUser(highestBidder);
		}
		auctionService.removeAuction(auctionId);
	}
	
	public String textifyItemsHTML(List<Item> list) {
		String result = "";
		char pick = '"';
		for(Item i: list) {
			if(isAuction(i.getUser().getId(), i.getId())) {
				result += "<tr class='spec_hov_td'><td class='user_auction item_td' colspan='2' onmouseout="+pick+"itemOut()"+pick+" onmouseover="+pick+"itemDescription('"+ i.getDescription() +"','"+i.getId()+"')"+pick+">";
				result += i.getName();
			}
			else {
				result += "<tr class='spec_hov_td'><td class='item_td' onmouseout="+pick+"itemOut()"+pick+" onmouseover="+pick+"itemDescription('"+ i.getDescription() +"','"+i.getId()+"')"+pick+">";
				result += i.getName();
				result += "</td><td class='auction_button_td'>";
				result += "<input type='button' class='auction_button_class' onclick="+pick+"createAuctionButton('" + i.getUser().getId() + "','" + i.getId() + "')"+pick+" value='Auction' class='create_auction_button' id='auc_but"+i.getId()+"'/>";                                      
			}

			result += "</td></tr>";
		}
		return result;
	}
	
	public String textifyUserAuctionsHTML(List<Auction> list) {
		String result = "";
		char pick = '"';
		for(Auction a: list) {
			result += "<tr class='spec_hov_td'><td onmouseout="+pick+"auctionOut()"+pick+" onmouseover="+pick+"auctionDescription('"+ itemService.getItemById(a.getItemId()).getDescription() +"','"+a.getId()+"')"+pick+">";
			result += itemService.getItemById(a.getItemId()).getName();
			result += "</td><td>";
			result += a.getHighestBid();
			result += "</td><td>";
			result += userService.getUserById(a.getHighestBidder()).getName();
			result += "</td><td>";
			result += "<input type='button' onclick="+pick+"deleteAuctionButton('" + a.getId() + "')"+pick+" value='Delete' class='delete_auction_button'/>";
			result += "</td><td>";
			result += "<input type='button' onclick="+pick+"sellAuctionButton('" + a.getId() + "')"+pick+" value='Sell' class='sell_auction_button'/>";                                      
			result += "</td></tr>";
		}
		return result;
	}
	
	public String textifyOtherAuctionsHTML(List<Auction> list) {
		String result = "";
		char pick = '"';
		for(Auction a: list) {
			result += "<tr class='spec_hov_td'><td onmouseout="+pick+"auctionOut()"+pick+" onmouseover="+pick+"auctionDescription('"+ itemService.getItemById(a.getItemId()).getDescription() +"','"+a.getId()+"')"+pick+">";
			result += itemService.getItemById(a.getItemId()).getName();
			result += "</td><td>";
			result += a.getHighestBid();
			result += "</td><td>";
			result += userService.getUserById(a.getHighestBidder()).getName();
			result += "</td><td>";
			result += "<input type='button' onclick="+pick+"bidAuctionButton('" + a.getId() + "')"+pick+" value='Bid' class='bid_auction_button'  id='bid_but"+a.getId()+"'/>";                                      
			result += "</td></tr>";
		}
		return result;
	}
	
	public String textifyNewitemsHTML(List<NewItem> list) {
		String result = "";
		char pick = '"';
		for(NewItem n: list) {
			result += "<tr class='spec_hov_td'><td onmouseout="+pick+"newitemOut()"+pick+" onmouseover="+pick+"newitemDescription('"+ n.getDescription() +"','"+n.getId()+"')"+pick+">";
			result += n.getName();
			result += "</td><td>";
			result += n.getPrize();
			result += "</td><td>";
			result += n.getUps() + "/" + n.getDowns();
			result += "</td><td>";
			result += "<input type='button' onclick="+pick+"voteNewitem('" + n.getId() + "')"+pick+" value='Vote' class='vote_newitem_button'  id='vote_but"+n.getId()+"'/>";                                      
			result += "</td></tr>";
		}
		return result;
	}
}
