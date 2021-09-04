<%-- 
    Document   : informacion-de-piezas
    Created on : 19/08/2021, 19:35:27
    Author     : Luis Monterroso
--%>

<%@page import="AreaDeFabrica.GestorDePieza"%>
<%@page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <link href='https://unpkg.com/boxicons@2.0.9/css/boxicons.min.css' rel='stylesheet'>
        <title>Piezas</title>
    </head>
    <body>
        <%
            //el siguiente codigo va en todos los bodyes de las paginas en donde queremos que no se entre si el usuario no esta registrado
            HttpSession sesion = request.getSession();
            if (sesion.getAttribute("log") == null || sesion.getAttribute("log").equals("0") || !sesion.getAttribute("puesto").equals("Fábrica")) {
                response.sendRedirect("index.jsp");
            }
        %>
        <%--Esta es la barra de navegacion solo ocntiene el boton para regresar--%>
        <nav class="navbar navbar-light" style="background-color: #393e41;">
            <a href="menu-principal-fabrica.jsp" class="btn btn-outline-light me-3"><i class='bx bx-log-out-circle' style='color:#ffffff'  ></i> HOME</a>
        </nav> 
        <div class="container mt-2">
            <div class="row">
                <div class="col-sm">
                    <h2>Piezas en inventario</h2>
                </div>
            </div>
        </div>
        <%--esta es la tabla contenida en contenedoes que la hacen mas peuqena--%>
        <div class="container mt-2">
            <div class="row">
                <div class="col-sm">
                    <form action="informacion-de-piezas.jsp" method="POST">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th scope="col" class="text-center">
                                        <input type="submit" value="Ordenar lista de forma descendente" name="btnDescendente" class="btn btn-outline-success" />
                                    </th>
                                    <th scope="col" class="text-center">
                                        <input type="submit" value="Ordenar lista de forma ascendente" name="btnAscendente" class="btn btn-outline-success" />
                                    </th>
                                    <th scope="col">
                                        <a href="registro-de-piezas.jsp" class="btn btn btn-outline-success me-3 mt-2"><i class='bx bxs-message-alt-add' style='color:#21a42b'></i> 
                                            Nueva Pieza
                                        </a>    
                                    </th>
                                </tr>
                                <tr>                               
                                    <th scope="col">Nombre</th>
                                    <th scope="col">Precio</th>
                                    <th scope="col">Existencias</th>
                                    <th scope="col">Editar/Eliminar registro</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%try {
                                %>
                                <jsp:include page="/TablaPieza"/>
                                <%
                                    } catch (Exception e) {
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
