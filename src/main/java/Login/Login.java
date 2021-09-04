package Login;

import java.sql.*;
import ConexionMysql.*;

public class Login {

    public String buscarUsuarios(String usuario, String password) {
        try {
            Connection conexion = Conexion.conectar();
            //vemos si existe alguna tupla con estas credenciasles
            PreparedStatement query = conexion.prepareStatement("SELECT * FROM "
                    + "usuario WHERE UPPER(usuario) = UPPER(?) AND UPPER(password) = UPPER(?);");
            query.setString(1, usuario);//damos significado a los ?
            query.setString(2, password);         
            ResultSet resultado = query.executeQuery();//ejectuamos la consulta
            while(resultado.next()){//exploramos la respuesta
                if(resultado.getString("estado").equals("Activo")){//vemos si esta activo el usuario
                    return resultado.getString("puesto");//si esta activo retornamos el usuario
                }
                if(resultado.getString("estado").equals("Inactivo")){//si esta inactivo
                    return "Usuario inactivo";//retornamos que esta inactivo
                }
            }
            return "Usuario o contraseña invalidos";//si llega aqui etonces algo no esa valido
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
}
