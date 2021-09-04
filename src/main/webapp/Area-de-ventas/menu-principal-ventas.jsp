<%-- 
    Document   : Punto-de-venta
    Created on : 18/08/2021, 16:16:47
    Author     : Luis Monterroso
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <title>Punto de venta</title>
    </head>
    <body>
        <%
            //el siguiente codigo va en todos los bodyes de las paginas en donde queremos que no se entre si el usuario no esta registrado
            HttpSession sesion = request.getSession();
            if (sesion.getAttribute("log") == null || sesion.getAttribute("log").equals("0")|| !sesion.getAttribute("puesto").equals("Punto de venta")) {
                response.sendRedirect("/MiMuebleria/index.jsp");
            }
        %>
         <nav class="navbar navbar-light" style="background-color: #393e41;">
                <form class="container-fluid justify-content-start">
                    <a href="nueva-venta.jsp" class="btn btn-outline-light me-3">Nueva venta</a>
                    <a href="devolucion.jsp" class="btn btn-outline-light me-3">Devolución</a>
                    <a href="consultas.jsp" class="btn btn-outline-light me-3">Consultas</a>
                    <button type="submit" name="btnCerrar" class="btn btn-danger float-right">Cerrar sesión</button>
               </form>
        </nav> 
    </body>
</html>
