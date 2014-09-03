package com.mvidosa.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.mvidosa.itm.Item;

@Repository
public class ItemDAOImpl implements ItemDAO{
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public void addItem(Item i) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(i);
	}

	@Override
	public void updateItem(Item i) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(i);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Item> listItems() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Item> itemList = session.createQuery("from Item").list();
		return itemList;
	}

	@Override
	public Item getItemById(int itemId) {
		Session session = this.sessionFactory.getCurrentSession();
		Item i = (Item) session.get(Item.class, new Integer(itemId));
		return i;
	}

	@Override
	public void removeItem(int itemId) {
		Session session = this.sessionFactory.getCurrentSession();
		Item i = (Item) session.get(Item.class, new Integer(itemId));
		if(null != i) {
			session.delete(i);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Item> listItemByUserId(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("from Item where userId = :userId").setParameter("userId", id).list();
	}
}
