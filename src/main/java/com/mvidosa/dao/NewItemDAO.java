package com.mvidosa.dao;

import java.util.List;

import com.mvidosa.itm.NewItem;

public interface NewItemDAO {
	public void addNewItem(NewItem n);
	public void updateNewItem(NewItem n);
	public void removeNewItem(int id);
	public NewItem getNewItemById(int id);
	public List<NewItem> listNewItems();
}
