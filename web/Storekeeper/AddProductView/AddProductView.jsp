<%@page import="java.util.HashSet"%>
<%@page import="model.Category"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Add Product</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Storekeeper/AddProductView/AddProductView.css"/>
    </head>
    <body>
        <div class="navbar">
            <div class="nav-left">
                <a href="${pageContext.request.contextPath}/Storekeeper/MainStorekeeperView/MainStorekeeperView.jsp" class="nav-link">Home</a>
                <form action="${pageContext.request.contextPath}/SearchProductServlet" method="get" style="display:inline;">
                    <button type="submit" class="nav-btn">Back</button>
                </form>
            </div>
        </div>
        <div class="container">
            <h1>Add Product Home</h1>
            <form class="add-product" action="${pageContext.request.contextPath}/AddProductServlet" method="post">
                <label for="name">Name</label>
                <input type="text" id="name" name="name" placeholder="Enter product name" required>

                <label for="unitprice">Unit Price</label>
                <input type="text" id="unitprice" name="unitprice" placeholder="Enter product's unit price" required>

                <label for="description">Description</label>
                <textarea id="description" name="description" placeholder="Enter the product's description" required></textarea>

                <label for="category">Category</label>
                <select id="category" name="category" required>
                    <option value="">-- Select Category --</option>
                    <%
                        HashSet<Category> categories = (HashSet<Category>) session.getAttribute("categories");

                        if (categories != null) {
                            for (Category c : categories) {
                    %>
                    <option value="<%= c.getId() + "|" + c.getName() + "|" + c.getNote()%>"><%= c.getName()%></option>
                    <%
                        }
                    } else {
                    %>
                    <option disabled>No category available</option>
                    <%
                        }
                    %>
                </select>


                <button type="submit" class="add-btn">Add</button>
                <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
                <% if (errorMessage != null) {%>
                <div class="error"><%= errorMessage%></div>
                <% }
                %>
            </form>
        </div>

    </body>
</html>
