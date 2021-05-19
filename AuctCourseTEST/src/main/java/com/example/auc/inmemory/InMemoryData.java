package com.example.auc.inmemory;

import com.example.auc.model.Auction;
import com.example.auc.model.User;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class InMemoryData {

    private List<User> users;
    private List<Auction> auctions;
    private HashMap<Integer, User> usersMap = new HashMap<Integer, User>();
    private HashMap<Integer, Auction> auctionsMap = new HashMap<Integer, Auction>();

    public InMemoryData() {
        users = new ArrayList<User>();
        users.add(new User(1, "Denis", "denis@example.com", "passworddenis"));
        users.add(new User(2, "Egor", "egor@example.com", "passwordegor"));
        users.add(new User(3, "Andrey", "andrey@example.com", "passwordandrey"));
        users.add(new User(4, "Diana", "diana@example.com", "passworddiana"));
        users.add(new User(5, "Rostik", "rostik@example.com", "passwordrostik"));
        users.add(new User(6, "Daniel", "daniel@example.com", "passworddaniel"));

        auctions = new ArrayList<Auction>();
        auctions.add(new Auction(1, 1, "Lamborgini", "Aventador", 1000, Instant.now()));
        auctions.add(new Auction(2, 2, "Ford", "Fiesta", 1000, Instant.now()));
        auctions.add(new Auction(3, 3, "Subaru", "Forrester", 1000, Instant.now()));
        auctions.add(new Auction(4, 4, "Opel", "Astra J", 1000, Instant.now()));
        auctions.add(new Auction(5, 6, "Opel", "Monterey", 1000, Instant.now()));
        //auctions.add(new Auction(6, 5, "Porshe", "911", 1000, Instant.now()));

        for (User u : users) {
            usersMap.put(u.getUserId(), u);
        }

        for (Auction a : auctions) {
            auctionsMap.put(a.getAuctionId(), a);
        }
    }

    public User getUserById(Integer id) { return usersMap.get(id); }
    public User getUserByLogin(String login) {
        for (User u : users) {
            if (u.getLogin().equals(login)) return u;
        }
        return null;
    }

    public Auction getAuctionById(Integer id) { return auctionsMap.get(id); }
    public Collection<Auction> getAllAuctions() { return auctionsMap.values(); }
    public Collection<Auction> getAuctionsByText(String query) {
        String[] words = query.toLowerCase().split(" ");
        return auctionsMap.values().stream()
                .filter(auction -> auctionContainsAllWords(auction, words))
                .collect(Collectors.toList());
    }
    private static boolean auctionContainsAllWords(Auction auction, String[] words) {
        String string = auction.getTitle() + " " + auction.getDescription();
        string = string.toLowerCase();
        return Stream.of(words).allMatch(string::contains);
    }

    public void addAuctions(Auction auction) {
        if (auctionsMap.containsKey(auction.getAuctionId())) return;
        auctions.add(auction);
        auctionsMap.put(auction.getAuctionId(), auction);
    }

    public void deleteAuctionById(Integer id) {
        if (!auctionsMap.containsKey(id)) return;

        for (int i = 0; i < auctions.size(); i++) {
            if (auctions.get(i).getAuctionId().equals(id)) {
                auctions.remove(i);
                break;
            }
        }
    }
}
