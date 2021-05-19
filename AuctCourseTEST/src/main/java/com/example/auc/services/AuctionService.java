package com.example.auc.services;

import com.example.auc.model.*;

import java.util.Collection;

public interface AuctionService {
    Collection<Auction> getAllAuctions();

    Auction getAuctionById(Integer auctionId);

    Collection<Auction> search(String text);

    void addBid(Auction auction, User user, Integer bid);
}
