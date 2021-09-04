/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.PuntoDeVenta;

import AreaDeVentas.GestorDeVenta;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Luis Monterroso
 */
@WebServlet(name = "ControladorDeCredenciales", urlPatterns = {"/ControladorDeCredenciales"})
public class ControladorDeCredenciales extends HttpServlet {

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
            try {
                GestorDeVenta gestor = new GestorDeVenta();
                String nit = request.getParameter("nit");
                String nombre = "";
                String direccion = "";
                int contador = 0;
                if (!nit.equals("")) {
                    ResultSet resultado = gestor.buscarNit(nit);
                    while (resultado.next()) {
                        contador++;
                        nombre = resultado.getString("nombre");
                        direccion = resultado.getString("direccion");
                    }
                    if (contador > 0) {
                        response.sendRedirect("/MiMuebleria/ControladorVenta?codigoDeCliente="+nit
                        +"&nombre="+nombre+"&direccion="+direccion);
                    } else {
                        response.sendRedirect("/MiMuebleria/Area-de-ventas/credenciales-cliente-sin-registro.jsp?codigoDeCliente="+nit);
                    }
                } else {
                    throw new NumberFormatException("Se esperaba un NIT valido");
                }
            } catch (NumberFormatException ex) {

            } catch (SQLException ex) {

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
