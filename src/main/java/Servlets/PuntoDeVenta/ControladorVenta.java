package Servlets.PuntoDeVenta;

import AreaDeVentas.GestorDeStock;
import AreaDeVentas.GestorDeVenta;
import AreaDeVentas.ProductoEnVenta;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ControladorVenta", urlPatterns = {"/ControladorVenta"})
public class ControladorVenta extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods
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
            //objetos a urilizar
            HttpSession sesion = request.getSession();
            GestorDeVenta gestor = new GestorDeVenta();
            GestorDeStock stock = new GestorDeStock();
            //obtener parametros nescesarios
            String codigoDeCliente = (String) request.getParameter("codigoDeCliente");
            String nombreCliente = (String) request.getParameter("nombre");
            String direccion = (String) request.getParameter("direccion");
            String codigoDeUsuario = (String) sesion.getAttribute("usuario");
            int codigoDeFactura = gestor.generarCodigoDeFactura();
            //
            String confirmacion = gestor.venderArticulos(codigoDeFactura, GestorDeStock.carrito, codigoDeCliente, codigoDeUsuario); //ejecutamos la compra
            switch (confirmacion) {
                case "Se realizo la venta con exito":
                    //si se comleta la compra 
                    response.sendRedirect("/MiMuebleria/Area-de-ventas/factura.jsp?vendedor=" + codigoDeUsuario + "&nombreCliente=" + nombreCliente
                            + "&direccion=" + direccion + "&codigoDeCliente=" + codigoDeCliente + "&codigoFactura=" + codigoDeFactura);
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
