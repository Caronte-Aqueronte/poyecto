<%-- 
    Document   : devoluciones-en-intervalo-de-tiempo
    Created on : 31/08/2021, 22:53:00
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
        <title>Devoluciones en intervalo de tiempo</title>
    </head>
    <body>
        <%
            //obtenemos los valores que nos interesan
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
                            <button type="submit" class="btn btn-success mt-3 me-3" name="btnDevoluciones">Consultar devoluciones</button>

                        </form>    
                        <button onclick="crearCsv('Devoluciones en intervalo de tiempo <%=Reporte.obtenerFechaYHoraActual()%>.csv')" class="btn btn-success mt-3 me-3">Descargar tabla en CSV</button>
                    </div>

                    <table class="table table-striped">
                        <tr>                               
                            <th scope="col">Devoluciones registradas del <%=fecha1%> al <%=fecha2%></th>
                        </tr>
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
                            try {
                                while (resultado.next()) {
                                    out.print("<tr>");
                                    out.print("<td>" + resultado.getString("codigo_de_cliente") + "</td>");
                                    out.print("<td>" + resultado.getString("nombre") + "</td>");
                                    out.print("<td>" + resultado.getInt("codigo_de_ensamble") + "</td>");
                                    out.print("<td>" + resultado.getString("codigo_de_mueble") + "</td>");
                                    out.print("<td>" + resultado.getString("motivo") + "</td>");
                                    out.print("<td>" + resultado.getDouble("perdida") + "</td>");
                                    out.print("<td>" + resultado.getString("fecha_de_devolucion") + "</td>");
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
