package AreaDeVentas;

import static ConexionMysql.Conexion.CONEXION;
import java.sql.*;
import java.text.SimpleDateFormat;

public class GestorDeConsulta {

    public ResultSet comprasEnIntervaloDeTiempo(String nit, java.util.Date fecha1, java.util.Date fecha2) {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd"); //nos servira para dar fomratosql date a util date
            String fecha1String = formato.format(fecha1); //le damos formato
            String fecha2String = formato.format(fecha2);//le damos formato
            PreparedStatement query = CONEXION.prepareStatement(
                    "SELECT a.codigo_de_factura, b.nit, b.nombre, b.direccion, d.nombre, d.precio, a.fecha_de_compra\n"
                    + "FROM compra a INNER JOIN cliente b INNER JOIN ensamble c INNER JOIN\n"
                    + "mueble d ON a.codigo_de_cliente = b.nit and a.codigo_de_ensamble = c.codigo_de_ensamble and c.codigo_de_mueble = d.nombre\n"
                    + "and a.fecha_de_compra BETWEEN ? AND ? AND b.nit = ?"); //hacemos la consulta
            query.setString(1, fecha1String);//damos valores a ?
            query.setString(2, fecha2String);
            query.setString(3, nit);
            ResultSet resultado = query.executeQuery();//ejecutamos el query
            return resultado;//retornmamos el resultado
        } catch (SQLException ex) {
            return null;
        }
    }

    public ResultSet devolucionesEnUNLapsoDeTiempo(String nit, java.util.Date fecha1, java.util.Date fecha2) {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd"); //nos servira para dar fomratosql date a util date
            String fecha1String = formato.format(fecha1); //le damos formato
            String fecha2String = formato.format(fecha2);//le damos formato
            PreparedStatement query = CONEXION.prepareStatement(
                    "SELECT a.codigo_de_cliente, b.nombre ,a.codigo_de_ensamble, c.codigo_de_mueble, a.motivo, a.fecha_de_devolucion FROM devolucion a INNER JOIN"
                    + " cliente b INNER JOIN ensamble c ON a.codigo_de_cliente = b.nit"
                    + " and a.codigo_de_ensamble = c.codigo_de_ensamble"
                    + " and a.fecha_de_devolucion BETWEEN ? AND ? and a.codigo_de_cliente = ?;"); //hacemos la consulta
            query.setString(1, fecha1String);//damos valores a ?
            query.setString(2, fecha2String);
            query.setString(3, nit);
            ResultSet resultado = query.executeQuery();//ejecutamos el query
            return resultado;//retornmamos el resultado
        } catch (SQLException ex) {
            return null;
        }
    }

    public ResultSet ventasDeHoy() {
        try {
            java.util.Date hoy = new java.util.Date();
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd"); //nos servira para dar fomratosql date a util date
            String fechaHoy = formato.format(hoy); //le damos formato
            PreparedStatement query = CONEXION.prepareStatement(
                    "SELECT a.codigo_de_factura, b.nit, b.nombre, b.direccion, d.nombre, d.precio, a.fecha_de_compra\n"
                    + "FROM compra a INNER JOIN cliente b INNER JOIN ensamble c INNER JOIN\n"
                    + "mueble d ON a.codigo_de_cliente = b.nit and a.codigo_de_ensamble = c.codigo_de_ensamble and c.codigo_de_mueble = d.nombre\n"
                    + "and a.fecha_de_compra = ?"); //hacemos la consulta
            query.setString(1, fechaHoy);//damos valores a ?
            ResultSet resultado = query.executeQuery();//ejecutamos el query
            return resultado;//retornmamos el resultado
        } catch (SQLException ex) {
            return null;
        }
    }

    public ResultSet detallesDeFactura(int codigoDeFactura) {
        try {
            PreparedStatement query = CONEXION.prepareStatement(
                    "SELECT a.codigo_de_factura, b.nit, b.nombre, b.direccion, d.nombre, d.precio, a.fecha_de_compra\n"
                    + "FROM compra a INNER JOIN cliente b INNER JOIN ensamble c INNER JOIN\n"
                    + "mueble d ON a.codigo_de_cliente = b.nit and a.codigo_de_ensamble = c.codigo_de_ensamble and c.codigo_de_mueble = d.nombre\n"
                    + "and a.codigo_de_factura = ?"
            ); //hacemos la consulta
            query.setInt(1, codigoDeFactura);//damos valores a ?
            ResultSet resultado = query.executeQuery();//ejecutamos el query
            return resultado;//retornmamos el resultado
        } catch (SQLException ex) {
            return null;
        }
    }
}
