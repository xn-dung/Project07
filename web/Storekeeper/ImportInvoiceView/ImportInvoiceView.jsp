<%@page import="java.util.TimeZone"%>
<%@page import="model.Supplier"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Create Import Invoice</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Storekeeper/ImportInvoiceView/ImportInvoiceView.css?v=2"/>
        <script>
            function isValidNumber(value) {
                value = value.trim();
                return /^(\d+(\.\d+)?)$/.test(value);
            }

            function updateTotal() {
                let rows = document.querySelectorAll("#orderTable tbody tr");
                let total = 0;
                let invalid = false;

                rows.forEach(row => {
                    let priceInput = row.querySelector(".unitPrice input");
                    let qtyInput = row.querySelector(".quantity input");

                    let price = priceInput.value.trim();
                    let qty = qtyInput.value.trim();


                    if (!isValidNumber(price)) {
                        invalid = true;
                        return;
                    } else {
                        priceInput.style.border = "";
                    }

                    if (!isValidNumber(qty)) {
                        invalid = true;
                        return;
                    } else {
                        qtyInput.style.border = "";
                    }

                    price = parseFloat(price);
                    qty = parseFloat(qty);
                    total += price * qty;
                });

                document.getElementById("totalPrice").innerText = total.toLocaleString('en-US', {minimumFractionDigits: 1, maximumFractionDigits: 1});

                if (invalid) {
                    console.warn("Có ô nhập không hợp lệ (chứa ký tự hoặc rỗng).");
                }
            }

            document.addEventListener("DOMContentLoaded", function () {
                const rows = document.querySelectorAll("#orderTable tbody tr");
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
                        btn.type = "button";
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
                updateTotal();
            });

        </script>
    </head>
    <body>
        <div id="importNavbar" class="navbar">
            <div class="nav-left">
                <a href="${pageContext.request.contextPath}/Storekeeper/MainStorekeeperView/MainStorekeeperView.jsp" class="nav-link">Home</a>
                <form action="${pageContext.request.contextPath}/SearchProductServlet" method="get" style="display:inline;">
                    <button type="submit" class="nav-btn">Back</button>
                </form>
            </div>
        </div>

        <div class="container">
            <h1>Import Invoice Home</h1>

            <div class="info-row">
                <div class="info-item">
                    <label for="date">Date:</label>
                    <%
                        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy");
                        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
                        String today = sdf.format(new Date());
                    %>
                    <input type="text" id="date" name="date" value="<%=today%>" readonly>
                </div>

                <div class="info-item">
                    <label for="supplier">Supplier:</label>
                    <%
                        Supplier su = (Supplier) session.getAttribute("supplier");
                        String suname = su.getName();
                    %>
                    <input type="text" id="supplier" name="supplier" value="<%= suname%>" readonly>
                </div>
            </div>

            <form action="${pageContext.request.contextPath}/ImportInvoiceServlet" method="post">
                <div class="table-container">
                    <table id="orderTable">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Unit Price</th>
                                <th>Quantity</th>
                                <th>Category</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<Product> list = (List<Product>) session.getAttribute("importedProducts");
                                if (list != null) {
                                    for (Product b : list) {
                            %>
                            <tr>
                                <td><%= b.getId() %></td>
                                <td><%= b.getName()%></td>
                                <td class="unitPrice">
                                    <input type="text" name="unitPrice" value="<%= b.getUnitPrice()%>" min="0" step="any" onchange="updateTotal()" inputmode="decimal">
                                </td>
                                <td class="quantity">
                                    <input type="text" name="quantity" value="1" min="1" onchange="updateTotal()">
                                </td>
                                <td><%= b.getCategory().getName()%></td>
                            </tr>
                            <%
                                
                                    }
                                }
                            %>
                        </tbody>
                    </table>
                </div>

                <div class="total">
                    <strong>Total: <span id="totalPrice">0.00</span>VND</strong>
                </div>

                <div class="actions">
                    <button type="submit">Create</button>
                </div>
            </form>
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
