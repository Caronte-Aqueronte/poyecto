package AreaDeFabrica;

import ConexionMysql.Conexion;
import static ConexionMysql.Conexion.CONEXION;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GestorDeEnsamble {

    public String ensamblarMueble(String mueble, String usuario) {

        try {
            CONEXION.setAutoCommit(false);
            //traemos la tabla de piezas que tiene que tener mi mueble
            PreparedStatement query = CONEXION.prepareStatement(
                    "SELECT * FROM formula WHERE codigo_de_mueble = ?");
            query.setString(1, mueble);
            ResultSet tablaDePiezas = query.executeQuery();
            //
            //restamos las existencias de las piezas
            int contadorExisteMueble = 0;
            int piezasQueSeNecesitan = 0;
            String nombreDePieza = "";
            double precioDePieza = 0;
            double costoDeProduccion = 0;
            while (tablaDePiezas.next()) {
                contadorExisteMueble++;
                piezasQueSeNecesitan = tablaDePiezas.getInt("piezas_necesarias");
                nombreDePieza = tablaDePiezas.getString("nombre_de_pieza");
                precioDePieza = tablaDePiezas.getDouble("precio_de_pieza");
                costoDeProduccion = costoDeProduccion + (precioDePieza * piezasQueSeNecesitan);
                GestorDePieza gestorDePieza = new GestorDePieza();
                if (gestorDePieza.haySuficientesPiezas(nombreDePieza, precioDePieza, piezasQueSeNecesitan)) {
                    PreparedStatement query2 = CONEXION.prepareStatement(
                            "UPDATE pieza SET existencias = existencias - ? WHERE"
                            + " nombre = ? AND precio = ?");
                    query2.setInt(1, piezasQueSeNecesitan);
                    query2.setString(2, nombreDePieza);
                    query2.setDouble(3, precioDePieza);
                    query2.executeUpdate();
                } else {
                    throw new SQLException("No hay suficientes piezas para armar este ensamble");
                }
            }
            //si llega hasta aqui no hay ningun problema guardamos el nuevo mueble creado
            if (contadorExisteMueble > 0) {
                PreparedStatement query3 = CONEXION.prepareStatement(
                        "INSERT INTO ensamble (codigo_de_usuario,codigo_de_mueble,fecha_de_ensamble,costo_de_ensamble,aprobacion) values (?,?,CURDATE(),?,?);");
                query3.setString(1, usuario);
                query3.setString(2, mueble);
                query3.setDouble(3, costoDeProduccion);
                query3.setString(4, "No aprobado");
                query3.executeUpdate();
                CONEXION.commit();
                return "Ensamble realizado con exito";
            }
            throw new SQLException("No hay indicaciones para este ensamble o el mueble no existe");
        } catch (SQLException e) {
            try {
                CONEXION.rollback();
                return e.getMessage();
            } catch (SQLException ex) {
                Logger.getLogger(GestorDeEnsamble.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "Error con la base de datos";
        }
    }

    public ResultSet mostrarMuebles() {
        try {

            Statement query = CONEXION.createStatement();
            ResultSet resultado = query.executeQuery("SELECT * FROM ensamble");
            return resultado;
        } catch (SQLException e) {
            return null;
        }
    }

    public String registrarMuebleEnSalaDeVentas(String codigoDeEnsamble) {
        //ver si el codigo de ensamble existe en la base de datos
        try {
            int contadorResultado = 0;
            CONEXION.setAutoCommit(false);
            PreparedStatement query = CONEXION.prepareStatement(
                    "SELECT * FROM ensamble WHERE codigo_de_ensamble = ?"); //preparamos la consulta queremos obtener el ensamble que tiene ese codigo
            query.setInt(1, Integer.valueOf(codigoDeEnsamble)); // damos valor a ?
            ResultSet resultado = query.executeQuery(); //ejecutamos la query
            while (resultado.next()) {
                if (resultado.getString("aprobacion").equals("Aprobado")) {

                } else {
                    contadorResultado++;
                }
            }
            if (contadorResultado > 0) {
                PreparedStatement query2 = CONEXION.prepareStatement("UPDATE ensamble set aprobacion = ? WHERE codigo_de_ensamble = ?;");
                query2.setString(1, "Aprobado");
                query2.setInt(2, Integer.valueOf(codigoDeEnsamble));
                query2.executeUpdate();
                query2 = CONEXION.prepareStatement("INSERT INTO stock VALUES (?,?);");
                query2.setInt(1, Integer.valueOf(codigoDeEnsamble));
                query2.setString(2, "Disponible");
                query2.executeUpdate();
                CONEXION.commit();
                return "Se registro con exito el ensamble a la sala de ventas";
            }
            return "Error al registrar el ensamble a la sala de ventas, el ensamble no existe o ya esta en la sala de ventas";
        } catch (SQLException ex) {
            try {
                CONEXION.rollback();
                return "Error relacionado con la base de datos";
            } catch (SQLException ex1) {
                return "ERROR CRITICO al intentar hacer rolback";
            }
        } catch (NumberFormatException ex) {
            return "Se esperaba un codigo de ensamble valido";
        }
    }

    public ResultSet mostrarMueblesOrdenados(String orden) {
        try {

            Statement query = CONEXION.createStatement();
            ResultSet resultado = null;
            switch (orden) {
                case "ascendente":
                    resultado = query.executeQuery(
                            "SELECT * FROM ensamble ORDER BY fecha_de_ensamble ASC;");
                    break;
                case "descendente":
                    resultado = query.executeQuery(
                            "SELECT * FROM ensamble ORDER BY fecha_de_ensamble DESC;");
                    break;
            }
            return resultado;
        } catch (SQLException ex) {
            return null;
        }
    }

    public static int devolverPiezas(int codigoEnsamble) {
        try{
            String codigoMueble = "";
            String nombreDePieza = "";
            double precioDePieza = 0;
            int piezasNescesarias = 0;
            int filasAfectadas = 0;
            CONEXION.setAutoCommit(false);
            PreparedStatement query = CONEXION.prepareStatement(
                    "SELECT codigo_de_mueble FROM ensamble WHERE codigo_de_ensamble = ?");//pedimos la tabla de los codigos de muebles
            query.setInt(1, codigoEnsamble);   //damos valor a ?                                           
            ResultSet resultado = query.executeQuery();//ejecutamos el quey
            while(resultado.next()){//exploramos la tabla resultado
                codigoMueble = resultado.getString("codigo_de_mueble");//si entra entonces toma un valor
            }
            query = CONEXION.prepareStatement(
                    "SELECT * FROM formula WHERE codigo_de_mueble = ?");//pedimos la lista de piezas que se nescita para ese mueble
            query.setString(1, codigoMueble);//damos valor a ?
            resultado = query.executeQuery();
            while(resultado.next()){//exploramos la tabla resultado
               //obtenemos los valores de la tabla
                nombreDePieza = resultado.getString("nombre_de_pieza");
                precioDePieza = resultado.getDouble("precio_de_pieza");
                piezasNescesarias = resultado.getInt("piezas_necesarias");
                PreparedStatement query2 = CONEXION.prepareStatement( //con los valores que encontramos damos valores a los?
                        "UPDATE pieza SET existencias = existencias + ? WHERE nombre = ? AND precio = ?");
                query2.setInt(1, piezasNescesarias);
                query2.setString(2, nombreDePieza);
                query2.setDouble(3, precioDePieza);               
                filasAfectadas = filasAfectadas + query2.executeUpdate();
            }
            System.out.println(filasAfectadas);
            CONEXION.commit();
            return filasAfectadas;
        }catch(SQLException ex){
            try {
                CONEXION.rollback();
            } catch (SQLException ex1) {
            }
            return 0;
        }
    }

    /**
     * Este metodo busca el codigo de ensamble enviado y retorna el costo de
     * produccion de ese ensamble
     *
     * @param codigoDeEnsamble
     * @return
     */
    public static double saberConstoDeEnsamble(int codigoDeEnsamble) {
        try {
            double costoDeEnsamble = 0;
            PreparedStatement query = CONEXION.prepareStatement(
                    "SELECT costo_de_ensamble FROM ensamble WHERE codigo_de_ensamble = ?");//buscamos el codigo
            query.setInt(1, codigoDeEnsamble);
            ResultSet resultado = query.executeQuery();
            while (resultado.next()) { //en base al resultado obtenemos el valor del costo de ensamble
                costoDeEnsamble = resultado.getDouble("costo_de_ensamble");
            }
            return costoDeEnsamble;
        } catch (SQLException ex) {
            return 0;
        }

    }
}
