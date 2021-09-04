<%@page import="Login.Login"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <link href='https://unpkg.com/boxicons@2.0.9/css/boxicons.min.css' rel='stylesheet'>
        <title>Login</title>
    </head>
    <body>
        <%
            HttpSession sesion = request.getSession(); //este objeto crea una sesion en el sistema    
        %>
        <div class="container mt-5">
            <div class="row">
                <div class="col-sm">
                    <form action="index.jsp" method="post">
                        <div class="mb-3">
                            <label for="exampleInputEmail1" class="form-label">Usuario</label>
                            <input type="text" class="form-control" name="txtUsuario" placeholder="Ingrese su Usuario" required="">
                        </div>
                        <div class="mb-3">
                            <label for="exampleInputPassword1" class="form-label">Contraseña</label>
                            <input type="password" class="form-control" name="txtContra" placeholder="Ingrese su Contraseña" required="">
                        </div>
                        <button type="submit" name="btnEntrar" class="btn btn-outline-primary"><i class='bx bxs-log-in'></i> Entrar</button>
                    </form>
                    <%
                        if (request.getParameter("btnEntrar") != null) { //revisamos si se le dio clik al boton entrar
                            String usuario = request.getParameter("txtUsuario");//miramos el texto que se ingreso en txt usuario
                            String contra = request.getParameter("txtContra");//miramos el texto que se ingreso en txtx contra
                            Login login = new Login(); //creamos un objeto login que permitira usar el metodo que nesecitamos
                            String resultado = login.buscarUsuarios(usuario, contra); // este String sera el que usamos para redirigir al usuario 
                            switch (resultado) { //segun la columna puesto redirigiremos al usuario
                                case "Fábrica":
                                    sesion.setAttribute("log", "1"); //asignamos el identificador log y 1 para saber que la maquina se logeo en el sistema
                                    sesion.setAttribute("usuario", usuario);
                                    sesion.setAttribute("puesto", "Fábrica");
                                    response.sendRedirect("Area-de-fabrica/menu-principal-fabrica.jsp");
                                    break;
                                case "Punto de venta":
                                    sesion.setAttribute("log", "1"); //asignamos el identificador log y 1 para saber que la maquina se logeo en el sistema
                                    sesion.setAttribute("usuario", usuario);
                                    sesion.setAttribute("puesto", "Punto de venta");
                                    response.sendRedirect("Area-de-ventas/menu-principal-ventas.jsp");
                                    break;
                                case "Área financiera":
                                    sesion.setAttribute("log", "1"); //asignamos el identificador log y 1 para saber que la maquina se logeo en el sistema
                                    sesion.setAttribute("usuario", usuario);
                                    sesion.setAttribute("puesto", "Área financiera");
                                    response.sendRedirect("Area-financiera/menu-principal-financiera.jsp");
                                    break;
                                default:
                                    out.print("<div class=\"alert alert-danger\" role=\"alert\">"
                                            + resultado
                                            + "</div>");
                                    break;
                            }
                        }
                    %>
                </div> 
            </div>
        </div>
    </body>
</html>
