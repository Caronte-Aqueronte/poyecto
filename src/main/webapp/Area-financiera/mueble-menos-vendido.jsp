<%-- 
    Document   : mueble-menos-vendido
    Created on : 1/09/2021, 22:54:24
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
        <title>Mueble menos vendido en intervalo de tiempo</title>
    </head>
    <body>
        <%
            ResultSet ventasDelMuebleEnFechas = (ResultSet) request.getAttribute("ventasDelMuebleEnFechas");
            String nombreDelMueble = String.valueOf(request.getAttribute("nombreDelMueble"));
            String ventasDelMueble = String.valueOf(request.getAttribute("ventasDelMueble"));
            //los string pueden estar nulos asi que si lo etan les damos un valor para que se vea clara la informacion
            if (nombreDelMueble == null) {
                nombreDelMueble = "No se registraron ventas en estas fechas";
                ventasDelMueble = "0";
            }
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
                            <button type="submit" class="btn btn-success mt-3 me-3" name="btnMuebleMenosVendido">Consultar ventas</button>

                        </form>    
                        <button onclick="crearCsv('Mueble menos vendido en intervalo de tiempo <%=Reporte.obtenerFechaYHoraActual()%>.csv')" class="btn btn-success mt-3 me-3">Descargar tabla en CSV</button>
                    </div>

                    <table class="table table-striped">
                        <tr>                               
                            <th scope="col">Mueble menos vendido <%=fecha1%> al <%=fecha2%></th>
                        </tr>
                        <tr>                               
                            <th scope="col">Nombre del mueble menos vendido</th>
                            <th scope="col">Veces que se vendio el mueble</th>
                        </tr>
                        <tr>
                            <td><%=nombreDelMueble%></td>
                            <td><%=ventasDelMueble%></td>
                        </tr>
                        <tr>                               
                            <th scope="col">Descripción de ventas</th>
                        </tr>
                        <tr>                               
                            <th scope="col">Codigo de factura</th>
                            <th scope="col">Codigo de ensamble</th>
                            <th scope="col">Nombre del mueble</th>
                            <th scope="col">Usuario vendedor</th>
                            <th scope="col">Fecha de venta</th>
                        </tr>
                        <%
                            try {
                                while (ventasDelMuebleEnFechas.next()) {//exploramos el resultset con los parametros que sabemos que vendran
                                    out.print("<tr>");                  //creamos las nuevas tuplas
                                    out.print("<td>" + ventasDelMuebleEnFechas.getInt("codigo_de_factura") + "</td>");
                                    out.print("<td>" + ventasDelMuebleEnFechas.getInt("codigo_de_ensamble") + "</td>");
                                    out.print("<td>" + ventasDelMuebleEnFechas.getString("codigo_de_mueble") + "</td>");
                                    out.print("<td>" + ventasDelMuebleEnFechas.getString("usuario_vendedor") + "</td>");
                                    out.print("<td>" + ventasDelMuebleEnFechas.getString("fecha_de_compra") + "</td>");
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
