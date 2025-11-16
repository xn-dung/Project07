<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Add Supplier</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Storekeeper/AddSupplierView/AddSupplierView.css"/>
    </head>
    <body>
        <div class="navbar">
            <div class="nav-left">
                <a href="${pageContext.request.contextPath}/Storekeeper/MainStorekeeperView/MainStorekeeperView.jsp" class="nav-link">Home</a>
                <form action="${pageContext.request.contextPath}/SearchSupplierServlet" method="get" style="display:inline;">
                    <button type="submit" class="nav-btn">Back</button>
                </form>
            </div>
        </div>
        <div class="container">
            <h1>Add Supplier Home</h1>
            <form action="${pageContext.request.contextPath}/AddSupplierServlet" method="post">
                <label for="name">Name</label>
                <input type="text" id="name" name="name" placeholder="Enter supplier name" required>

                <label for="address">Address</label>
                <input type="text" id="address" name="address" placeholder="Enter supplier address" required>

                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="Enter supplier email" required>

                <label for="tel">Phone</label>
                <input type="tel" id="tel" name="tel" placeholder="Enter phone number" pattern="[0-9]+"
                oninput="this.value = this.value.replace(/[^0-9]/g, '')" required>

                <button type="submit">Add</button>
                <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
                <% if (errorMessage != null) {%>
                <div class="error"><%= errorMessage%></div>
                <% }
                %>
            </form>
        </div>

    </body>
</html>
