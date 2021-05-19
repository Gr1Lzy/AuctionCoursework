package com.example.auc.services;

import com.example.auc.inmemory.InMemoryData;
import com.example.auc.model.Auction;
import com.example.auc.model.User;
import javafx.util.Pair;

import java.util.Collection;

public class AuctionServiceImpl implements AuctionService {

    InMemoryData db;

    public AuctionServiceImpl(InMemoryData db) {
        this.db = db;
    }

    @Override
    public Collection<Auction> getAllAuctions() {
        return db.getAllAuctions();
    }

    @Override
    public Auction getAuctionById(Integer auctionId) {
        return db.getAuctionById(auctionId);
    }

    @Override
    public Collection<Auction> search(String query) {
        if (query == null || query.equals("")) {
            return getAllAuctions();
        }
        return db.getAuctionsByText(query);
    }

    @Override
    public void addBid(Auction auction, User user, Integer bid) {
        auction.addBid(new Pair<User, Integer>(user, bid));
    }
}
