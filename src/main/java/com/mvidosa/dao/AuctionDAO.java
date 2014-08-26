package com.mvidosa.dao;

import java.util.List;

import com.mvidosa.auct.Auction;

public interface AuctionDAO {
	public void addAuction(Auction	a);
	public void updateAuction(Auction a);
	public void removeAuctionByItemId(int id);
	public Auction getAuctionById(int id);
	public List<Auction> listAuctions();
	public List<Auction> listAuctionsByUserId(int id);
}
