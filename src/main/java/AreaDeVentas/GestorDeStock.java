/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AreaDeVentas;


import static ConexionMysql.Conexion.CONEXION;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luis Monterroso
 */
public class GestorDeStock {
    public static ArrayList<ProductoEnVenta> carrito = new ArrayList<>();
    
   /**
    * Este metodo hace los joins suficientes para obtener los muebles disponibles en stock
    * @return 
    */
    public ResultSet verArticulosDisponibles() {
        try {
            Statement query = CONEXION.createStatement();
            ResultSet resultado = query.executeQuery("SELECT a.codigo_de_ensamble, b.codigo_de_mueble, "
                    + "c.precio FROM stock a INNER JOIN ensamble b "
                    + "INNER JOIN mueble c ON a.codigo_de_ensamble = b.codigo_de_ensamble AND "
                    + "b.codigo_de_mueble = c.nombre AND a.estado = \"Disponible\";");
            return  resultado;
        } catch (SQLException e) {
            return null;
        }
    }
    /**
     * Este metodo actualiza el estado de mi articulo en stock
     * @param codigoDeEnsamble
     * @param nuevoEstado
     * @return 
     */
    public String cambiarEstadoDeArticulo(int codigoDeEnsamble,String nuevoEstado){
        try {
            CONEXION.setAutoCommit(true);
            PreparedStatement query = CONEXION.prepareStatement(
                    "UPDATE stock SET estado = ? WHERE codigo_de_ensamble = ?;");
            query.setString(1, nuevoEstado);
            query.setInt(2, codigoDeEnsamble);
            query.executeUpdate();
            CONEXION.commit();
            return "Actualizado con exito";
        } catch (SQLException e) {
            try {
                CONEXION.rollback();
            } catch (SQLException ex) {
                
            }
            return "Error, no se actualizo";
        } 
    }
}
