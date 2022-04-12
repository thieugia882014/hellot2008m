<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.food.entity.Product" %><%
    Product obj=(Product)request.getAttribute("obj");
%>

<!DOCTYPE html>
<html>
<title>W3.CSS</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<body>

<div class="w3-container w3-card-4" >
    <h2 class="w3-text-blue">Product detail <%=obj.getId()%></h2>
    <p>làm ơn.</p>
    <p>
        <label class="w3-text-blue"><b>name</b></label>
        <span><%=obj.getName()%></span>
    </p>

    <p>
        <label class="w3-text-blue"><b>Thumbnail</b></label>
        <img src="<%=obj.getThumbnail()%>" alt="" style="width: 200px">
    </p>
    <p>
        <label class="w3-text-blue"><b>Price</b></label>
        <span><%=obj.getPrice()%>VND</span>
    </p>

    <p>
        <label class="w3-text-blue"><b>Status</b></label>
        <span><%=obj.getStatus()%></span>
    </p>

    <p>
        <form name="submitFrom" method="GET" action="/shopping-cart/add">
            <input type="hidden" name="productId" value="<%=obj.getId()%>">
            <input type="number" name="quantity" value="1">
            <button class="w3-button w3-green" type="submit">Add</button>
        </form>

        <a href="/products/list" class="w3-btn w3-blue">end</a>
    </p>
</div>

</body>
</html>
