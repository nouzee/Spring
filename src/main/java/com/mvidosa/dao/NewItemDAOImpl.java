package com.mvidosa.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.mvidosa.itm.NewItem;

@Repository
public class NewItemDAOImpl implements NewItemDAO{
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public void addNewItem(NewItem n) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(n);
	}

	@Override
	public void updateNewItem(NewItem n) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(n);
	}

	@Override
	public void removeNewItem(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		NewItem n = (NewItem) session.get(NewItem.class, new Integer(id));
		if(null != n) {
			session.delete(n);
		}
	}

	@Override
	public NewItem getNewItemById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		NewItem n = (NewItem) session.get(NewItem.class, new Integer(id));
		return n;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<NewItem> listNewItems() {
		Session session = this.sessionFactory.getCurrentSession();
		List<NewItem> newItemList = session.createQuery("from NewItem").list();
		return newItemList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<NewItem> listNewItemsByUserId(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("from NewItem where userId = :userId").setParameter("userId", id).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<NewItem> listNewItemsByUserIdCompl(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("from NewItem where userId <> :userId").setParameter("userId", id).list();
	}
}
