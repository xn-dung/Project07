<%-- 
    Document   : register
    Created on : Aug 16, 2025, 10:33:29?AM
    Author     : dungc
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Register</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Customer/RegisterView/RegisterView.css"/>
    </head>
    <body>
        <div class="register-container">
            <form action="${pageContext.request.contextPath}/RegisterServlet" method="post">
              <h2 class="register-title">Register Form</h2>
              <label>Username: </label>
              <input
                class="register-input"
                type="text"
                name ="username"
                placeholder="Type your username"
                required
              />
              <label>Password: </label>
              <input
                class="register-input"
                type="password"
                name="password"
                placeholder="type your password"
                required
              />
              <br />
              <label>Full Name: </label>
              <input
                class="register-input"
                type="text"
                name="name"
                placeholder="type your full name"
                required
              />
              <label>Address: </label>
              <input
                class="register-input"
                type="text"
                name="address"
                placeholder="type your address"
                required
              />
              <br />
              <label>Birth: </label>
              <input
                class="register-input"
                type="date"
                name="birth"
                required
              />
              <label>Email: </label>
              <input
                class="register-input"
                type="email"
                name="email"
                placeholder="Type your email"
              />
              <br/>
              <label>Phone Number: </label>
              <input
                class="register-input"
                type="tel"
                name="tel"
                placeholder="Type your phone number"
                pattern="[0-9]+"
                oninput="this.value = this.value.replace(/[^0-9]/g, '')"
                required
              />
              <label>ID Card </label>
              <input
                class="register-input"
                type="text"
                name="customerId"
                placeholder="Type your card id"
                required
              />
              <hr />
              <button class="register-btn" type="submit">
                Submit
              </button>
                <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
                    <% if (errorMessage != null) { %>
                        <div class="register-error"><%= errorMessage %></div>
                    <% } 
                %>
            </form>
          </div>
    </body>
</html>
