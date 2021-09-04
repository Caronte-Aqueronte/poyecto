<%-- 
    Document   : factura
    Created on : 26/08/2021, 21:23:33
    Author     : Luis Monterroso
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="AreaDeVentas.GestorDeStock"%>
<%@page import="AreaDeVentas.ProductoEnVenta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <title>Factura</title>
    </head>
    <body>
        <nav class="navbar navbar-light" style="background-color: #393e41;">
            <form class="container-fluid justify-content-start">
                <a href="/MiMuebleria/Area-de-ventas/menu-principal-ventas.jsp" class="btn btn-outline-light me-3">Terminar</a>
            </form>
        </nav> 
        <h1>FACTURA</h1>
        <h4>Codigo de factura:</h4>
        <h4><%=request.getParameter("codigoFactura")%></h4>
        <h4>NIT:</h4>
        <h4><%=request.getParameter("codigoDeCliente")%></h4>
        <h4>Nombre:</h4>
        <h4><%=request.getParameter("nombreCliente")%></h4>
        <h4>Direccion:</h4>
        <h4><%=request.getParameter("direccion")%></h4>
        <h4>Usted fue atendido por:</h4>
        <h4><%=request.getParameter("vendedor")%></h4>
        <h4>Detalle:</h4>
        <div style="float:left">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th scope="col"><h5>Productos disponibles</h5></th>
                    </tr>
                    <tr>    
                        <th scope="col">Codigo de ensamble</th>
                        <th scope="col">Nombre de mueble</th>
                        <th scope="col">Precio</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <%
                            for (ProductoEnVenta item : GestorDeStock.carrito) {
                        %>     
                        <td><%=item.getCodigoDeEnsamble()%></td>
                        <td><%=item.getCodigoMueble()%></td>
                        <td><%=item.getPrecio()%></td>
                        <%}
                        ArrayList<ProductoEnVenta> carritoVacio = new ArrayList<>();
                        GestorDeStock.carrito = carritoVacio;
                        %>
                    </tr>
                </tbody>
            </table>
        </div>
    </body>
</html>
