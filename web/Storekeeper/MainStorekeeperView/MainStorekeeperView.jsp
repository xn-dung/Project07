<%@page import="model.Staff"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Storekeeper/MainStorekeeperView/MainStorekeeperView.css"/>
</head>
<body>
    <%
        Staff staff = (Staff) session.getAttribute("staff");
    %>
    <div class="navbar">
        <div class="nav-left">
            Hello, <%= staff.getName()%>!
        </div>
        <div class="nav-right">
            <form action="${pageContext.request.contextPath}/LogoutServlet" method="get" style="display:inline;">
                <button type="submit" class="logout-btn">Logout</button>
            </form>
        </div>
    </div>

    <div class="content">
        <h1>Welcome to Home Page</h1>
        <div class="center-btn">
            <a href="${pageContext.request.contextPath}/SearchSupplierServlet" class="order-btn">Import product from supplier</a>
        </div>
    </div>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>

    <script>
        <%
            String toastMessage = (String) session.getAttribute("toastMessage");
            String toastType = (String) session.getAttribute("toastType");
            if (toastMessage != null && toastType != null) {
                session.removeAttribute("toastMessage");
                session.removeAttribute("toastType");
        %>
                  toastr.options = {
                      closeButton: true,
                      progressBar: true,
                      positionClass: "toast-bottom-right",
                      timeout: 3000

                  };
                  toastr["<%=toastType%>"]("<%=toastMessage%>");
        <%
            }
        %>
    </script>
</body>
</html>