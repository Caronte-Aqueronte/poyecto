<%-- 
    Document   : registro-de-piezas
    Created on : 21/08/2021, 23:24:35
    Author     : Luis Monterroso
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <link href='https://unpkg.com/boxicons@2.0.9/css/boxicons.min.css' rel='stylesheet'>
        <title>Registro de piezas</title>
    </head>
    <body>
        <%
            //el siguiente codigo va en todos los bodyes de las paginas en donde queremos que no se entre si el usuario no esta registrado
            HttpSession sesion = request.getSession();
            if (sesion.getAttribute("log") == null || sesion.getAttribute("log").equals("0") || !sesion.getAttribute("puesto").equals("Fábrica")) {
                response.sendRedirect("index.jsp");
            }
        %>
        <nav class="navbar navbar-light" style="background-color: #393e41;">
            <form class="container-fluid justify-content-start">
                <a href="menu-principal-fabrica.jsp" class="btn btn-outline-light me-3"><i class='bx bx-log-out-circle' style='color:#ffffff'  ></i> Home</a>
                <a href="informacion-de-piezas.jsp" class="btn btn-outline-light me-3"><i class='bx bx-log-out-circle' style='color:#ffffff'  ></i> Regresar</a>
            </form>
        </nav> 
        <div class="container mt-5">
            <div class="row">
                <div class="col-sm">
                    <form action="registro-de-piezas.jsp" method="POST">
                        <div class="mb-3">
                            <input type="text" class="form-control" name="nombre" placeholder="Nombre de la pieza" required="">
                        </div>
                        <div class="mb-3">
                            <input type="number" step="0.01" class="form-control" name="precio" placeholder="Precio de la pieza" required="">
                        </div>
                        <div class="mb-3">
                            <input type="number" class="form-control" name="existencias" placeholder="Existencias de la pieza" required="">                            
                        </div>
                        <button type="submit" class="btn btn-primary" name="btnGuardar"><i class='bx bx-save' style='color:#ffffff' ></i> Guardar Pieza</button>
                    </form>
                </div> 
            </div> 
        </div>
        <div class="container mt-3">
            <div class="row">
                <div class="col-sm">
                        <jsp:include page="/IngresoDePieza"/>
                </div> 
            </div> 
        </div>
    </body>
</html>
