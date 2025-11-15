<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign in</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/LoginView/LoginView.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
 <div class="login-container">
     <h2 class="login-title">
          <strong>Welcome Staff</strong>
     </h2>
     <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
        <label>Username</label>
        <input
          name="username"
          class="login-input"
          type="text"
          placeholder="username"
          required
        />
        <label>Password</label>
        <input
          name="password"
          class="login-input"
          type="password"
          placeholder="password"
          required
        />
        <button class="login-btn" type="submit">
          Login
        </button>
        <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
            <% if (errorMessage != null) { %>
                <div class="login-error"><%= errorMessage %></div>
            <% } 
        %>
      </form>
      <a class="login-register-link" href="${pageContext.request.contextPath}/Customer/RegisterView/RegisterView.jsp">
          Register new account
        </a>
 </div>
          <link rel="stylesheet"
                href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
              <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
              <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>

              <script>
                  <% 
                      String toastMessage = (String) session.getAttribute("toastMessage");
                      String toastType = (String) session.getAttribute("toastType");
                      if(toastMessage != null && toastType != null){
                            session.removeAttribute("toastMessage");
                            session.removeAttribute("toastType");
                  %>
                      toastr.options = {
                          closeButton : true,
                          progressBar : true,
                          positionClass : "toast-bottom-right",
                          timeout : 3000

                      };
                      toastr["<%=toastType%>"]("<%=toastMessage%>");
                  <% 
                      }
                  %>
              </script>
</body>
</html>