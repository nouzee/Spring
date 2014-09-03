package com.mvidosa.service;

import java.util.List;

import com.mvidosa.itm.NewItem;

public interface NewItemService {
	public void addNewItem(NewItem n);
	public void updateNewItem(NewItem n);
	public List<NewItem> listNewItems();
	public NewItem getNewItemById(int id);
	public void removeNewItem(int id);
	public List<NewItem> listNewItemsByUserId(int id);
	public List<NewItem> listNewItemsByUserIdCompl(int id);
}
