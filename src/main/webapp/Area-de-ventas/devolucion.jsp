<%-- 
    Document   : devolucion
    Created on : 29/08/2021, 17:55:16
    Author     : Luis Monterroso
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <title>Nueva devolución</title>
    </head>
    <body>
        <%
            String alerta = (String) request.getAttribute("alerta");
        %>
        <jsp:include page="nav-regresar.jsp"/>
        <div class="container mt-5" style="width: 700px; height: 200px;">
            <div class="row">
                <div class="col-sm">
                    <form action="/MiMuebleria/ControladorDevoluciones" method="post">
                        <label>Ingrese NIT:</label>
                        <div class="mb-3 mt-2">
                            <input type="text" class="form-control" name="nit" placeholder="NIT" required="">
                        </div>
                        <label>Codigo de factura:</label>
                        <div class="mb-3 mt-2">
                            <input type="number" class="form-control" name="codigoFactura" placeholder="Codigo de factura" required="">
                        </div>
                        <label>Codigo de ensamble:</label>
                        <div class="mb-3 mt-2">
                            <input type="number" class="form-control" name="codigoEnsamble" placeholder="Codigo de ensamble" required="">
                        </div>
                        <label>Motivo de devolución:</label>
                        <div class="mb-3 mt-2">
                            <textarea class="form-control" name="motivo" rows="3" placeholder="Ingrese motivo" required=""></textarea>
                        </div>
                        <input type="submit" value="Procesar petición" class="btn btn-primary" />
                    </form>
                    <%
                        if (alerta != null) {
                    %>
                    <div class="alert alert-info" role="alert">
                        <%=alerta%>
                    </div>
                    <%}%>
                </div> 
            </div> 
        </div>
    </body>
</html>
