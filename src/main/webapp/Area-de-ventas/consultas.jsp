<%-- 
    Document   : consultas
    Created on : 29/08/2021, 21:28:35
    Author     : Luis Monterroso
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <link href='https://unpkg.com/boxicons@2.0.9/css/boxicons.min.css' rel='stylesheet'>
        <title>Consultas</title>
    </head>
    <body>
        <jsp:include page="nav-regresar.jsp"/>
        <%
            ResultSet resultado = (ResultSet) request.getAttribute("resultado");
            ArrayList<String> columnas = (ArrayList<String>) request.getAttribute("columnas");
            if (columnas == null) {
                columnas = new ArrayList<String>();
            }
        %>
        <div class="container row g-3 align-items-center mt-1" style="float: left; width: 600px; height: 350px;  margin: 30px">
            <form action="/MiMuebleria/ControladorConsultas" method="post">
                <div class="col-auto">
                    <label  class="col-form-label">NIT del cliente:</label>
                </div>
                <div class="col-auto">
                    <input type="text" name="codigoCliente" class="form-control">
                </div>
                <div class="col-auto">
                    <label  class="col-form-label">Primera fecha:</label>
                </div>
                <div class="col-auto">
                    <input type="date" name="fecha1" class="form-control">
                </div>
                <div class="col-auto">
                    <label  class="col-form-label">Segunda fecha:</label>
                </div>
                <div class="col-auto">
                    <input type="date" name="fecha2" class="form-control">
                </div>
                <div class="col-auto mt-3">
                    <button type="submit" class="btn btn-outline-secondary me-3" name="btnCompras">Consultar compras del cliente</button>
                    <button type="submit" class="btn btn-outline-secondary" name="btnDevoluciones">Consultar devoluciones del cliente</button>
                </div>
            </form>
        </div>
        <div style="float: left; width: 50px; height: 10px"></div>
        <div style="float: left; margin: 10px" class="mt-5">
            <form action="/MiMuebleria/ControladorConsultas" method="post">
                <button type="submit" class="btn btn-outline-secondary me-3" name="btnProductos">Muebles disponibles en la sala de ventas</button>
            </form>
            <form action="/MiMuebleria/ControladorConsultas" method="post">
                <button type="submit" class="btn btn-outline-secondary mt-3" name="btnVentaDeHoy">Ventas de hoy</button>
            </form>
        </div>
        <div style="float: left; width: 50px; height: 10px"></div>
        <div style="float: left; margin: 10px" class="mt-5">
            <form action="/MiMuebleria/ControladorConsultas" method="post">
                <div class="col-auto">
                    <label  class="col-form-label">Codigo de factura:</label>
                </div>
                <div class="col-auto">
                    <input type="number" name="codigoFactura" class="form-control" placeholder="Ingrese codigo de factura">
                </div>
                <button type="submit" class="btn btn-outline-secondary mt-3" name="btnFactura">Buscar factura de un cliente</button>
            </form>
        </div>
        <div class="container" style="width: 1000px; height: 200px">
            <table class="table table-striped">
                <thead>
                    <tr>                               
                        <%
                            for (String item : columnas) {
                        %>
                        <th scope="col"><%=item%></th>
                            <%
                                }
                            %>
                    </tr>
                </thead>
                <tbody>
                    <%
                        try {
                            if (request.getParameter("btnCompras") != null) {//si esto se cumple entonces el usuario preciono el boton de
                                while (resultado.next()) {                  //ventas entoncces extraemos loss valores del resultset
                                    out.print("<tr>" // que queremos
                                            + "<th scope=\"row\">" + resultado.getInt("codigo_de_factura")
                                            + "</th>"
                                            + "<td>" + resultado.getString("nit") + "</td>"
                                            + "<td>" + resultado.getString("nombre") + "</td>"
                                            + "<td>" + resultado.getString("direccion") + "</td>"
                                            + "<td>" + resultado.getString(5) + "</td>"
                                            + "<td>" + resultado.getString("precio") + "</td>"
                                            + "<td>" + resultado.getString("fecha_de_compra") + "</td>"
                                            + "</tr>");
                                }
                            }
                            if (request.getParameter("btnDevoluciones") != null) { //si esto se cumple entonces el usuario preciono el boton de
                                while (resultado.next()) {                         //devoluciones entoncces extraemos loss valores del resultset
                                    out.print("<tr>" // que queremos
                                            + "<th scope=\"row\">" + resultado.getString("codigo_de_cliente")
                                            + "</th>"
                                            + "<td>" + resultado.getString("nombre") + "</td>"
                                            + "<td>" + resultado.getInt("codigo_de_ensamble") + "</td>"
                                            + "<td>" + resultado.getString("codigo_de_mueble") + "</td>"
                                            + "<td>" + resultado.getString("motivo") + "</td>"
                                            + "<td>" + resultado.getString("fecha_de_devolucion") + "</td>"
                                            + "</tr>");
                                }
                            }
                            if (request.getParameter("btnProductos") != null) {
                                while (resultado.next()) {
                                    out.print("<tr>" // que queremos
                                            + "<th scope=\"row\">" + resultado.getInt("codigo_de_ensamble")
                                            + "</th>"
                                            + "<td>" + resultado.getString("codigo_de_mueble") + "</td>"
                                            + "<td>" + resultado.getDouble("precio") + "</td>"
                                            + "</tr>");
                                }
                            }
                            if (request.getParameter("btnVentaDeHoy") != null) {//si esto se cumple entonces el usuario preciono el boton de
                                while (resultado.next()) {                  //ventas entoncces extraemos loss valores del resultset
                                    out.print("<tr>" // que queremos
                                            + "<th scope=\"row\">" + resultado.getInt("codigo_de_factura")
                                            + "</th>"
                                            + "<td>" + resultado.getString("nit") + "</td>"
                                            + "<td>" + resultado.getString("nombre") + "</td>"
                                            + "<td>" + resultado.getString("direccion") + "</td>"
                                            + "<td>" + resultado.getString(5) + "</td>"
                                            + "<td>" + resultado.getString("precio") + "</td>"
                                            + "<td>" + resultado.getString("fecha_de_compra") + "</td>"
                                            + "</tr>");
                                }
                            }
                            if (request.getParameter("btnFactura") != null) {//si esto se cumple entonces el usuario preciono el boton de
                                while (resultado.next()) {                  //ventas entoncces extraemos loss valores del resultset
                                    out.print("<tr>" // que queremos
                                            + "<th scope=\"row\">" + resultado.getInt("codigo_de_factura")
                                            + "</th>"
                                            + "<td>" + resultado.getString("nit") + "</td>"
                                            + "<td>" + resultado.getString("nombre") + "</td>"
                                            + "<td>" + resultado.getString("direccion") + "</td>"
                                            + "<td>" + resultado.getString(5) + "</td>"
                                            + "<td>" + resultado.getString("precio") + "</td>"
                                            + "<td>" + resultado.getString("fecha_de_compra") + "</td>"
                                            + "</tr>");
                                }
                            }
                        } catch (NullPointerException ex) {

                        }
                    %>
                </tbody>
            </table>          
        </div>
    </body>
</html>
