<%-- 
    Document   : manejo-de-usuarios
    Created on : 2/09/2021, 20:48:06
    Author     : Luis Monterroso
--%>

<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <link href='https://unpkg.com/boxicons@2.0.9/css/boxicons.min.css' rel='stylesheet'>
        <title>Manejo de usuarios</title>
    </head>
    <body>
        <%
            ResultSet usuarios = (ResultSet) request.getAttribute("tablaUsuarios");
        %>
        <jsp:include page="nav-regresar.jsp"/>
        <div class="container mt-5">
            <div class="row">
                <div class="col-sm">
                    <div style="float: left; width: 600px; height: 250px;  margin: 30px">
                        <form method="post" action="/MiMuebleria/Consultas">
                            <label  class="col-form-label">Buscar usuario por nombre de usuario</label>
                            <input type="text" name="txtNombreUsuario" class="form-control">
                            <button type="submit" class="btn btn-success mt-3 me-3" name="btnBuscar">Consultar ventas</button>
                        </form>    
                    </div>
                    <table class="table table-striped">
                        <tr>                               
                            <th scope="col">Nombre del trabajador</th>
                            <th scope="col">Nombre de usuario</th>
                            <th scope="col">Puesto del trabajador</th>
                            <th scope="col">Estado del usuario</th>
                        </tr>
                        <%
                            try {
                                while (usuarios.next()) {
                                    out.print("<tr>");
                                    out.print("<td>" + usuarios.getInt("nombre_de_trabajador") + "</td>");
                                    out.print("<td>" + usuarios.getString("usuario") + "</td>");
                                    out.print("<td>" + usuarios.getString("puesto") + "</td>");
                                    out.print("<td>" + usuarios.getString("estado") + "</td>");
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
