<%-- 
    Document   : NIT
    Created on : 26/08/2021, 20:43:34
    Author     : Luis Monterroso
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <link href='https://unpkg.com/boxicons@2.0.9/css/boxicons.min.css' rel='stylesheet'>
        <title>Ingresar NIT</title>
    </head>
    <body>
        <nav class="navbar navbar-light" style="background-color: #393e41;">
            <a href="/MiMuebleria/Area-de-ventas/nueva-venta.jsp" class="btn btn-outline-light me-3"><i class='bx bx-log-out-circle' style='color:#ffffff'  ></i> HOME</a>
        </nav>
        <div class="container mt-5" style="width: 700px; height: 200px;">
            <div class="row">
                <div class="col-sm">
                    <form action="/MiMuebleria/ControladorDeCredenciales" method="post">
                        <label>Ingrese NIT:</label>
                        <div class="mb-3 mt-2">
                            <input type="text" class="form-control" name="nit" placeholder="Ingrese NIT" required="">
                        </div>
                        <button type="submit" class="btn btn-primary" name="btnAgregar"><i class='bx bxs-cart-add' style='color:#ffffff'  ></i> Confimar compra</button>
                    </form>                 
                </div> 
            </div> 
        </div>
    </body>
</html>
