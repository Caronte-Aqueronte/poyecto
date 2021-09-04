/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.PuntoDeVenta;

import AreaDeVentas.GestorDeStock;
import AreaDeVentas.ProductoEnVenta;
import Clientes.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Luis Monterroso
 */
@WebServlet(name = "GuardarCliente", urlPatterns = {"/GuardarCliente"})
public class GuardarCliente extends HttpServlet {

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
            GestorDeClientes gestor = new GestorDeClientes();
            GestorDeStock stock = new GestorDeStock();
            String nit = request.getParameter("codigoDeCliente");
            String direccion = (String) request.getParameter("direccion");
            String nombre = (String) request.getParameter("nombre");
            Cliente cliente = new Cliente(nit, nombre, direccion);
            String confirmacion = gestor.guardarNuevoCliente(cliente);
            switch (confirmacion) {
                case "Se agrego nuevo cliente":
                    response.sendRedirect("/MiMuebleria/ControladorVenta?codigoDeCliente="+nit
                        +"&nombre="+nombre+"&direccion="+direccion);
                    break;
                default:
                    //si no se completa a compra
                    for (ProductoEnVenta item : GestorDeStock.carrito) { //cambiamos los estados de los productos a disponibles
                        stock.cambiarEstadoDeArticulo(item.getCodigoDeEnsamble(), "Disponible");
                    }
                    ArrayList<ProductoEnVenta> carritoVacio2 = new ArrayList<>(); //creamos un carrito vacio
                    GestorDeStock.carrito = carritoVacio2; //borramos el carrito actual
                    response.sendRedirect("/MiMuebleria/Area-de-ventas/nueva-venta.jsp");
                    break;
            }
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
