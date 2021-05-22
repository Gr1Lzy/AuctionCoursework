<%@page contentType="text/html" pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Online Auctions</title>
</head>
<body>
<%@include file="header.jspf" %>
<section>
    <form action="doAddLot" method="post">
        <label for="title">Lot title:</label><br>
        <input type="text" name="title" id="title" /><br><br>

        <label for="description">Lot description:</label><br>
        <input type="text" name="description" id="description" /><br><br>

        <label for="startPrice">Lot start price:</label><br>
        <input type="number" name="startPrice" id="startPrice" /><br><br>

        <input type="submit" value="Add lot"/>
    </form>
</section>
<%@include file="footer.jspf" %>
</body>
</html>
