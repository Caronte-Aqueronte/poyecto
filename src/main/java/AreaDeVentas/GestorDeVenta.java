package AreaDeVentas;

import AreaDeFabrica.GestorDeEnsamble;
import static ConexionMysql.Conexion.CONEXION;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import static java.time.temporal.ChronoUnit.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GestorDeVenta {

    public int generarCodigoDeFactura() {
        try {
            int nuevoCodigo = 1;
            boolean seguirBuscando = true;
            PreparedStatement query;
            ResultSet resultado;
            while (seguirBuscando) {
                seguirBuscando = false; // si esta bandera no se vulve a activar entonces no hay registros con ese codigo y podemos retornar el nuevo codigo de factura
                query = CONEXION.prepareStatement("SELECT * FROM compra WHERE codigo_de_factura=?;"); //preparamos la query
                query.setInt(1, nuevoCodigo);//damos valor al ?
                resultado = query.executeQuery();//ejecutamos
                whileInterior:
                while (resultado.next()) { //si entra entonces esa clave ya existe
                    seguirBuscando = true; //hacemos true para que siga buscando
                    nuevoCodigo++;// aumentamos el codigo
                    break whileInterior;//rompemos este whole
                }
            }
            //si se llega aqui enrtonces hay una clave que no existe y la retornamos
            return nuevoCodigo;
        } catch (SQLException e) {
            return 0;
        }
    }

    public String venderArticulos(int nuevaFactura, ArrayList<ProductoEnVenta> venta, String codigoCliente, String usuarioVendedor) {

        try {
            CONEXION.setAutoCommit(false);
            PreparedStatement query;
            int codigoDeEnsamble = 0;
            for (ProductoEnVenta item : venta) {
                if (!codigoCliente.equals("") && item.getCodigoDeEnsamble() != 0) {
                    codigoDeEnsamble = item.getCodigoDeEnsamble();
                    query = CONEXION.prepareStatement("UPDATE stock SET estado = ? WHERE codigo_de_ensamble = ? ");
                    query.setString(1, "Vendido");
                    query.setInt(2, codigoDeEnsamble);
                    query.executeUpdate();
                    query = CONEXION.prepareStatement("INSERT INTO compra VALUES (?,?,?,?, CURDATE())");
                    query.setInt(1, nuevaFactura);
                    query.setString(2, codigoCliente);
                    query.setInt(3, codigoDeEnsamble);
                    query.setString(4, usuarioVendedor);
                    query.executeUpdate();
                } else {
                    throw new SQLException("Error, no se permiten parametros vacios");
                }
            }
            CONEXION.commit();
            return "Se realizo la venta con exito";
        } catch (SQLException ex) {
            try {
                CONEXION.rollback();
                return ex.getMessage();
            } catch (SQLException ex1) {
                return "Error critico al hacer el rolback";
            }
        }
    }

    public ResultSet buscarNit(String nit) {
        try {
            PreparedStatement query = CONEXION.prepareStatement(
                    "SELECT * FROM cliente WHERE nit = ?");
            query.setString(1, nit);
            ResultSet resultado = query.executeQuery();
            return resultado;
        } catch (SQLException ex) {
            return null;
        }
    }

    public String hacerDevolucion(String codigoDeCliente, String motivo, int codigoDeFactura, int codigoDeEnsamble) {
        try {
            boolean bandera = false;
            PreparedStatement query = CONEXION.prepareStatement(
                    "SELECT * FROM compra WHERE codigo_de_factura = ? AND codigo_de_cliente = ?"); //seleccionamos la factura con ese codigo
            query.setInt(1, codigoDeFactura);
            query.setString(2, codigoDeCliente);
            ResultSet resultado = query.executeQuery();
            while (resultado.next()) {
                bandera = true; //si existe ese codigo entonces la vandera se hara true
            }
            if (bandera == true) {//si la bandera es true entonces existe el codigo y seguimos
                java.util.Date fechaDeCompra = null;
                bandera = false; // hacemos la bandera false para otra comparacion
                query = CONEXION.prepareStatement(
                        "SELECT * FROM compra WHERE codigo_de_factura = ? AND codigo_de_ensamble = ?");
                query.setInt(1, codigoDeFactura);
                query.setInt(2, codigoDeEnsamble);
                resultado = query.executeQuery();
                while (resultado.next()) {
                    bandera = true;//si existe ese codigo y el ensamble es de ese codigo entonces la vandera se hara true
                    fechaDeCompra = resultado.getDate("fecha_de_compra");//obtenemos la fecha de compra
                }
                if (bandera == true) {//si la bandera es true entonces existe el codigo y seguimos a analizar la fecha 
                    if (saberSiPasoUnaSemana(fechaDeCompra) == true) {//si es true entonces podemos hacer la devolucion
                        CONEXION.setAutoCommit(false);
                        double perdida = GestorDeEnsamble.saberConstoDeEnsamble(codigoDeEnsamble) / 3; //obtenemos la perdida con la formula
                        PreparedStatement query2 = CONEXION.prepareStatement(//hacemos la consulta
                                "INSERT INTO devolucion VALUES (?,?,?,?,CURDATE())");
                        query2.setString(1, codigoDeCliente);
                        query2.setInt(2, codigoDeEnsamble);
                        query2.setString(3, motivo);
                        query2.setDouble(4, perdida);
                        if (query2.executeUpdate() > 0) {//si se afectan filas entonces se hizo la devolucin en la base de datos
                            //devolvemos el ensamble a la fabrica
                            GestorDeEnsamble.devolverPiezas(codigoDeEnsamble);
                            CONEXION.commit();
                            return "Se hizo la devolución";//FINALMENTE NOTIFICAMOS LA DEVOLUCION
                        } else {
                            throw new Exception("No se hizo la devolucion, parametros erroneos");
                        }
                    } else {//si no entonces la rechazamos
                        throw new Exception("No se puede hacer la devolución, ha pasado mas de una semana desde la compra");
                    }

                } else {//si no lanzamos una excepcion
                    throw new Exception("El codigo de ensamble no pertenece a la factura");
                }
            } else {//si no lanzamos una excepcion
                throw new Exception("La factura no existe o NIT y codigo de factura no coinciden");
            }

        } catch (SQLException ex) {
            try {
                CONEXION.rollback();
            } catch (SQLException ex1) {
                
            }
            return ex.getMessage();
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public boolean saberSiPasoUnaSemana(java.util.Date fechaDeCompra) {
        java.util.Date fechaDeHoy = new java.util.Date(); //obtenemos la fecha de hoy
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); //este formato le daremos a las fechas Date
        String fechaDeHoyConFormato = formato.format(fechaDeHoy);//damos formato a las fechas de compra y de hoy
        String fechaDeCompraConFormato = formato.format(fechaDeCompra);//
        DateTimeFormatter formato2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");//creamos un formato
        ChronoLocalDate compra = ChronoLocalDate.from(formato2.parse(fechaDeCompraConFormato)); //obtenemos el ChronoLocalDate
        ChronoLocalDate hoy = ChronoLocalDate.from(formato2.parse(fechaDeHoyConFormato));//
        ChronoPeriod tiempoTransucurrido = ChronoPeriod.between(compra, hoy);//obtenemos el perido de tiempo entre las fechas
        if (tiempoTransucurrido.get(DAYS) > 7) { //si el perido de dias es mayot a 7 entonces ya paso mas de una semana
            return false;
        }
        return true;
    }
}
