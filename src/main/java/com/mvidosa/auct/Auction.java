package com.mvidosa.auct;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mvidosa.usr.User;

@Entity
@Table(name="AUCTION")
public class Auction {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private int prize;
	
	private int highestBid;
	
	private int highestBidder;
	
	private int itemId;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPrize() {
		return prize;
	}

	public void setPrize(int prize) {
		this.prize = prize;
	}

	public int getHighestBid() {
		return highestBid;
	}

	public void setHighestBid(int highestBid) {
		this.highestBid = highestBid;
	}

	public int getHighestBidder() {
		return highestBidder;
	}

	public void setHighestBidder(int highestBidder) {
		this.highestBidder = highestBidder;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	//------------------------CONSTRUCTORS------------------------//
	public Auction() {
		
	}
	
	public Auction(int prize, int itemid, int startingPrize, User user) {
		this.prize = prize;
		this.highestBid = startingPrize;
		this.highestBidder = user.getId();
		this.itemId = itemid;
		this.user = user;
	}

}
