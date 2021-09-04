/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.AreaDeFabrica;

import AreaDeFabrica.GestorDePieza;
import AreaDeFabrica.Pieza;
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
@WebServlet(name = "IngresoDePieza", urlPatterns = {"/IngresoDePieza"})
public class IngresoDePieza extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        GestorDePieza gestor = new GestorDePieza();
        String nombre;
        String confimacion = "";
        double precio = 0;
        int existencias = 0;
        if (request.getParameter("btnGuardar") != null) {
            try {
                nombre = request.getParameter("nombre");
                precio = Double.parseDouble(request.getParameter("precio"));
                existencias = Integer.parseInt(request.getParameter("existencias"));
                if (!nombre.equalsIgnoreCase("")) {
                    Pieza pieza = new Pieza(nombre, precio, existencias);
                    confimacion = gestor.insertarPieza(pieza);
                } else {
                    throw new Exception("Parametros vacios");
                }
            } catch (NumberFormatException ex) {
                confimacion = "Error, se esperaba parametros validos";
            } catch (Exception ex2) {
                confimacion = ex2.getMessage();
            }
            out.print("<div class=\"alert alert-warning\" role=\"alert\"> "
                    + confimacion
                    + " </div>");
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
