<%--<%@page contentType="text/html" pageEncoding="UTF-8"%>--%>
<%--<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Online Auctions</title>
    </head>
    <body>
        <%@include file="header.jspf"%>
        <section>
            <table class="movies-table">
                <thead>
                    <tr>
                        <th>Lots</th>
                        <th>Top bid</th>
                        <th>Top user</th>
                        <th>Active</th>
                        <th>Owner</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="auction" items="${auctions}">
                        <tr>
                            <td title="${auction.description}">
                                <a href="auction?auctionId=${auction.auctionId}">${auction.title}</a>
                            </td>
                            <td>${auction.currentMaxBid.value}</td>
                            <td>${auction.currentMaxBid.key.name}</td>
                            <td>
                                <c:if test="${auction.isActive}">✔️</c:if>
                                <c:if test="${!auction.isActive}">❌</c:if>
                            </td>
                            <td>${userService.getUserById(auction.getOwnerUserId()).getName()}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </section>
        <br>
        <%@include file="footer.jspf"%>
    </body>
</html>
