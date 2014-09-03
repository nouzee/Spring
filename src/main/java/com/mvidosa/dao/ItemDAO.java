package com.mvidosa.dao;

import java.util.List;

import com.mvidosa.itm.Item;

public interface ItemDAO {
	public void addItem(Item i);
	public void updateItem(Item i);
	public List<Item> listItems();
	public Item getItemById(int itemId);
	public void removeItem(int itemId);
	public List<Item> listItemByUserId(int id);
}
