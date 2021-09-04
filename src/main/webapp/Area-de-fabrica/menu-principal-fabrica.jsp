<%-- 
    Document   : Area-de-fabrica
    Created on : 18/08/2021, 16:11:34
    Author     : Luis Monterroso
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="AreaDeFabrica.GestorDePieza"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <title>Fábrica</title>
    </head>
    <body>
        <%
            //el siguiente codigo va en todos los bodyes de las paginas en donde queremos que no se entre si el usuario no esta registrado
            HttpSession sesion = request.getSession();
            if (sesion.getAttribute("log") == null || sesion.getAttribute("log").equals("0") || !sesion.getAttribute("puesto").equals("Fábrica")) {
                 response.sendRedirect("/MiMuebleria/index.jsp");
             }
        %>
        <nav class="navbar navbar-light" style="background-color: #393e41;">
            <form class="container-fluid justify-content-start">
                <a href="informacion-de-piezas.jsp" class="btn btn-outline-light me-3">Información de piezas</a>
                <a href="/MiMuebleria/index.jsp" class="btn btn-outline-light me-3">Libro de indicaciones</a>
                <a href="informacion-de-ensambles.jsp" class="btn btn-outline-light me-3">Información de ensambles</a>
                <button type="submit" name="btnCerrar" class="btn btn-danger float-right">Cerrar sesión</button>
            </form>
        </nav> 
        <div class="container mt-2">
            <div class="row">
                <div class="col-sm">
                    <form action="informacion-de-piezas.jsp" method="POST">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th scope="col" class="text-center">
                                        <h2>Piezas con poca existencia</h2>
                                    </th>
                                </tr>
                                <tr>                               
                                    <th scope="col">Nombre</th>
                                    <th scope="col">Precio</th>
                                    <th scope="col">Existencias</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    GestorDePieza gestor = new GestorDePieza();
                                    ResultSet resultado = null;
                                    resultado = gestor.mostrarPiezasAgotadas();
                                    while (resultado.next()) {
                                        out.print("<tr>"
                                                + "<th scope=\"row\">" + resultado.getString("nombre") + ""
                                                + "</th>"
                                                + "<td>" + resultado.getDouble("precio") + "</td>"
                                                + "<td>" + resultado.getInt("existencias") + "</td>"
                                                + "</tr>");
                                    }
                                %>
                            </tbody>
                        </table>
                    </form>           
                </div>
            </div>
        </div>
    </body>
</html>
