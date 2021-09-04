<%-- 
    Document   : ganancias-en-intervalo-de-tiempo
    Created on : 1/09/2021, 00:38:02
    Author     : Luis Monterroso
--%>

<%@page import="AreaFinanciera.Reporte"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="/MiMuebleria/ExportarACsv.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <link href='https://unpkg.com/boxicons@2.0.9/css/boxicons.min.css' rel='stylesheet'>
        <title>Ganancias en intervalo de tiempo</title>
    </head>
    <body>
        <%
            ResultSet ventas = (ResultSet) request.getAttribute("tablaVentas");
            ResultSet devoluciones = (ResultSet) request.getAttribute("tablaDevoluciones");
            String totalDeGanancias = String.valueOf(request.getAttribute("gananciasTotales"));
            String dineroEnVentas = String.valueOf(request.getAttribute("dineroEnVentas"));
            String dineroEnDevoluciones = String.valueOf(request.getAttribute("dineroEnDevoluciones"));
            String costoDeProduccion = String.valueOf(request.getAttribute("costoDeProduccion"));
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
                            <button type="submit" class="btn btn-success mt-3 me-3" name="btnGanancias">Consultar ganancias</button>

                        </form>    
                        <button onclick="crearCsv('Ganancias en intervalo de tiempo <%=Reporte.obtenerFechaYHoraActual()%>.csv')" class="btn btn-success mt-3 me-3">Descargar tabla en CSV</button>
                    </div>

                    <table class="table table-striped">
                        <tbody>
                            <tr>                               
                                <th scope="col">Ganancias en intervalo del <%=fecha1%> al <%=fecha2%></th>
                            </tr>
                            <tr>
                                <th scope="col">Dinero en ventas</th>
                                <th scope="col">Dinero en devoluciones</th>
                                <th scope="col">Costo de produccion de los muebles</th>
                                <th scope="col">Total de ganancias</th>                           
                            </tr>
                            <%
                                try {
                                    out.print("<tr>");//creamos la nueva fila con los resultados de la consulta
                                    out.print("<td>" + dineroEnVentas + "</td>");
                                    out.print("<td>" + dineroEnDevoluciones + "</td>");
                                    out.print("<td>" + costoDeProduccion + "</td>");
                                    out.print("<td>" + totalDeGanancias + "</td>");
                                    out.print("</tr>");
                                } catch (NullPointerException ex) {

                                }
                            %>
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
                            <%  try {//el resultado puede estar nulo entonces capturamos la excepcion
                                    while (ventas.next()) {//exploramos el resultado de las ventas en ese intervalo de tiempo
                                        out.print("<tr>");//creamos la nueva fila con los resultados de la consulta
                                        out.print("<td>" + ventas.getInt("codigo_de_factura") + "</td>");
                                        out.print("<td>" + ventas.getString("nit") + "</td>");
                                        out.print("<td>" + ventas.getString("nombre") + "</td>");
                                        out.print("<td>" + ventas.getString("direccion") + "</td>");
                                        out.print("<td>" + ventas.getInt("codigo_de_ensamble") + "</td>");
                                        out.print("<td>" + ventas.getString(6) + "</td>");
                                        out.print("<td>" + ventas.getDouble("precio") + "</td>");
                                        out.print("<td>" + ventas.getString("fecha_de_compra") + "</td>");
                                        out.print("</tr>");
                                    }
                                    System.out.println("se acabo");
                                } catch (NullPointerException ex) {

                                }
                            %>
                            <tr>                               
                                <th scope="col">Nit del cliente</th>
                                <th scope="col">Nombre del cliente</th>
                                <th scope="col">Codigo de ensamble devuelto</th>
                                <th scope="col">Nombre del mueble</th>
                                <th scope="col">Motivo de devolucion</th>
                                <th scope="col">Perdida que reprsenta</th>
                                <th scope="col">Fecha de devolucion</th>
                            </tr>
                            <%
                                try {//el resultado puede estar nulo entonces capturamos la excepcion
                                    while (devoluciones.next()) {//exploramos el resultado de las devoluciones en ese intervalo de tiempo
                                        out.print("<tr>");//creamos la nueva fila con los resultados de la consulta
                                        out.print("<td>" + devoluciones.getString("codigo_de_cliente") + "</td>");
                                        out.print("<td>" + devoluciones.getString("nombre") + "</td>");
                                        out.print("<td>" + devoluciones.getInt("codigo_de_ensamble") + "</td>");
                                        out.print("<td>" + devoluciones.getString("codigo_de_mueble") + "</td>");
                                        out.print("<td>" + devoluciones.getString("motivo") + "</td>");
                                        out.print("<td>" + devoluciones.getDouble("perdida") + "</td>");
                                        out.print("<td>" + devoluciones.getString("fecha_de_devolucion") + "</td>");
                                        out.print("</tr>");
                                    }
                                } catch (NullPointerException ex) {

                                }
                            %> 
                        </tbody>
                    </table>                   
                </div>
            </div>
        </div>
    </body>
</html>
