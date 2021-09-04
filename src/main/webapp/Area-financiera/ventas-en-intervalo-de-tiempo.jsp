<%-- 
    Document   : ventas-en-intervalo-de-tiempo
    Created on : 31/08/2021, 17:21:36
    Author     : Luis Monterroso
--%>

<%@page import="AreaFinanciera.Reporte"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="/MiMuebleria/ExportarACsv.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <link href='https://unpkg.com/boxicons@2.0.9/css/boxicons.min.css' rel='stylesheet'>
        <title>Ventas en intervalo de tiempo</title>
    </head>
    <body>
        <%
            ResultSet resultado = (ResultSet) request.getAttribute("tabla");
            String fecha1 = String.valueOf(request.getAttribute("fecha1"));
            String fecha2 = String.valueOf(request.getAttribute("fecha2"));
            if (fecha1.equals("null")) {//si esta nulo enotnces les damos un valor
                fecha1 = "-";
                fecha2 = "-";
            }
        %>
        <jsp:include page="nav-regresar.jsp"/>
        <div class="container mt-5">
            <div class="row">
                <div class="col-sm">
                    <div style="float: left; width: 600px; height: 250px;  margin: 30px">
                        <form method="post" action="/MiMuebleria/Consultas">
                            <jsp:include page="fechas.jsp"/>
                            <button type="submit" class="btn btn-success mt-3 me-3" name="btnVentas">Consultar ventas</button>
                        </form>    
                        <button onclick="crearCsv('Ventas en intervalo de tiempo <%=Reporte.obtenerFechaYHoraActual()%>.csv')" class="btn btn-success mt-3 me-3">Descargar tabla en CSV</button>
                    </div>

                    <table class="table table-striped">
                        <tr>                               
                            <th scope="col">Ventas registradas del <%=fecha1%> al <%=fecha2%></th>
                        </tr>
                        <tr>                               
                            <th scope="col">Codigo de factura</th>
                            <th scope="col">Nit del cliente</th>
                            <th scope="col">Nombre del cliente</th>
                            <th scope="col">Dirección del cliente</th>
                            <th scope="col">Codigo de ensamble vendido</th>
                            <th scope="col">Mueble Vendido</th>
                            <th scope="col">Precio del mueble vendido</th>
                            <th scope="col">Fecha de venta</th>
                        </tr>
                        <%
                            try {
                                while (resultado.next()) {
                                    out.print("<tr>");
                                    out.print("<td>" + resultado.getInt("codigo_de_factura") + "</td>");
                                    out.print("<td>" + resultado.getString("nit") + "</td>");
                                    out.print("<td>" + resultado.getString("nombre") + "</td>");
                                    out.print("<td>" + resultado.getString("direccion") + "</td>");
                                    out.print("<td>" + resultado.getInt("codigo_de_ensamble") + "</td>");
                                    out.print("<td>" + resultado.getString(6) + "</td>");
                                    out.print("<td>" + resultado.getDouble("precio") + "</td>");
                                    out.print("<td>" + resultado.getString("fecha_de_compra") + "</td>");
                                    out.print("</tr>");
                                }
                            } catch (NullPointerException ex) {

                            }
                        %>
                    </table>                   
                </div>
            </div>
        </div>
    </body>
</html>
