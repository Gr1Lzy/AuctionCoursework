package com.example.auc.web;


import com.example.auc.model.*;
import com.example.auc.services.AuctionService;
import com.example.auc.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "FrontControllerServlet", urlPatterns = {"/do/*"})
public class FrontControllerServlet extends HttpServlet {

    AuctionService auctionService;
    UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        auctionService = (AuctionService) config.getServletContext().getAttribute("auctionService");
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) pathInfo = "/";

        try {
            switch (pathInfo) {
                case "/login":
                    login(request, response);
                    break;
                case "/logout":
                    logout(request, response);
                    break;
                case "/auction":
                    auction(request, response);
                    break;
                case "/addLot":
                    addLot(request, response);
                    break;
                case "/doAddLot":
                    doAddLot(request, response);
                    break;
                case "/placeBid":
                    placeBid(request, response);
                    break;
                case "/toggleIsActive":
                    toggleIsActive(request, response);
                    break;
                case "/delete":
                    delete(request, response);
                    break;
                case "/":
                case "auctions":
                default:
                    homepage(request, response);
                    break;
            }
        } catch (RuntimeException ex) {
            error(request, response, "Oops, " + ex.getMessage());
        }

    }

    protected void homepage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchText = request.getParameter("text");

        Collection<Auction> auctions = auctionService.search(searchText);
        request.setAttribute("auctions", auctions);
        request.setAttribute("text", searchText);
        request.getRequestDispatcher("/WEB-INF/jsp/auctions.jsp").forward(request, response);
    }

    protected void auction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int auctionId = Integer.parseInt(request.getParameter("auctionId"));
        Auction auction = auctionService.getAuctionById(auctionId);
        request.setAttribute("auction", auction);
        request.getRequestDispatcher("/WEB-INF/jsp/auction.jsp").forward(request, response);
    }

    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();

        String login = request.getParameter("login");
        User currUser = userService.getByLogin(login);
        if (currUser == null) {
            error(request, response, "Sorry, user with login '" + login + "' not exists");
            return;
        }
        String password = request.getParameter("password");

        if (!userService.checkPassword(currUser, password)) {
            error(request, response, "Sorry, wrong password");
            return;
        }

        request.getSession().setAttribute("currUser", currUser);
        response.sendRedirect(".");
    }

    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("auctions", auctionService.getAllAuctions());
        request.getSession().invalidate();
        response.sendRedirect(".");
    }

    protected void addLot(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/addLot.jsp").forward(request, response);
    }

    protected void doAddLot(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currUser = (User) request.getSession().getAttribute("currUser");
        if (currUser == null) {
            error(request, response, "Sorry, you need to log in");
            return;
        }

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        Integer startPrice = Integer.parseInt(request.getParameter("startPrice"));

        Integer id = 1;
        while (auctionService.getAuctionById(id) != null) id++;

        auctionService.addAuction(new Auction(id, currUser.getUserId(), title, description, startPrice, false));
        response.sendRedirect(".");
    }

    protected void placeBid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User currUser = (User) request.getSession().getAttribute("currUser");
        if (currUser == null) {
            error(request, response, "Sorry, you need to log in");
            return;
        }

        int auctionId = Integer.parseInt(request.getParameter("auctionId"));
        Auction auction = auctionService.getAuctionById(auctionId);

        if (auction.getOwnerUserId().equals(currUser.getUserId())) {
            error(request, response, "Owner cannot place bids on his own lot");
            return;
        }

        int bid = Integer.parseInt(request.getParameter("bid"));
        if (bid < auction.getCurrentMaxBid().getValue()) {
            error(request, response, "Please, enter a number higher than current price");
            return;
        }

        auctionService.addBid(auction, currUser, bid);
        response.sendRedirect("./auction?auctionId=" + auctionId);
    }

    protected void toggleIsActive(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currUser = (User) request.getSession().getAttribute("currUser");
        if (currUser == null) {
            error(request, response, "Sorry, you need to log in");
            return;
        }

        int auctionId = Integer.parseInt(request.getParameter("auctionId"));
        Auction auction = auctionService.getAuctionById(auctionId);

        if (auction.getOwnerUserId() != currUser.getUserId()) {
            error(request, response, "Only user can toggle state");
            return;
        }

        auction.setIsActive(!auction.getIsActive());
        response.sendRedirect("./auction?auctionId=" + auction.getAuctionId());
    }

    protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currUser = (User) request.getSession().getAttribute("currUser");
        if (currUser == null) {
            error(request, response, "Sorry, you need to log in");
            return;
        }

        int auctionId = Integer.parseInt(request.getParameter("auctionId"));

        if (auctionService.getAuctionById(auctionId).getOwnerUserId() != currUser.getUserId()) {
            error(request, response, "Only user can delete auction");
            return;
        }

        auctionService.deleteAuction(auctionId);
        response.sendRedirect(".");
    }

    protected void error(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        request.setAttribute("message", message);
        request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

