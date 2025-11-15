<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Supplier"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Search Supplier</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Storekeeper/SearchSupplierView/SearchSupplierView.css?v=2"/>
        <script>
            function SelecteSupplier(row) {
                document.querySelectorAll("table tr.selected").forEach(r => r.classList.remove("selected"));
                row.classList.add("selected");

                const id = row.cells[0].innerText;
                const name = row.cells[1].innerText;
                const address = row.cells[2].innerText;
                const email = row.cells[3].innerText;
                const tel = row.cells[4].innerText;

                const form = document.createElement("form");
                form.method = "post";
                form.action = "${pageContext.request.contextPath}/SearchSupplierServlet";

                const fields = {id, name, address, email, tel};
                for (let key in fields) {
                    const input = document.createElement("input");
                    input.type = "hidden";
                    input.name = key;
                    input.value = fields[key];
                    form.appendChild(input);
                }

                document.body.appendChild(form);
                form.submit();
            }

            document.addEventListener("DOMContentLoaded", function () {
                const rows = document.querySelectorAll(".supplier-table tbody tr");
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
            <a href="${pageContext.request.contextPath}/Storekeeper/MainStorekeeperView/MainStorekeeperView.jsp" class="nav-link">Home</a>
            <div class="nav-right">
                <form action="${pageContext.request.contextPath}/AddSupplierServlet" method="get" style="display:inline;">
                    <button type="submit" class="add-btn">Add New Supplier</button>
                </form>
            </div>
        </div>

        <div class="container">
            <h1 class="page-title">Search Supplier Home</h1>

            <form action="${pageContext.request.contextPath}/SearchSupplierServlet" method="get" class="search-bar">
                <input type="text" name="supplierName" placeholder="Type supplier's name..." class="search-input">
                <button type="submit" class="search-button">Search</button>
            </form>

            <div class="table-container">
                <h2 class="section-title">List of Suppliers</h2>
                <table class="supplier-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Address</th>
                            <th>Email</th>
                            <th>Phone</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            ArrayList<Supplier> list = (ArrayList<Supplier>) session.getAttribute("listSupplier");
                            if (list != null && list.size() > 0) {
                                for (int i = 0; i < list.size(); i++) {
                                    Supplier s = list.get(i);
                        %>
                        <tr onclick='SelecteSupplier(this)'>
                            <td><%= s.getId()%></td>
                            <td><%= s.getName()%></td>
                            <td><%= s.getAddress()%></td>
                            <td><%= s.getEmail()%></td>
                            <td><%= s.getTel()%></td>
                        </tr>
                        <%      }
                        } else {
                        %>
                        <tr>
                            <td colspan="5" class="no-result">No supplier information found.</td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
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
