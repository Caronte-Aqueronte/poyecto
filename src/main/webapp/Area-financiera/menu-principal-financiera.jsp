<%-- 
    Document   : Area-fiinanciera
    Created on : 18/08/2021, 16:17:30
    Author     : Luis Monterroso
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj" crossorigin="anonymous"></script>
        <title>Área financiera</title>
    </head>
    <body>
        <%
            //el siguiente codigo va en todos los bodyes de las paginas en donde queremos que no se entre si el usuario no esta registrado
            HttpSession sesion = request.getSession();
            if (sesion.getAttribute("log") == null || sesion.getAttribute("log").equals("0") || !sesion.getAttribute("puesto").equals("Área financiera")) {
                response.sendRedirect("index.jsp");
            }
        %>
        <nav class="navbar navbar-light" style="background-color: #393e41;">
            <form class="container-fluid justify-content-start">
                <div class="dropdown me-3">
                    <button class="btn btn-outline-light dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                        Consultas
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                        <li><a href="ventas-en-intervalo-de-tiempo.jsp" class="dropdown-item">Ventas en un intervalo de tiempo</a></li>
   
                        <li><a href="devoluciones-en-intervalo-de-tiempo.jsp" class="dropdown-item">Devoluciones en un intervalo de tiempo</a></li>
                 
                        <li><a href="ganancias-en-intervalo-de-tiempo.jsp" class="dropdown-item">Reporte de ganancias en un intervalo de tiempo</a></li>
                 
                        <li><a href="usuario-con-mas-ventas-en-intervalo-de-tiempo.jsp" class="dropdown-item">Usuario que registra más ventas en un intervalo de tiempo</a></li>
                     
                        <li><a href="usuario-que-mas-ganancias-en-intervalo-de-tiempo.jsp" class="dropdown-item">Usuario que registra más ganancias en un intervalo de tiempo</a></li>
     
                        <li><a href="mueble-mas-vendido.jsp" class="dropdown-item">Mueble más vendido en un intervalo de tiempo</a></li>

                        <li><a href="mueble-menos-vendido.jsp" class="dropdown-item">Mueble menos vendido en un intervalo de tiempo</a></li>
                    </ul>
                </div>
                <div class="dropdown me-3">
                    <button class="btn btn-outline-light dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                        Creaciones
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                        <li><a href="nueva-venta.jsp" class="dropdown-item">Crear nuevo mueble</a></li>
                        <li><a href="nueva-venta.jsp" class="dropdown-item">Crear/cancelar usuario</a></li>
                    </ul>
                </div>
                <button type="submit" name="btnCerrar" class="btn btn-danger float-right">Cerrar sesión</button>
            </form>  
        </nav>
    </body>
</html>
