package com.example.auc.services;

import com.example.auc.model.*;

import java.util.Collection;

public interface AuctionService {

    void addAuction(Auction auction);

    void deleteAuction(Integer auctionId);

    Collection<Auction> getAllAuctions();

    Auction getAuctionById(Integer auctionId);

    void addBid(Auction auction, User user, Integer bid);

    Collection<Auction> search(String text);
}
