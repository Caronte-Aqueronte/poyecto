<%-- 
    Document   : credenciales-cliente-sin-registro
    Created on : 26/08/2021, 22:07:35
    Author     : Luis Monterroso
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <link href='https://unpkg.com/boxicons@2.0.9/css/boxicons.min.css' rel='stylesheet'>
        <title>Credenciales de cliente</title>
    </head>
    <body>
        <nav class="navbar navbar-light" style="background-color: #393e41;">
        </nav>
        <div class="container mt-5" style="width: 700px; height: 200px;">
            <div class="row">
                <div class="col-sm">
                    <form action="/MiMuebleria/GuardarCliente" method="post">
                        <label>Ingrese Nombre del cliente:</label>
                        <div class="mb-3 mt-2">
                            <input type="text" class="form-control" name="nombre" placeholder="Nombre" required="">
                        </div>
                         <label>Direccion del cliente:</label>
                        <div class="mb-3 mt-2">
                            <input type="text" class="form-control" name="direccion" placeholder="Direccion" required="">
                        </div>
                         <div class="mb-3 mt-2">
                             <input type="hidden" class="form-control" name="codigoDeCliente" required="" value="<%=request.getParameter("codigoDeCliente")%>">
                        </div>
                        <button type="submit" class="btn btn-primary" name="btnAgregar"><i class='bx bxs-cart-add' style='color:#ffffff'  ></i> Confimar compra</button>
                    </form>                 
                </div> 
            </div> 
        </div>
    </body>
</html>
