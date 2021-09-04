<%-- 
    Document   : eliminar-pieza
    Created on : 23/08/2021, 00:24:34
    Author     : Luis Monterroso
--%>

<%@page import="AreaDeFabrica.Pieza"%>
<%@page import="AreaDeFabrica.GestorDePieza"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            HttpSession sesion = request.getSession();
            if (sesion.getAttribute("log") == null || sesion.getAttribute("log").equals("0") || !sesion.getAttribute("puesto").equals("Fábrica")) {
                response.sendRedirect("index.jsp");
            } else {
                try {
                    GestorDePieza gestor = new GestorDePieza();
                    Pieza pieza = new Pieza(request.getParameter("nombre"), Double.parseDouble(request.getParameter("precio")), 0);
                    gestor.eliminarPieza(pieza);
                } catch (Exception e) {
                }
                response.sendRedirect("informacion-de-piezas.jsp");
            }
        %>
    </body>
</html>
