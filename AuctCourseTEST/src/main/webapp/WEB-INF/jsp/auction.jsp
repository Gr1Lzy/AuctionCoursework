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
            <div class="movie-title">
                <h1>
                    <c:if test="${auction.isActive}">✔️</c:if>
                    <c:if test="${!auction.isActive}">❌</c:if>
                    ${auction.title}
                </h1>
                <h3>Current bid: ${auction.currentMaxBid.value}</h3>
                <h3>Owner: ${userService.getUserById(auction.getOwnerUserId()).getName()}</h3>
                <h3>Description:</h3>
                <p>${auction.description}</p>
            </div>

            <c:if test="${!empty currUser && auction.isActive && auction.ownerUserId != currUser.userId}">
                <form action="placeBid" method="POST">
                    <input type="hidden" name="auctionId" value="${auction.auctionId}" />
                    <label for="bid">Enter bid:</label><br>
                    <input type="number" name="bid" id="bid" value="${auction.currentMaxBid.value}" />
                    <input type="submit" value="Place bid" />
                </form>
            </c:if>
            <c:if test="${!empty currUser && auction.ownerUserId == currUser.userId}">
                <button>
                    <a href="delete?auctionId=${auction.auctionId}" style="text-decoration: none; color: unset;">
                        Delete auction
                    </a>
                </button>
                <button>
                    <a href="toggleIsActive?auctionId=${auction.auctionId}" style="text-decoration: none; color: unset;">
                            ${(auction.isActive) ? "Finish" : "Start"} auction
                    </a>
                </button>
            </c:if>
        </section>
        <%@include file="footer.jspf"%>
    </body>
</html>
