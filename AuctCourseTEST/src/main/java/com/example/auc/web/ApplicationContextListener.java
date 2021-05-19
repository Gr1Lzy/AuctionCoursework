package com.example.auc.web;

import com.example.auc.inmemory.InMemoryData;
import com.example.auc.services.*;

import javax.servlet.*;
import java.util.function.UnaryOperator;

public class ApplicationContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        InMemoryData inMemoryData = new InMemoryData();

        AuctionService auctionService = new AuctionServiceImpl(inMemoryData);
        sce.getServletContext().setAttribute("auctionService", auctionService);

        UserService userService = new UserServiceImpl(inMemoryData, UnaryOperator.identity());
        sce.getServletContext().setAttribute("userService", userService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
