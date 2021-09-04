<%-- 
    Document   : informacion-de-ensambles
    Created on : 21/08/2021, 19:33:54
    Author     : Luis Monterroso
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <link href='https://unpkg.com/boxicons@2.0.9/css/boxicons.min.css' rel='stylesheet'>
        <title>Ensambles</title>
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
                <a href="menu-principal-fabrica.jsp" class="btn btn-outline-light me-3">Regresar</a>
            </form>
        </nav> 
        <div class="container mt-2">
            <div class="row">
                <div class="col-sm">
                    <h2>Muebles ensamblados</h2>
                </div>
            </div>
        </div>
        <div class="container mt-2">
            <div class="row">
                <div class="col-sm">
                    <form action="informacion-de-ensambles.jsp" method="POST">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th></th>
                                    <th scope="col" class="text-center">
                                        <input type="submit" value="Ordenar lista de forma descendente" name="btnDescendente" class="btn btn-outline-success" />
                                    </th>
                                    <th scope="col" class="text-center">
                                        <input type="submit" value="Ordenar lista de forma ascendente" name="btnAscendente" class="btn btn-outline-success" />
                                    </th>
                                    <th scope="col">
                                        <a href="ensamblar-muebles.jsp" class="btn btn btn-outline-success me-3 mt-2"><i class='bx bxs-message-alt-add' style='color:#21a42b'></i> 
                                            Nuevo Ensamble
                                        </a>    
                                    </th>
                                </tr>
                                <tr>                               
                                    <th scope="col">Usuario ensamblador</th>
                                    <th scope="col">Codigo de ensamble</th>
                                    <th scope="col">Mueble ensamblado</th>
                                    <th scope="col">Fecha de ensamble</th>
                                    <th scope="col">Aprobación</th>
                                    <th scope="col">Aprobar</th>
                                </tr>
                            </thead>
                            <tbody>
                                <jsp:include page= "/TablaEnsambles" />
                            </tbody>
                        </table>
                    </form>           
                </div>
            </div>>
        </div>
    </body>
</html>
