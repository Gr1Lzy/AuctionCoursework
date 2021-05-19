
package com.example.auc.model;


import javafx.util.Pair;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


public class Auction {

    private Integer id;
    private Integer ownerUserId;
    private String title;
    private String description;
    private Integer startPrice;
    private Set<Pair<User, Integer>> bids = new HashSet<Pair<User, Integer>>();
    private Instant timeCreated;

    public Auction(Integer id, Integer ownerUserId, String title, String description, Integer startPrice, Instant instant) {
        this.id = id;
        this.ownerUserId = ownerUserId;
        this.title = title;
        this.description = description;
        this.startPrice = startPrice;
        this.timeCreated = instant;
    }

    public Integer getAuctionId() { return id; }
    public void setAuctionId(Integer id) { this.id = id; }

    public Integer getOwnerUserId() { return ownerUserId; }
    public void setOwnerUserId(Integer ownerId) { this.ownerUserId = ownerId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getStartPrice() { return startPrice; }
    public void setStartPrice(Integer startPrice) { this.startPrice = startPrice; }

    public Set<Pair<User, Integer>> getBids() { return bids; }
    public void addBid(Pair<User, Integer> newBid) { bids.add(newBid); }
    public int getNumberOfBids() { return bids.size(); }

    public Instant getTimeCreated() { return timeCreated; }
    public void setTimeCreated(Instant time) { this.timeCreated = time; }
}
