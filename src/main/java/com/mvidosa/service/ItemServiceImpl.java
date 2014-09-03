package com.mvidosa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvidosa.dao.ItemDAO;
import com.mvidosa.itm.Item;

@Service
public class ItemServiceImpl implements ItemService {
	
	private ItemDAO itemDAO;
	
	public void setItemDAO(ItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}

	@Override
	@Transactional
	public void addItem(Item i) {
		this.itemDAO.addItem(i);
	}

	@Override
	@Transactional
	public void updateItem(Item i) {
		this.itemDAO.updateItem(i);
	}

	@Override
	@Transactional
	public List<Item> listItem() {
		return this.itemDAO.listItems();
	}

	@Override
	@Transactional
	public Item getItemById(int id) {
		return this.itemDAO.getItemById(id);
	}

	@Override
	@Transactional
	public void removeItem(int id) {
		this.itemDAO.removeItem(id);
	}

	@Override
	@Transactional
	public List<Item> listItemByUserId(int id) {
		return this.itemDAO.listItemByUserId(id);
	}

}
