package Servlets.AreaDeFabrica;

import AreaDeFabrica.GestorDePieza;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "TablaPieza", urlPatterns = {"/TablaPieza"})
public class TablaPieza extends HttpServlet {

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
        ResultSet resultado = null;
        try {
            if (request.getParameter("btnDescendente") != null) {
                resultado = gestor.ordenarPiezas("descendente");
            } else if (request.getParameter("btnAscendente") != null) {
                resultado = gestor.ordenarPiezas("ascendente");
            } else {
                resultado = gestor.mostrarPiezas();
            }
            while (resultado.next()) {
                out.print("<tr>"
                        + "<th scope=\"row\">" + resultado.getString("nombre") + ""
                        + "</th>"
                        + "<td>" + resultado.getDouble("precio") + "</td>"
                        + "<td>" + resultado.getInt("existencias") + "</td>"
                        + "<td> "
                        + "<a href=\"editar-pieza.jsp?nombre=" + resultado.getString("nombre") + "&precio=" + resultado.getDouble("precio") + "\"><i class='bx bxs-edit' style='color:#2b1da5'  ></i> "
                        + "<a href=\"eliminar-pieza.jsp?nombre=" + resultado.getString("nombre") + "&precio=" + resultado.getDouble("precio") + "\"><i class='bx bx-trash' style='color:#d52d4c'  ></i> "
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
}
