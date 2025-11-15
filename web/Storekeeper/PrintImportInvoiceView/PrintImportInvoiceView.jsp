<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="model.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Print Import Invoice</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Storekeeper/PrintImportInvoiceView/PrintImportInvoiceView.css?v=4"/>
    </head>
    <body>
        <div id="importNavbar" class="navbar">
            <div class="nav-left">
                <a href="${pageContext.request.contextPath}/Storekeeper/MainStorekeeperView/MainStorekeeperView.jsp" class="nav-link">Home</a>
            </div>
        </div>

        <div class="container">
            <h1>Import Invoice</h1>
            <%
                ImportInvoice invoice = (ImportInvoice) session.getAttribute("importinvoice");
                if (invoice != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy");
                    String dateStr = sdf.format(invoice.getDate());
                    Supplier supplier = invoice.getSupplier();
                    Staff staff = invoice.getStaff();
                    ArrayList<ImportedProduct> list = invoice.getList();
            %>
            <div class="info-row">
                <div class="info-item"><label>ID:</label> <span><%= invoice.getId()%></span></div>
                <div class="info-item"><label>Date:</label> <span><%= dateStr%></span></div>
                <div class="info-item"><label>Supplier:</label> <span><%= supplier.getName()%></span></div>
                <div class="info-item"><label>Staff:</label> <span><%= staff.getName()%></span></div>
            </div>

            <div class="table-container">
                <table id="printTable">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Product Name</th>
                            <th>Unit Price</th>
                            <th>Quantity</th>
                            <th>Total</th>
                            <th>Category</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            int index = 1;
                            double totalInvoice = 0;
                            for (ImportedProduct ip : list) {
                                Product p = ip.getProduct();
                                double lineTotal = p.getUnitPrice() * ip.getQuantity();
                                totalInvoice += lineTotal;
                        %>
                        <tr>
                            <td><%= index%></td>
                            <td><%= p.getName()%></td>
                            <td><%= String.format("%,.0f", p.getUnitPrice())%> VND</td>
                            <td><%= ip.getQuantity()%></td>
                            <td><%= String.format("%,.0f", lineTotal)%> VND</td>
                            <td><%= p.getCategory().getName()%></td>
                        </tr>
                        <%
                                index++;
                            }
                        %>
                    </tbody>
                </table>
            </div>

            <div class="total">
                <strong>Total: <span id="totalInvoice"><%= String.format("%,.0f", totalInvoice)%> VND</span></strong>
            </div>

            <div class="actions">
                <button id="printBtn" type="button">Print</button>
            </div>
            <% }%>
        </div>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.10.1/html2pdf.bundle.min.js"></script>
        <script>
            document.getElementById("printBtn").addEventListener("click", () => {
                window.onafterprint = function () {
                    window.location.href = "${pageContext.request.contextPath}/Storekeeper/MainStorekeeperView/MainStorekeeperView.jsp";
                };
                window.print();
            });
        </script>
    </body>
</html>
