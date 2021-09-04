/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.PuntoDeVenta;

import AreaDeVentas.GestorDeConsulta;
import AreaDeVentas.GestorDeStock;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
@WebServlet(name = "ControladorConsultas", urlPatterns = {"/ControladorConsultas"})
public class ControladorConsultas extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            GestorDeConsulta gestor = new GestorDeConsulta();
            ArrayList<String> columnas = new ArrayList<>();
            if (request.getParameter("btnCompras") != null && !request.getParameter("codigoCliente").equals("")
                    && !request.getParameter("fecha1").equals("") && !request.getParameter("fecha2").equals("")) {//si enra entonces el usuario quiere ver las compras
                String nit = request.getParameter("codigoCliente"); //obtenemos los valores
                String fecha1 = request.getParameter("fecha1");//
                String fecha2 = request.getParameter("fecha2");//
                Date fecha1Convertida = formato.parse(fecha1);//creamos nuevas fechas
                Date fecha2Convertida = formato.parse(fecha2);
                //seteamos las columnas de las tablas
                columnas.add("Codigo de factura");
                columnas.add("Nit del cliente");
                columnas.add("Nombre del cliente");
                columnas.add("Direccion del cliente");
                columnas.add("Nombre del mueble");
                columnas.add("Precio del mueble");
                columnas.add("Fecha de compra");
                //
                request.setAttribute("columnas", columnas);//definimos el atrivuto en la request
                ResultSet resultado = gestor.comprasEnIntervaloDeTiempo(nit, fecha1Convertida, fecha2Convertida);//este es el resultado de la consulta
                request.setAttribute("resultado", resultado);
                request.getRequestDispatcher("Area-de-ventas/consultas.jsp").forward(request, response);

            } else if (request.getParameter("btnDevoluciones") != null && !request.getParameter("codigoCliente").equals("")
                    && !request.getParameter("fecha1").equals("") && !request.getParameter("fecha2").equals("")) {
                String nit = request.getParameter("codigoCliente"); //obtenemos los valores
                String fecha1 = request.getParameter("fecha1");//
                String fecha2 = request.getParameter("fecha2");//
                Date fecha1Convertida = formato.parse(fecha1);//creamos nuevas fechas
                Date fecha2Convertida = formato.parse(fecha2);
                //seteamos las columnas de las tablas
                columnas.add("Codigo de cliente");
                columnas.add("Nombre de cliente");
                columnas.add("Codigo de ensamble");
                columnas.add("Nombre de mueble");
                columnas.add("Motivo de devolución");
                columnas.add("Fecha de devolución");
                ////definimos el atrivuto en la request
                request.setAttribute("columnas", columnas);
                ResultSet resultado = gestor.devolucionesEnUNLapsoDeTiempo(nit, fecha1Convertida, fecha2Convertida);//este es el resultado de la consulta
                request.setAttribute("resultado", resultado);
                request.getRequestDispatcher("Area-de-ventas/consultas.jsp").forward(request, response);

            } else if (request.getParameter("btnProductos") != null) {
                GestorDeStock gestorStock = new GestorDeStock();
                columnas.add("Codigo de ensamble");
                columnas.add("Codigo de mueble");
                columnas.add("Precio");
                request.setAttribute("columnas", columnas);
                ResultSet resultado = gestorStock.verArticulosDisponibles();
                request.setAttribute("resultado", resultado);
                request.getRequestDispatcher("Area-de-ventas/consultas.jsp").forward(request, response);
            } else if (request.getParameter("btnVentaDeHoy") != null) {
                //seteamos las columnas de las tablas
                columnas.add("Codigo de factura");
                columnas.add("Nit del cliente");
                columnas.add("Nombre del cliente");
                columnas.add("Direccion del cliente");
                columnas.add("Nombre del mueble");
                columnas.add("Precio del mueble");
                columnas.add("Fecha de compra");
                //
                request.setAttribute("columnas", columnas);//definimos el atrivuto en la request
                ResultSet resultado = gestor.ventasDeHoy();//este es el resultado de la consulta
                request.setAttribute("resultado", resultado);
                request.getRequestDispatcher("Area-de-ventas/consultas.jsp").forward(request, response);
            } else if (request.getParameter("btnFactura") != null && !request.getParameter("codigoFactura").equals("")) {
                int codigoFactura = Integer.valueOf(request.getParameter("codigoFactura"));
                columnas.add("Codigo de factura");
                columnas.add("Nit del cliente");
                columnas.add("Nombre del cliente");
                columnas.add("Direccion del cliente");
                columnas.add("Nombre del mueble");
                columnas.add("Precio del mueble");
                columnas.add("Fecha de compra");
                request.setAttribute("columnas", columnas);//definimos el atrivuto en la request
                ResultSet resultado = gestor.detallesDeFactura(codigoFactura);//este es el resultado de la consulta
                request.setAttribute("resultado", resultado);
                request.getRequestDispatcher("Area-de-ventas/consultas.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("Area-de-ventas/consultas.jsp").forward(request, response);
            }
        } catch (ParseException ex) {
          //  request.getRequestDispatcher("Area-de-ventas/consultas.jsp").forward(request, response);
          response.sendRedirect("Area-de-ventas/consultas.jsp");
        } catch (Exception ex) {
            request.getRequestDispatcher("Area-de-ventas/consultas.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
