<%-- 
    Document   : usuario-que-mas-ganancias-en-intervalo-de-tiempo
    Created on : 1/09/2021, 20:28:10
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
        <title>Usuario que registra mas ganancias</title>
    </head>
    <body>
        <%
            ResultSet ventasDeUsuario = (ResultSet) request.getAttribute("ventasDeUsuario");
            String nombreVendedor = String.valueOf(request.getAttribute("nombreMayorVendedor"));
            String gananciasDelVendedor = String.valueOf(request.getAttribute("gananciasMayorVendedor"));
            String fecha1 = String.valueOf(request.getAttribute("fecha1"));
            String fecha2 = String.valueOf(request.getAttribute("fecha2"));
            //los string pueden estar nuelos asi que si lo etan les damos un valor para que se vea clara la informacion
            if (nombreVendedor == null || nombreVendedor.equals("null")) {
                nombreVendedor = "No se registraron ventas en estas fechas";
                gananciasDelVendedor = "0";
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
                            <button type="submit" class="btn btn-success mt-3 me-3" name="btnGananciasVendedor">Consultar ganancias</button>

                        </form>    
                        <button onclick="crearCsv('Ganancias de vendedor en intervalo de tiempo <%=Reporte.obtenerFechaYHoraActual()%>.csv')" class="btn btn-success mt-3 me-3">Descargar tabla en CSV</button>
                    </div>

                    <table class="table table-striped">
                        <tr>                               
                            <th scope="col">Usuario que roporto mas ganancias del <%=fecha1%> al <%=fecha2%></th>
                        </tr>
                        <tr>                               
                            <th scope="col">Nombre de vendedor que mas ganancias reporta</th>
                            <th scope="col">Ganancias que reporto el vendedor</th>
                        </tr>
                        <tr>
                            <td><%=nombreVendedor%></td>
                            <td><%=gananciasDelVendedor%></td>
                        </tr>
                        <tr>                               
                            <th scope="col">Usuario vendedor</th>
                            <th scope="col">Factura generada</th>
                            <th scope="col">Nit en la factura</th>
                            <th scope="col">Nombre del cliente</th>
                            <th scope="col">Direccion del cliente</th>
                            <th scope="col">Ensamble vendido</th>
                            <th scope="col">Nombre del mueble vendido</th>
                            <th scope="col">Precio del mueble vendido</th>
                            <th scope="col">Fecha de venta</th>
                        </tr>
                        <%
                            try {
                                while (ventasDeUsuario.next()) {//exploramos el resultset con los parametros que sabemos que vendran
                                    out.print("<tr>");                  //creamos las nuevas tuplas
                                    out.print("<td>" + ventasDeUsuario.getString("usuario_vendedor") + "</td>");
                                    out.print("<td>" + ventasDeUsuario.getInt("codigo_de_factura") + "</td>");
                                    out.print("<td>" + ventasDeUsuario.getString("nit") + "</td>");
                                    out.print("<td>" + ventasDeUsuario.getString("nombre") + "</td>");
                                    out.print("<td>" + ventasDeUsuario.getString("direccion") + "</td>");
                                    out.print("<td>" + ventasDeUsuario.getInt("codigo_de_ensamble") + "</td>");
                                    out.print("<td>" + ventasDeUsuario.getString(7) + "</td>");
                                    out.print("<td>" + ventasDeUsuario.getDouble("precio") + "</td>");
                                    out.print("<td>" + ventasDeUsuario.getString("fecha_de_compra") + "</td>");
                                    out.print("</tr>");
                                }
                            } catch (NullPointerException ex) {//el resultset puede estar nulo asi capturamos esa excepsion

                            }
                        %>
                    </table>                   
                </div>
            </div>
        </div>
    </body>
</html>
