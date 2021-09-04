package AreaDeFabrica;

import ConexionMysql.Conexion;
import static ConexionMysql.Conexion.CONEXION;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GestorDePieza {

    public String insertarPieza(Pieza pieza) {

        try {
            int contadorElementoExistente = 0;
            int existenciasActuales = 0;
            int suma;
            CONEXION.setAutoCommit(false);
            PreparedStatement query1 = CONEXION.prepareStatement(
                    "SELECT * FROM pieza WHERE nombre = ? and precio = ?;");
            query1.setString(1, pieza.getNombreDePieza());
            query1.setDouble(2, pieza.getPrecio());
            ResultSet resultado = query1.executeQuery();

            while (resultado.next()) {
                contadorElementoExistente++;
                existenciasActuales = resultado.getInt("existencias");
            }
            suma = pieza.getExistencias() + existenciasActuales;
            if (suma >= 0) {
                if (contadorElementoExistente > 0) {

                    PreparedStatement query2 = CONEXION.prepareStatement(
                            "UPDATE pieza SET existencias = ? WHERE nombre = ? and precio = ?;");
                    query2.setInt(1, suma);
                    query2.setString(2, pieza.getNombreDePieza());
                    query2.setDouble(3, pieza.getPrecio());
                    query2.executeUpdate();
                } else {
                    PreparedStatement query3 = CONEXION.prepareStatement(
                            "INSERT INTO pieza VALUES (?,?,?);");
                    query3.setString(1, pieza.getNombreDePieza());
                    query3.setDouble(2, pieza.getPrecio());
                    query3.setInt(3, pieza.getExistencias());
                    query3.executeUpdate();
                }
                CONEXION.commit();
                return "Pieza ingresada con exito al sistema";
            } else {
                return "No se inserto la pieza, no se permite tener valores negativos registrados en el apartado de existencias";
            }
        } catch (SQLException ex) {
            try {
                CONEXION.rollback();
            } catch (SQLException ex1) {
                return "Error severo";
            }
            return "No se inserto la pieza debido a un error inesperado";
        }
    }

    public void eliminarPieza(Pieza pieza){
        try {
            CONEXION.setAutoCommit(false);
            PreparedStatement query = CONEXION.prepareStatement(
                    "DELETE FROM pieza WHERE nombre = ? and precio = ?;");
            query.setString(1, pieza.getNombreDePieza());
            query.setDouble(2, pieza.getPrecio());
            query.executeUpdate();
            CONEXION.commit();
        } catch (SQLException e) {
            try {
                CONEXION.rollback();
            } catch (SQLException ex) {

            }
        }
    }

    public String modificarPieza(Pieza pieza, String nombre, String precio) {
        Connection conexion = Conexion.conectar();
        try {
            int contadorPiezaExistente = 0; //este contador sirve para contar si ya existe una pieza igual a la que vamos a actualizar
            conexion.setAutoCommit(false); //manejamos transaciones
            PreparedStatement query1 = conexion.prepareStatement("SELECT * FROM pieza WHERE nombre=? and precio=?;"); //esta quey devulve una tabla donde estan las piezas con caracterisicas iguales
            query1.setString(1, pieza.getNombreDePieza()); //damos valores a las ?
            query1.setDouble(2, pieza.getPrecio());//
            ResultSet resultado = query1.executeQuery();//ejecutamos la query
            while (resultado.next()) {//exploramos el resultado
                contadorPiezaExistente++; //si entra significa que ya hay una pieza con las caracteristicas
            }
            if (contadorPiezaExistente == 0) { //si esto se cumple entonces no hay piezas iguales
                PreparedStatement query2 = conexion.prepareStatement( //hacemos una qyery
                        "UPDATE pieza SET nombre = ?, precio = ? WHERE nombre = ? AND precio = ?;");
                query2.setString(1, pieza.getNombreDePieza());//damos valores a los ?
                query2.setDouble(2, pieza.getPrecio());//
                query2.setString(3, nombre);//
                query2.setDouble(4, Double.parseDouble(precio));//
                query2.executeUpdate();//
                conexion.commit();//si todo salio bien hacemos el commit
                return "Se modifico la pieza con exito";//retornamos el exito
            }
            return "No se actualizo la pieza debido a que ya hay una pieza con caracteristicas similares"; //si el igf no se cumple ya hay piezas con valores iguales
        } catch (SQLException ex) {
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
            }
            return ex.getMessage();
        } catch (NumberFormatException e) {
            return "No se modifico la pieza, precio con formato incorrecto";
        }
    }

    public ResultSet mostrarPiezas() {
        try {

            Statement query = CONEXION.createStatement();
            ResultSet resultado = query.executeQuery("SELECT * FROM pieza;");
            return resultado;
        } catch (SQLException ex) {
            return null;
        } 
    }

    public ResultSet ordenarPiezas(String orden) {
        try {
            Statement query = CONEXION.createStatement();
            ResultSet resultado = null;
            switch (orden) {
                case "ascendente":
                    resultado = query.executeQuery(
                            "SELECT * FROM pieza ORDER BY existencias ASC;");
                    break;
                case "descendente":
                    resultado = query.executeQuery(
                            "SELECT * FROM pieza ORDER BY existencias DESC;");
                    break;
            }
            return resultado;
        } catch (SQLException ex) {
            return null;
        } 
    }

    public ResultSet mostrarPiezasAgotadas() {
        try {
            Statement query = CONEXION.createStatement();
            ResultSet resultado = query.executeQuery("SELECT * FROM pieza WHERE"
                    + " existencias <= 10;");
            return resultado;

        } catch (Exception e) {
            return null;
        }
    }

    public boolean haySuficientesPiezas(String nombreDePieza, double precioDePieza, int piezasQueSeNecesitan) {
        try {
            PreparedStatement query = CONEXION.prepareStatement(
                    "SELECT * FROM pieza WHERE nombre = ? and precio = ?;");
            query.setString(1, nombreDePieza);
            query.setDouble(2, precioDePieza);
            ResultSet tablaDePiezas = query.executeQuery();
            while (tablaDePiezas.next()) {
                int piezasActuales = tablaDePiezas.getInt("existencias");
                if (piezasActuales < piezasQueSeNecesitan) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
