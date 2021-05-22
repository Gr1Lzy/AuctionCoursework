<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Online Auctions</title>
    </head>
    <body>
        <%@include file="header.jspf"%>
        <section>
            <h1>${message}</h1>
            <a href="auctions.jsp">Go to main page</a>
        </section>  
        <br>
        <%@include file="footer.jspf"%>
    </body>
</html>
