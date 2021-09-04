/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.AreaFinanciera;

import AreaDeFabrica.GestorDeEnsamble;
import java.sql.*;
import AreaFinanciera.Reporte;
import AreaFinanciera.VentaDeVendedor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Luis Monterroso
 */
@WebServlet(name = "Consultas", urlPatterns = {"/Consultas"})
public class Consultas extends HttpServlet {

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try ( PrintWriter out = response.getWriter()) {
            Reporte consulta = new Reporte();
            //nos interesa siempre devolver las fechas
            String primeraFechaParametro = request.getParameter("fecha1");
            String segundaFechaParametro = request.getParameter("fecha2");
            request.setAttribute("fecha1", primeraFechaParametro);
            request.setAttribute("fecha2", segundaFechaParametro);
            //
            if (request.getParameter("btnVentas") != null) {//si este boton fue activado entonces hacemos el reporte de ventas
                //tomamos las fechas de la request
                String primeraFecha = request.getParameter("fecha1");
                String segundaFecha = request.getParameter("fecha2");
                //hacemos la consulta
                ResultSet respuesta = consulta.ventasEnFechas(primeraFecha, segundaFecha);
                //guardamos la consulta
                request.setAttribute("tabla", respuesta);
                //mandamos la reques hacia el jsp correspondiente
                request.getRequestDispatcher("Area-financiera/ventas-en-intervalo-de-tiempo.jsp").forward(request, response);
            } else if (request.getParameter("btnDevoluciones") != null) {//si este boton fue activado entonces hacemos el reporte de devoluciones
                //tomamos las fechas de la request
                String primeraFecha = request.getParameter("fecha1");
                String segundaFecha = request.getParameter("fecha2");
                ResultSet respuesta = consulta.devolucionesEnFechas(primeraFecha, segundaFecha);//hacemos la consulta
                //guardamos la consulta
                request.setAttribute("tabla", respuesta);
                //mandamos la reques hacia el jsp correspondiente
                request.getRequestDispatcher("Area-financiera/devoluciones-en-intervalo-de-tiempo.jsp").forward(request, response);
            } else if (request.getParameter("btnGanancias") != null) {//si este boton fue activado entonces hacemos el reporte de ganancias
                //tomamos las fechas de la request
                String primeraFecha = request.getParameter("fecha1");
                String segundaFecha = request.getParameter("fecha2");
                //estas variables nos indicaran las ganancias
                double dineroEnVentas = 0;
                double dineroEnDevoluciones = 0;
                double costoDeProduccion = 0;
                //hacemos el reporte de las ventas en ese intervalo de tiempo
                ResultSet ventas = consulta.ventasEnFechas(primeraFecha, segundaFecha);
                ResultSet ventas2 = consulta.ventasEnFechas(primeraFecha, segundaFecha);
                //hacemos el reporte de las devoluciones en ese intervalo de tiempo
                ResultSet devoluciones = consulta.devolucionesEnFechas(primeraFecha, segundaFecha);
                ResultSet devoluciones2 = consulta.devolucionesEnFechas(primeraFecha, segundaFecha);
                //exploramos las ventas en esas fechas en el monto vendido sumamos el contador dineroEnVentas, ademas verificamos el costo de produccion del ensamble en cuestion
                while (ventas.next()) {
                    int codigoDeEnsamble = ventas.getInt("codigo_de_ensamble");
                    dineroEnVentas += ventas.getDouble("precio");
                    costoDeProduccion += GestorDeEnsamble.saberConstoDeEnsamble(codigoDeEnsamble);
                }
                //ahora exploramos las devoluciones y sumamos las perdidas
                while (devoluciones.next()) {
                    dineroEnDevoluciones += devoluciones.getDouble("perdida");
                }
                //ahora creamos las gancias que es restarle a las ventas el costo d eproduccion y la perdida de devoluciones
                double gananciasTotales = dineroEnVentas - (dineroEnDevoluciones + costoDeProduccion);
                //seteamos en el request los valores encontrados
                request.setAttribute("dineroEnVentas", dineroEnVentas);
                request.setAttribute("dineroEnDevoluciones", dineroEnDevoluciones);
                request.setAttribute("costoDeProduccion", costoDeProduccion);
                request.setAttribute("gananciasTotales", gananciasTotales);
                request.setAttribute("tablaVentas", ventas2);
                request.setAttribute("tablaDevoluciones", devoluciones2);
                request.getRequestDispatcher("Area-financiera/ganancias-en-intervalo-de-tiempo.jsp").forward(request, response);
            } else if (request.getParameter("btnMejorVendedor") != null) {
                //tomamos las fechas de la request
                String primeraFecha = request.getParameter("fecha1");
                String segundaFecha = request.getParameter("fecha2");
                //variable que guardara el nombre con mayor ventas
                String nombreVendedor = "";
                int numeroDeVentas = 0;//guardara la cantidad de ventas que hace
                ResultSet mejorVendedor = consulta.usuarioConMasVentasEnFechas(primeraFecha, segundaFecha);
                while (mejorVendedor.next()) {
                    nombreVendedor = mejorVendedor.getString("usuario_vendedor");//obtenemos los valores
                    numeroDeVentas = mejorVendedor.getInt("numero_de_ventas");
                    break;//solo nos interesa la primera tupla de esta resultset
                }
                //ahora con el nombre del vendedor solicitamos sus ventas en ese intervalo de tiempo
                ResultSet ventasDelMejorVendedor = consulta.ventasDeUsuarioEnFechas(primeraFecha, segundaFecha, nombreVendedor);
                //seteamos los valores encontrados en ra request
                request.setAttribute("nombreVendedor", nombreVendedor);
                request.setAttribute("numeroDeVentas", numeroDeVentas);
                request.setAttribute("ventasDelMejorVendedor", ventasDelMejorVendedor);
                //hacemos el fordward
                request.getRequestDispatcher("Area-financiera/usuario-con-mas-ventas-en-intervalo-de-tiempo.jsp").forward(request, response);
            } else if (request.getParameter("btnGananciasVendedor") != null) {
                //tomamos las fechas de la request
                String primeraFecha = request.getParameter("fecha1");
                String segundaFecha = request.getParameter("fecha2");
                //hacemos la consulta de las ventas en esas fechas
                ResultSet respuesta = consulta.ventasEnFechasConVendedor(primeraFecha, segundaFecha);
                //hacemos un array de ventas
                ArrayList<VentaDeVendedor> ventasDelVendedor = new ArrayList<>();
                //estas son las variables que usaremos para explorar las ventas
                String nombreDelVendedor;
                double dineroDeLaVenta;
                int codigoDeMuebleVendido;
                double costoDeEnsambleDeVenta;
                boolean usuarioExiste = false;
                //exploramos el resultset en busca de separar las ventas de un vendedor
                while (respuesta.next()) {
                    nombreDelVendedor = respuesta.getString("usuario_vendedor");
                    dineroDeLaVenta = respuesta.getDouble("precio");
                    codigoDeMuebleVendido = respuesta.getInt("codigo_de_ensamble");
                    costoDeEnsambleDeVenta = GestorDeEnsamble.saberConstoDeEnsamble(codigoDeMuebleVendido);
                    usuarioExiste = false;
                    for (VentaDeVendedor item : ventasDelVendedor) {//por cada tupla miramos si el vendedor exist en el array y sumamos sus ventas en caso de que si
                        if (item.getUsuarioDeVendedor().equals(nombreDelVendedor)) {//si los usuarios son iguales entonces existe ese vendedor en el arreglo
                            item.setDineroEnVenta(item.getDineroEnVenta() + dineroDeLaVenta);
                            item.setCostoDeProduccion(item.getCostoDeProduccion() + costoDeEnsambleDeVenta);
                            item.setGananciaGenerada();
                            usuarioExiste = true;
                        }
                    }
                    if (usuarioExiste == false) {//si esto se cumple nunca se entro al if del foreach entonces el usuario aun no esta en mi arreglo
                        VentaDeVendedor ventaNueva = new VentaDeVendedor(nombreDelVendedor, dineroDeLaVenta, costoDeEnsambleDeVenta);
                        ventaNueva.setGananciaGenerada();//seteamos dentro del onjeto la ganancia
                        ventasDelVendedor.add(ventaNueva);//sumamos esta nueva venta al arreglo
                    }
                }
                //variables que usuaremos para identificar al mayor vendedor
                String nombreMayorVendedor = "";
                double gananciasMayorVendedor = 0;
                //ahora volvemos a recorrer el arreglo pero averiguando quien representa mas ganancia
                for (VentaDeVendedor item : ventasDelVendedor) {
                    double gananciaComparacion = item.getGananciaGenerada();
                    String nombreComparacion = item.getUsuarioDeVendedor();
                    if (gananciaComparacion > gananciasMayorVendedor) {//si la ganancia del objeto es mayor que la referencia
                        gananciasMayorVendedor = gananciaComparacion;//seteamos los nuevos valores, nombre y ganancia
                        nombreMayorVendedor = nombreComparacion;
                    }
                }
                //consultamos las ventas del usuario con mao ganancia en ese intervalo de tiempo
                ResultSet ventasDeUsuario = consulta.ventasDeUsuarioEnFechas(primeraFecha, segundaFecha, nombreMayorVendedor);
                //por ultimo seteamos los atributos de la request
                request.setAttribute("nombreMayorVendedor", nombreMayorVendedor);
                request.setAttribute("gananciasMayorVendedor", gananciasMayorVendedor);
                request.setAttribute("ventasDeUsuario", ventasDeUsuario);
                //hacmeos el fordward
                request.getRequestDispatcher("Area-financiera/usuario-que-mas-ganancias-en-intervalo-de-tiempo.jsp").forward(request, response);
            } else if (request.getParameter("btnMuebleMasVendido") != null) {
                //tomamos las fechas de la request
                String primeraFecha = request.getParameter("fecha1");
                String segundaFecha = request.getParameter("fecha2");
                //obtenemos el mueble mas vendido
                ResultSet muebleMasVendido = consulta.muebleMasVendidoEnFechas(primeraFecha, segundaFecha);
                //variables que guardan los valores del mueble mas vendido
                String nombreDelMueble = "";
                int ventasDelMueble = 0;
                //exploramos el resultsrt solo nos interesa la primera tupla
                while (muebleMasVendido.next()) {
                    nombreDelMueble = muebleMasVendido.getString("codigo_de_mueble");
                    ventasDelMueble = muebleMasVendido.getInt("numero_de_ventas");
                    break;
                }
                //obtenemos las ventas que tiene ese mueble
                ResultSet ventasDelMuebleEnFechas = consulta.ventasDeUnMueble(primeraFecha, segundaFecha, nombreDelMueble);
                //seteamos los objetos
                request.setAttribute("nombreDelMueble", nombreDelMueble);
                request.setAttribute("ventasDelMueble", ventasDelMueble);
                request.setAttribute("ventasDelMuebleEnFechas", ventasDelMuebleEnFechas);
                //hacemos el fordaward
                request.getRequestDispatcher("/Area-financiera/mueble-mas-vendido.jsp").forward(request, response);
            } else if (request.getParameter("btnMuebleMenosVendido") != null) {
                //tomamos las fechas de la request
                String primeraFecha = request.getParameter("fecha1");
                String segundaFecha = request.getParameter("fecha2");
                //obtenemos el mueble menos vendido
                ResultSet muebleMasVendido = consulta.muebleMenosVendidoEnFechas(primeraFecha, segundaFecha);
                //variables que guardan los valores del mueble menos vendido
                String nombreDelMueble = "";
                int ventasDelMueble = 0;
                //exploramos el resultsrt solo nos interesa la primera tupla
                while (muebleMasVendido.next()) {
                    nombreDelMueble = muebleMasVendido.getString("codigo_de_mueble");
                    ventasDelMueble = muebleMasVendido.getInt("numero_de_ventas");
                    break;
                }
                //obtenemos las ventas que tiene ese mueble
                ResultSet ventasDelMuebleEnFechas = consulta.ventasDeUnMueble(primeraFecha, segundaFecha, nombreDelMueble);
                //seteamos los objetos
                request.setAttribute("nombreDelMueble", nombreDelMueble);
                request.setAttribute("ventasDelMueble", ventasDelMueble);
                request.setAttribute("ventasDelMuebleEnFechas", ventasDelMuebleEnFechas);
                //hacemos el fordaward
                request.getRequestDispatcher("/Area-financiera/mueble-menos-vendido.jsp").forward(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
