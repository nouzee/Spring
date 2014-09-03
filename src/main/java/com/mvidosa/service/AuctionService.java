package com.mvidosa.service;

import java.util.List;

import com.mvidosa.auct.Auction;

public interface AuctionService {
	public void addAuction(Auction a);
	public void updateAuction(Auction a);
	public List<Auction> listAuctions();
	public Auction getAuctionById(int id);
	public void removeAuction(int id);
	public List<Auction> listAuctionsByUserId(int id);
	public List<Auction> listAuctionsByUserIdCompl(int id);
}
