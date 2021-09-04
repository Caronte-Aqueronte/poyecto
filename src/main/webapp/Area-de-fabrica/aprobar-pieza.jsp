<%-- 
    Document   : aprobar-pieza
    Created on : 23/08/2021, 01:30:14
    Author     : Luis Monterroso
--%>

<%@page import="AreaDeFabrica.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            GestorDeEnsamble gestor = new GestorDeEnsamble();
            gestor.registrarMuebleEnSalaDeVentas(request.getParameter("codigo"));
            response.sendRedirect("informacion-de-ensambles.jsp");        
        %>
    </body>
</html>
