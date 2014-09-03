package com.mvidosa.usr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mvidosa.auct.Auction;
import com.mvidosa.itm.Item;
import com.mvidosa.itm.NewItem;

@Entity
@Table(name="USER")
public class User {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String password;
	
	private Date lastActivity;
	
	private int cash;
	
	private int totalCash;
	
	private int bonusPoints;
	
	@OneToMany(mappedBy="user")
	private List<Item> items;
	
	@OneToMany(mappedBy="user")
	private List<Auction> auctions;
	
	@OneToMany(mappedBy="user")
	private List<NewItem> newitems;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="userItem_id")
	private List<NewItem> voteditems;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastActivity() {
		return lastActivity;
	}

	public void setLastActivity(Date lastActivity) {
		this.lastActivity = lastActivity;
	}

	public int getBonusPoints() {
		return bonusPoints;
	}

	public void setBonusPoints(int bonusPoints) {
		this.bonusPoints = bonusPoints;
	}
	
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public List<Auction> getAuctions() {
		return auctions;
	}

	public void setAuctions(List<Auction> auctions) {
		this.auctions = auctions;
	}

	public List<NewItem> getNewitems() {
		return newitems;
	}

	public void setNewitems(List<NewItem> newitems) {
		this.newitems = newitems;
	}

	public List<NewItem> getVoteditems() {
		return voteditems;
	}

	public void setVoteditems(List<NewItem> voteditems) {
		this.voteditems = voteditems;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}
	

	public int getTotalcash() {
		return totalCash;
	}

	public void setTotalcash(int totalCash) {
		this.totalCash = totalCash;
	}
	
	public void addVotedItem(NewItem item) {
		this.voteditems.add(item);
	}
	
	@Override
    public boolean equals(Object u)
    {
		return ((User) u).getId()==this.getId();
    }
	
	@Override
	public String toString(){
		return "id = "+id+", name = "+name+", password = "+password+", last activity = "+lastActivity+",  bonus points = "+bonusPoints;
	}
	
	//------------------------CONSTRUCTORS------------------------//
	public User(String name, String password) {
		this.name = name;
		this.password = password;
		this.lastActivity = new Date();
		this.bonusPoints = 0;
		this.cash = 100000;
		this.totalCash = 100000;
		this.items = new ArrayList<Item>();
		this.newitems = new ArrayList<NewItem>();
		this.voteditems = new ArrayList<NewItem>();
		this.auctions = new ArrayList<Auction>();
	}
	
	public User() { }

}
