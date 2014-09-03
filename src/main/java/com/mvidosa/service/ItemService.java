package com.mvidosa.service;

import java.util.List;

import com.mvidosa.itm.Item;

public interface ItemService {
	public void addItem(Item i);
	public void updateItem(Item i);
	public List<Item> listItem();
	public Item getItemById(int id);
	public void removeItem(int id);
	public List<Item> listItemByUserId(int id);
}
