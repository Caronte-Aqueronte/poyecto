/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.AreaDeFabrica;

import AreaDeFabrica.GestorDeEnsamble;
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
@WebServlet(name = "TablaEnsambles", urlPatterns = {"/TablaEnsambles"})
public class TablaEnsambles extends HttpServlet {

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
        GestorDeEnsamble gestor = new GestorDeEnsamble();
        ResultSet resultado = null;
        try {
            if (request.getParameter("btnDescendente") != null) {
                resultado = gestor.mostrarMueblesOrdenados("descendente");
            } else if (request.getParameter("btnAscendente") != null) {
                resultado = gestor.mostrarMueblesOrdenados("ascendente");
            } else {
                resultado = gestor.mostrarMuebles();
            }
            while (resultado.next()) {
                out.print("<tr>"
                        + "<th scope=\"row\">" + resultado.getString("codigo_de_usuario")
                        + "</th>"
                        + "<td>" + resultado.getInt("codigo_de_ensamble") + "</td>"
                        + "<td>" + resultado.getString("codigo_de_mueble") + "</td>"
                        + "<td>" + resultado.getString("fecha_de_ensamble") + "</td>"
                        + "<td>" + resultado.getString("aprobacion") + "</td>"
                        + "<td> "
                        + "<a href=\"aprobar-pieza.jsp?codigo=" + resultado.getInt("codigo_de_ensamble") + "\"><i class='bx bxs-check-square' style='color:#2ca43f'  ></i>"
                        + "</td>"
                        + "</tr>");
            }
        } catch (SQLException ex) {

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
