package com.mvidosa.usr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mvidosa.auct.Auction;
import com.mvidosa.itm.Item;

public class User {
	
	private String name;
	private String password;
	private List<Item> items;
	private List<String> auctions;
	private int cash;
	private Date last_activity;
	public int getId;
	/*-------------------------------------------------------------------------------------------*/
	public User(String _name, String _password) {
		cash = 500000;
		name = _name;
		password = _password;
		items = new ArrayList<Item>();
		auctions = new ArrayList<String>();
		last_activity = new Date();
	}
	/*-------------------------------------------------------------------------------------------*/
	public boolean buyItem(Auction _item){
		salaryTime();
		if (_item.highestBid().getBid()<=cash) {
			cash -= _item.highestBid().getBid();
			items.add(_item);
			return true;
		}
		else {
			return false;
		}
	}
	/*-------------------------------------------------------------------------------------------*/
	public boolean isAuction(String _name) {
		return auctions.contains(_name);
	}
	/*-------------------------------------------------------------------------------------------*/
	public void addAuction(String _name) {
		if(!isAuction(_name)) {
			auctions.add(_name);
		}
	}
	/*-------------------------------------------------------------------------------------------*/
	public void addItem(Item _item, int _cost) {
		items.add(_item);
		if (_cost<=cash) {
			cash -= _cost;
			items.add(_item);
		}
	}
	/*-------------------------------------------------------------------------------------------*/
	public void sellItem(Item _item, int _bid) {
		items.remove(_item);
		cash += _bid;
		salaryTime();
	}
	/*-------------------------------------------------------------------------------------------*/
	public void salaryTime() {
		Date actual_time = new Date();
		long payed_hours = actual_time.getTime() - last_activity.getTime();
		int played_hours_int = (int) (payed_hours / (1000 * 60 * 60)); //milisec->sec  sec->min  min->hour
		
		if (played_hours_int>0)
		{
			last_activity = actual_time;
			cash += played_hours_int * 50000;
		}
	}
	/*-------------------------------------------------------------------------------------------*/
	public String getPassword() {
		salaryTime();
		return password;
	}
	/*-------------------------------------------------------------------------------------------*/
	public List<Item> getItems() {
		salaryTime();
		return items;
	}
	/*-------------------------------------------------------------------------------------------*/
	public int getCash() {
		salaryTime();
		return cash;
	}
	/*-------------------------------------------------------------------------------------------*/
	public String getName() {
		salaryTime();
		return name;
	}
	public void setItemList(List<Item> listItems) {
		// TODO Auto-generated method stub
		
	}
}
