<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Product" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Search Product</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Storekeeper/SearchProductView/SearchProductView.css">
        <script>
            function toggleProductSelection(checkbox) {
                const xhr = new XMLHttpRequest();
                xhr.open("POST", "${pageContext.request.contextPath}/SearchProductServlet", true);
                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                xhr.send("productId=" + checkbox.value + "&checked=" + checkbox.checked);
            }

            document.addEventListener("DOMContentLoaded", function () {
                const rows = document.querySelectorAll("table tr:not(:first-child)");
                const pageSize = 15;
                let currentPage = 1;
                const totalPages = Math.ceil(rows.length / pageSize);
                const tableContainer = document.querySelector(".table-container");
                const pagination = document.createElement("div");
                pagination.classList.add("pagination");
                tableContainer.appendChild(pagination);

                function showPage(page) {
                    currentPage = page;
                    rows.forEach((row, i) => {
                        row.style.display = (i >= (page - 1) * pageSize && i < page * pageSize) ? "" : "none";
                    });
                    renderPagination(totalPages, currentPage);
                }

                function renderPagination(totalPages, currentPage) {
                    pagination.innerHTML = "";
                    const createBtn = (page) => {
                        const btn = document.createElement("button");
                        btn.textContent = page;
                        btn.dataset.page = page;
                        if (page === currentPage)
                            btn.classList.add("active");
                        btn.onclick = () => showPage(page);
                        return btn;
                    };
                    const createEllipsis = (jumpPage) => {
                        const span = document.createElement("span");
                        span.textContent = "...";
                        span.classList.add("ellipsis");
                        span.onclick = () => showPage(jumpPage);
                        return span;
                    };

                    pagination.appendChild(createBtn(1));

                    if (currentPage > 3)
                        pagination.appendChild(createEllipsis(currentPage - 1));

                    for (let i = currentPage - 1; i <= currentPage + 1; i++) {
                        if (i > 1 && i < totalPages)
                            pagination.appendChild(createBtn(i));
                    }

                    if (currentPage < totalPages - 2)
                        pagination.appendChild(createEllipsis(currentPage + 1));

                    if (totalPages > 1)
                        pagination.appendChild(createBtn(totalPages));
                }

                showPage(1);
            });

        </script>
    </head>
    <body>
        <div class="navbar">
            <div class="nav-left">
                <a href="${pageContext.request.contextPath}/Storekeeper/MainStorekeeperView/MainStorekeeperView.jsp" class="nav-link">Home</a>
                <form action="${pageContext.request.contextPath}/SearchSupplierServlet" method="get" style="display:inline;">
                    <button type="submit" class="nav-btn">Back</button>
                </form>
            </div>
            <div class="nav-right">
                <form action="${pageContext.request.contextPath}/AddProductServlet" method="get" style="display:inline;">
                    <button type="submit" class="add-btn">Add New Product</button>
                </form>
            </div>
        </div>

        <div class='container'>
            <h1 class="page-title">Search Product Home</h1>

            <form action="${pageContext.request.contextPath}/SearchProductServlet" method="get" class="search-bar">
                <input type="text" name="productName" placeholder="Type product's name..." class="search-input">
                <button type="submit" class="search-button">Search</button>
            </form>

            <div class='table-container'>    
                <h2>List of Products</h2>

                <table class="product-table">
                    <tr>
                        <th>Select</th>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Unit Price</th>
                        <th>Description</th>
                        <th>Category</th>
                    </tr>
                    <%
                        List<Product> list = (List<Product>) session.getAttribute("listProduct");
                        List<String> selectedIds = (List<String>) session.getAttribute("selectedProducts");
                        if (selectedIds == null) {
                            selectedIds = new ArrayList<>();
                        }
                        if (list != null && list.size() > 0) {
                            for (Product b : list) {
                                boolean checked = selectedIds.contains(String.valueOf(b.getId()));
                    %>
                    <tr>
                        <td>
                            <input type="checkbox" 
                                   value="<%= b.getId()%>" 
                                   <%= checked ? "checked" : ""%>
                                   onchange="toggleProductSelection(this)">
                        </td>
                        <td><%= b.getId()%></td>
                        <td><%= b.getName()%></td>
                        <td><%= b.getUnitPrice()%></td>
                        <td><%= b.getDescription()%></td>
                        <td><%= b.getCategory().getName()%></td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="6" class="no-result">No product information found.</td>
                    </tr>
                    <% } %>
                </table>
            </div>
        </div>

        <form action="${pageContext.request.contextPath}/ImportInvoiceServlet" method="get" style="text-align:center; margin:20px;">
            <button type="submit" class="submit-btn">Submit Import</button>
        </form>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>

        <script>
                                       const togglePassword = document.getElementById("togglePassword");
                                       const passwordInput = document.getElementById("password");
                                       if (togglePassword && passwordInput) {
                                           togglePassword.addEventListener("click", function () {
                                               const type = passwordInput.getAttribute("type") === "password" ? "text" : "password";
                                               passwordInput.setAttribute("type", type);
                                               this.classList.toggle("fa-eye-slash");
                                           });
                                       }
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
