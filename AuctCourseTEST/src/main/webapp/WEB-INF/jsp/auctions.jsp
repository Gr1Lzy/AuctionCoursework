X<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%><!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Movie Database</title>
        <link rel="stylesheet" type="text/css" href="../css/mdb.css">
    </head>
    <body>
        <%@include file="header.jspf"%>
        <section>
            <table class="movies-table">
                <thead>
                    <tr>
                        <th>Movie (
                            <a href="search?sort=OLD_FIRST&text=${param.text}">Old First</a>,
                            <a href="search?sort=NEW_FIRST&text=${param.text}">New First</a>,
                            <a href="search?sort=BY_TITLE&text=${param.text}">By Title</a>
                            )</th>
                        <th>
                            <a href="search?sort=BY_LIKES&text=${param.text}">Likes</a>
                        </th>
                        <th>
                            <a href="search?sort=BY_COMMENTS&text=${param.text}">Comments</a>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="auction" items="${auctions}">
                        <tr>
                            <td title="${auction.description}">
                                <a href="auction?auctionId=${auction.auctionId}">${auction.title}</a>
                            </td>
<%--                            <td>${auction.numberOfLikes}</td>--%>
<%--                            <td>${auction.numberOfComments}</td>--%>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </section>
        <br>
        <%@include file="footer.jspf"%>
    </body>
</html>
