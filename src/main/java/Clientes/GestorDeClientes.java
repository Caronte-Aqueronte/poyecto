
package Clientes;

import static ConexionMysql.Conexion.CONEXION;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GestorDeClientes {
    public String guardarNuevoCliente(Cliente cliente){
        try {
            CONEXION.setAutoCommit(false);
            //verificamos si el nit ya existe
            PreparedStatement query = CONEXION.prepareStatement(
                    "SELECT * FROM cliente WHERE nit = ?");
            query.setString(1, cliente.getNit());
            ResultSet resultado = query.executeQuery();
            //si ya existe entrara a este while
            while(resultado.next()){
                return "No se ingreso nuevo usuario, Nit existente";
            }
            //si llega aqui entonces no existe entonces insertamos
            query = CONEXION.prepareStatement(
                    "INSERT INTO cliente VALUES(?,?,?)");
            query.setString(1, cliente.getNit());
            query.setString(2, cliente.getNombre());
            query.setString(3, cliente.getDireccion());
            query.executeUpdate();
            CONEXION.commit();
            return "Se agrego nuevo cliente";
        } catch (SQLException ex) {
            try {
                CONEXION.rollback();
            } catch (SQLException ex1) {
                return "Error al hacer rolback";
            }
            return ex.getMessage();
        }
    }
}
