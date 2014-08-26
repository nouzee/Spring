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

	private int itemId;
	
	private int userId;
	
	private int prize;
	
	private int highestBid;
	
	private int highestBidder;
	
	@ManyToOne
	@JoinColumn(name="user")
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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
}
