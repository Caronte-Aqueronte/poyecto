package AreaFinanciera;

import static ConexionMysql.Conexion.CONEXION;
import java.sql.*;

public class ConsultaUsuario {

    public String cambiarEstadoDeUsuario(String nombreUsuario, String nuevoEstado) {
        try {
            CONEXION.setAutoCommit(false);
            PreparedStatement query = CONEXION.prepareCall(
                    "UPDATE usuario SET estado = ? WHERE UPPER(usuario) = UPPER(?)");//esta query permite cambiar la columna estado como se mande el parametro
            query.setString(1, nuevoEstado);//le damos valores a los ?
            query.setString(2, nombreUsuario);
            int filasAfectadas = query.executeUpdate();//ejecutamos la query
            if (filasAfectadas > 0) {//si se afecto alguna fila esto deberia cumplirse
                CONEXION.commit();//si todo sale bien hacemos e commit
                return "Se actualizo el estado del usuario " + nombreUsuario;
            }
            //si llega aqui entonces no entro al if y no se actualizo nada etnocnes lanzamos la exception
            throw new SQLException("posiblemente hay infomacion erronea");

        } catch (SQLException ex) {//pueden hacer excepciones
            try {
                CONEXION.rollback();
            } catch (SQLException ex2) {
                return "Error relacionado con la base de datos";
            }
            return "No se actualizo el usuario: " + ex.getMessage();
        } catch (NullPointerException ex) {//si algo esta nulo
            return "Parametros vacios";
        }
    }

    public String insertarNuevoUsuario(String nombreDelTrabajador, String password, String nombreDeUsuario, String puesto) {
        try {
            //vemos que no exista ese usuario
            ResultSet usuarioExiste = mostrarUsuario(nombreDeUsuario);
            while (usuarioExiste.next()) {//si entral al while entonces ese usuario ya existe
                return "Nombre de usuario ya existe";
            }
            //si llega aqui no existe ese usuario etonces lo insertamos
            CONEXION.setAutoCommit(false);
            PreparedStatement query = CONEXION.prepareStatement(//insertamos los datos
                    "INSERT INTO usuario VALUES (?,?,?,?,?)");
            query.setString(1, nombreDelTrabajador);
            query.setString(2, nombreDeUsuario);
            query.setString(3, password);
            query.setString(4, puesto);
            query.setString(5, "Activo");
            if (query.executeUpdate() > 0) {
                CONEXION.commit();
                return "Se creo con existo el usuario " + nombreDeUsuario;
            }
            //si llego aqui entonces no entro al if y no se guardo nada
            throw new SQLDataException();

        } catch (SQLException ex) {
            return "No se inserto nuevo usuario";
        } catch (NullPointerException ex) {
            return "";
        }
    }

    /**
     * Este metodo busca en la base de datos el nombre del usuario y devuelve la
     * tabla que se encontro
     *
     * @param nombreUsuario
     * @return
     */
    public ResultSet mostrarUsuario(String nombreUsuario) {
        try {
            PreparedStatement query = CONEXION.prepareCall(
                    "SELECT * FROM usuario WHERE UPPER(usuario) = UPPER(?);");
            query.setString(1, nombreUsuario);
            ResultSet respuesta = query.executeQuery();//ejecutamos la query
            return respuesta;
        } catch (SQLException ex) {
            return null;
        } catch (NullPointerException ex) {
            return null;
        }
    }
       /**
     * Este metodo busca en la base de datos el nombre del usuario y devuelve la
     * tabla que se encontro
     *
     * @param nombreUsuario
     * @return
     */
    public ResultSet buscarUsuarioPorNombre(String nombreUsuario) {
        try {
            PreparedStatement query = CONEXION.prepareCall(
                    "SELECT * FROM usuario WHERE usuario like ?");
            query.setString(1, "%"+nombreUsuario+"%");
            ResultSet respuesta = query.executeQuery();//ejecutamos la query
            return respuesta;
        } catch (SQLException ex) {
            return null;
        } catch (NullPointerException ex) {
            return null;
        }
    }
}   

