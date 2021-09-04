/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.PuntoDeVenta;

import AreaDeVentas.GestorDeVenta;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Luis Monterroso
 */
@WebServlet(name = "ControladorDevoluciones", urlPatterns = {"/ControladorDevoluciones"})
public class ControladorDevoluciones extends HttpServlet {

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
            String codigoDeCliente = request.getParameter("nit");
            String motivo = request.getParameter("motivo");
            int codigoFactura = Integer.valueOf(request.getParameter("codigoFactura"));
            int codigoDeEnsamble = Integer.valueOf(request.getParameter("codigoEnsamble"));
            if (!codigoDeCliente.equals("") && !motivo.equals("") && codigoFactura > 0 && codigoDeEnsamble > 0) {
                GestorDeVenta gestor = new GestorDeVenta();
                String alerta = gestor.hacerDevolucion(codigoDeCliente, motivo, codigoFactura, codigoDeEnsamble);
                System.out.println(alerta);
                request.setAttribute("alerta", alerta);
                request.getRequestDispatcher("Area-de-ventas/devolucion.jsp").forward(request, response);
            } else {
                request.setAttribute("alerta", "Parametros vacios");
               request.getRequestDispatcher("Area-de-ventas/devolucion.jsp").forward(request, response);
            }
        } catch (NumberFormatException ex) {
            request.setAttribute("alerta", "Parametros invalidos");
            request.getRequestDispatcher("Area-de-ventas/devolucion.jsp").forward(request, response);
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
