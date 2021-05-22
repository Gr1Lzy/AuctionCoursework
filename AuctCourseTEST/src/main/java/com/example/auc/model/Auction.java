
package com.example.auc.model;


import javafx.util.Pair;

import java.util.HashSet;
import java.util.Set;


public class Auction {

    private Integer id;
    private Integer ownerUserId;
    private String title;
    private String description;
    private boolean isActive;
    private Pair<User, Integer> currentMaxBid;
    private Set<Pair<User, Integer>> bids = new HashSet<Pair<User, Integer>>();

    public Auction(Integer id, Integer ownerUserId, String title, String description, Integer startPrice, boolean isActive) {
        this.id = id;
        this.ownerUserId = ownerUserId;
        this.title = title;
        this.description = description;
        this.isActive = isActive;
        this.currentMaxBid = new Pair<User, Integer>(new User(0, "Start price", null, null), startPrice);
    }

    public Integer getAuctionId() { return id; }
    public void setAuctionId(Integer id) { this.id = id; }

    public Integer getOwnerUserId() { return ownerUserId; }
    public void setOwnerUserId(Integer ownerId) { this.ownerUserId = ownerId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean getIsActive() { return isActive; }
    public void setIsActive(boolean active) { isActive = active; }

    public Pair<User, Integer> getCurrentMaxBid() { return currentMaxBid; }

    public Set<Pair<User, Integer>> getBids() { return bids; }
    public void addBid(Pair<User, Integer> newBid) {
        if (currentMaxBid.getValue() < newBid.getValue()) {
            currentMaxBid = newBid;
        }
        bids.add(newBid);
    }
    public int getNumberOfBids() { return bids.size(); }
}
