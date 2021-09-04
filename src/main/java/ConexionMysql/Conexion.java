
package ConexionMysql;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {
    
    private static final String USUARIO = "usuarioParaProyecto";
    private static final String PASSWORD = "41288320abc";
    private static final String URL = "jdbc:mysql://localhost:3306/mi_muebleria";
    public static final Connection CONEXION = conectar();
    public static Connection conectar(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            return conexion;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }     
    }    
}
