/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.AreaDeFabrica;

import AreaDeFabrica.*;
import ConexionMysql.Conexion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Luis Monterroso
 */
@WebServlet(name = "EdicionDePieza", urlPatterns = {"/EdicionDePieza"})
public class EdicionDePieza extends HttpServlet {

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
        String alerta = "";

        if (request.getParameter("btnGuardar") != null) {
            try {
                String nombre = request.getParameter("nombre");
                String precio = request.getParameter("precio");
                String nombre2 = request.getParameter("nombre2");
                String precio2 = request.getParameter("precio2");
                if (!nombre.equals("") && !precio.equals("")) {
                    Pieza pieza = new Pieza(nombre, Double.parseDouble(precio), 0);
                    alerta = gestor.modificarPieza(pieza, nombre2, precio2);
                } else {
                    alerta = "Hay parametros vacios";
                }
            } catch (NumberFormatException e) {
                alerta = "precio con formato incorrecto";
            }
            out.print("<div class=\"alert alert-warning\" role=\"alert\"> "
                    + alerta
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
