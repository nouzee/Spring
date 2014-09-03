package com.mvidosa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvidosa.dao.NewItemDAO;
import com.mvidosa.itm.NewItem;

@Service
public class NewItemServiceImpl implements NewItemService{
	
	private NewItemDAO newItemDAO;
	
	public void setNewItemDAO(NewItemDAO newItemDAO) {
		this.newItemDAO = newItemDAO;
	}

	@Override
	@Transactional
	public void addNewItem(NewItem n) {
		this.newItemDAO.addNewItem(n);
		
	}

	@Override
	@Transactional
	public void updateNewItem(NewItem n) {
		this.newItemDAO.updateNewItem(n);
	}

	@Override
	@Transactional
	public List<NewItem> listNewItems() {
		return this.newItemDAO.listNewItems();
	}

	@Override
	@Transactional
	public NewItem getNewItemById(int id) {
		return this.newItemDAO.getNewItemById(id);
	}

	@Override
	@Transactional
	public void removeNewItem(int id) {
		this.newItemDAO.removeNewItem(id);
	}

	@Override
	@Transactional
	public List<NewItem> listNewItemsByUserId(int id) {
		return this.newItemDAO.listNewItemsByUserId(id);
	}

	@Override
	@Transactional
	public List<NewItem> listNewItemsByUserIdCompl(int id) {
		return this.newItemDAO.listNewItemsByUserIdCompl(id);
	}
	
}
