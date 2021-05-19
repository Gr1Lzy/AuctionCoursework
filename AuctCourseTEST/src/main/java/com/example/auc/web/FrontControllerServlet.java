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
//                case "/like" -> like(request, response);
//                case "/unlike" -> unlike(request, response);
                case "/placeBid":
                    placeBid(request, response);
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
        User user = userService.getByLogin(login);
        if (user == null) {
            error(request, response, "Sorry, user with login '" + login + "' not exists");
            return;
        }
        String password = request.getParameter("password");

        if (!userService.checkPassword(user, password)) {
            error(request, response, "Sorry, wrong password");
            return;
        }

        request.getSession().setAttribute("user", user);
        response.sendRedirect(".");
    }

    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("auctions", auctionService.getAllAuctions());
        request.getSession().invalidate();
        response.sendRedirect(".");
    }

//    protected void like(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        User user = (User) request.getSession().getAttribute("user");
//        if (user == null) {
//            error(request, response, "Sorry, you need to log in");
//            return;
//        }
//
//        int movieId = Integer.parseInt(request.getParameter("movieId"));
//        Movie movie = auctionService.getMovieById(movieId);
//
//        auctionService.likeMovie(movie, user);
//        response.sendRedirect("./movie?movieId=" + movieId);
//    }
//    protected void unlike(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        User user = (User) request.getSession().getAttribute("user");
//        if (user == null) {
//            error(request, response, "Sorry, you need to log in");
//            return;
//        }
//
//        int movieId = Integer.parseInt(request.getParameter("movieId"));
//        Auction auction = AuctionService.getAuctionById(movieId);
//
//        auctionService.unlikeMovie(movie, user);
//        response.sendRedirect("./movie?movieId=" + movieId);
//    }

    // todo: refactor below
    protected void placeBid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            error(request, response, "Sorry, you need to log in");
            return;
        }

        int auctionId = Integer.parseInt(request.getParameter("auctionId"));
        Auction auction = auctionService.getAuctionById(auctionId);

        int bid = 0;
        try {
            bid = Integer.parseInt(request.getParameter("text"));
        } catch (NumberFormatException ex) {
            error(request, response, "Please, enter a number");
        }

        auctionService.addBid(auction, user, bid);
        response.sendRedirect("./auction?auctionId=" + auctionId);
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

