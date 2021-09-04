<%-- 
    Document   : nueva-venta
    Created on : 24/08/2021, 22:28:35
    Author     : Luis Monterroso
--%>

<%@page import="AreaDeVentas.GestorDeStock.*"%>
<%@page import="AreaDeVentas.ProductoEnVenta"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="AreaDeVentas.GestorDeStock"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <link href='https://unpkg.com/boxicons@2.0.9/css/boxicons.min.css' rel='stylesheet'>
        <title>Nueva venta</title>
    </head>
    <body>
        <%
            //aqui obtenemos los valores de la request en caso vengan valores con que rellenar los inputs
            String nombreMueble = request.getParameter("nombreMueble");
            String codigoEnsamble = request.getParameter("codigoEnsamble");
            String precioMueble = request.getParameter("precioMueble");
            //si son nulos los inicializamos
            if (nombreMueble == null || codigoEnsamble == null || precioMueble == null) {
                nombreMueble = "";
                codigoEnsamble = "";
                precioMueble = "";
            }
        %>
        <jsp:include page="nav-regresar.jsp"/>   
        <div class="mt-5">
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
                            <th scope="col">Añadir a carrito</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            GestorDeStock stock = new GestorDeStock();
                            try {
                                ResultSet resultado = stock.verArticulosDisponibles();
                                //esxploramos el resultset y lo agregamos los resultados a la tabla
                                while (resultado.next()) {
                                    out.print("<tr>"
                                            + "<th scope=\"row\">" + resultado.getInt("codigo_de_ensamble")
                                            + "</th>"
                                            + "<td>" + resultado.getString("codigo_de_mueble") + "</td>"
                                            + "<td>" + resultado.getDouble("precio") + "</td>"
                                            + "<td>"
                                            + "<a href=\"/MiMuebleria/LlenarCarrito?nombreMueble=" + resultado.getString("codigo_de_mueble") + ""
                                            + "&codigoEnsamble=" + resultado.getInt("codigo_de_ensamble") + "&precioMueble=" + resultado.getDouble("precio") + "\">"
                                            + "<i class='bx bxs-cart-add' style='color:#17d576'  ></i></a>"
                                            + "</td>"
                                            + "</tr>");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        %>
                    </tbody>
                </table>
            </div>
            <div style="float: left; width: 50px; height: 10px"></div>
            <div style="float:left" class="mt-2">
                <div class="row">
                    <div class="col-sm">
                        <form action="/MiMuebleria/ControladorCarrito" method="post">
                        
                                <h5> Agregar </h5>
                                <div class="mb-3 mt-4">
                                    <input type="number" class="form-control" name="codigoEnsamble" placeholder="Codigo de Ensamble" required="" readonly value="<%=codigoEnsamble%>">
                                </div>
                                <div class="mb-3">
                                    <input type="text" step="0.01" class="form-control" name="codigoMueble" placeholder="Nombre del mueble" required="" readonly value="<%=nombreMueble%>">
                                </div>
                                <div class="mb-3">
                                    <input type="number" step="0.01" class="form-control" name="precio" placeholder="Precio del mueble" required="" readonly value="<%=precioMueble%>">
                                </div>

                            <button type="submit" class="btn btn-primary me-3" name="btnAgregar"><i class='bx bxs-cart-add' style='color:#ffffff'  ></i> Agregar a carrito</button>
                            <a href="/MiMuebleria/Area-de-ventas/NIT.jsp" class="btn btn-success"><i class='bx bx-money' style='color:#ffffff'  ></i> Efectuar compra</a>
                            </fieldset>
                        </form>                      
                    </div> 
                </div> 
            </div>  
            <div style="float: left; width: 50px; height: 20px"></div>
            <div style="float:left">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th scope="col"><h5>Carrito</h5></th>
                        </tr>
                        <tr>    
                            <th scope="col">Codigo de ensamble</th>
                            <th scope="col">Nombre</th>
                            <th scope="col">Precio</th>
                            <th scope="col">Descargar del carrito</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            try {
                            //esxploramos el resultset y lo agregamos los resultados a la tabla
                                for (ProductoEnVenta item : GestorDeStock.carrito) {
                                    out.print("<tr>"
                                            + "<th scope=\"row\">" + item.getCodigoDeEnsamble()
                                            + "</th>"
                                            + "<td>" + item.getCodigoMueble() + "</td>"
                                            + "<td>" + item.getPrecio() + "</td>"
                                            + "<td>"
                                            + "<a href=\"/MiMuebleria/DescargarCarrito?codigoEnsamble=" + item.getCodigoDeEnsamble() + "\">"
                                            + "<i class='bx bxs-cart-add' style='color:#17d576'</i></a>"
                                            + "</td>"
                                            + "</tr>");
                                }

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        %>
                    </tbody>
                </table>
            </div> 
        </div>
    </body>
</html>
