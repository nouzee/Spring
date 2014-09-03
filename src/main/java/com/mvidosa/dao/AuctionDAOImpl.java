package com.mvidosa.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.mvidosa.auct.Auction;

@Repository
public class AuctionDAOImpl implements AuctionDAO {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public void addAuction(Auction a) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(a);
	}

	@Override
	public void updateAuction(Auction a) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(a);
	}

	@Override
	public void removeAuctionByItemId(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Auction a = (Auction) session.get(Auction.class, new Integer(id));
		if(null != a) {
			session.delete(a);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Auction> listAuctions() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Auction> auctionList = session.createQuery("from Auction").list();
		return auctionList;
	}

	@Override
	public Auction getAuctionById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Auction a = (Auction) session.get(Auction.class, new Integer(id));
		return a;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Auction> listAuctionsByUserId(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("from Auction where userId = :userId").setParameter("userId", id).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Auction> listAuctionsByUserIdCompl(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("from Auction where userId <> :userId").setParameter("userId", id).list();
	}
	
}
