package com.mvidosa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvidosa.auct.Auction;
import com.mvidosa.dao.AuctionDAO;

@Service
public class AuctionServiceImpl implements AuctionService{
	
	private AuctionDAO auctionDAO;
	
	public void setAuctionDAO(AuctionDAO auctionDAO) {
		this.auctionDAO = auctionDAO;
	}

	@Override
	@Transactional
	public void addAuction(Auction a) {
		this.auctionDAO.addAuction(a);
	}

	@Override
	@Transactional
	public void updateAuction(Auction a) {
		this.auctionDAO.updateAuction(a);
	}

	@Override
	@Transactional
	public List<Auction> listAuctions() {
		return this.auctionDAO.listAuctions();
	}

	@Override
	@Transactional
	public Auction getAuctionById(int id) {
		return this.auctionDAO.getAuctionById(id);
	}

	@Override
	@Transactional
	public void removeAuction(int id) {
		this.auctionDAO.removeAuctionByItemId(id);
	}

	@Override
	@Transactional
	public List<Auction> listAuctionsByUserId(int id) {
		return this.auctionDAO.listAuctionsByUserId(id);
	}

	@Override
	@Transactional
	public List<Auction> listAuctionsByUserIdCompl(int id) {
		return this.auctionDAO.listAuctionsByUserIdCompl(id);
	}
}
