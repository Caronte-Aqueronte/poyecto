package AreaFinanciera;

import static ConexionMysql.Conexion.CONEXION;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Reporte {

    public ResultSet ventasEnFechas(String primeraFecha, String segundaFecha) {
        try {
            PreparedStatement query = CONEXION.prepareStatement(
                    "SELECT a.codigo_de_factura, b.nit, b.nombre, b.direccion, c.codigo_de_ensamble, d.nombre, d.precio, a.fecha_de_compra\n"
                    + "FROM compra a INNER JOIN cliente b INNER JOIN ensamble c INNER JOIN\n"
                    + "mueble d ON a.codigo_de_cliente = b.nit and a.codigo_de_ensamble = c.codigo_de_ensamble and c.codigo_de_mueble = d.nombre\n"
                    + "and a.fecha_de_compra BETWEEN ? AND ?;");
            query.setString(1, primeraFecha);
            query.setString(2, segundaFecha);
            ResultSet resultado = query.executeQuery();
            return resultado;
        } catch (Exception e) {
            return null;
        }
    }

    public ResultSet ventasEnFechasConVendedor(String primeraFecha, String segundaFecha) {
        try {
            PreparedStatement query = CONEXION.prepareStatement(
                    "SELECT a.usuario_vendedor, a.codigo_de_factura, b.nit, b.nombre, b.direccion, c.codigo_de_ensamble, d.nombre, d.precio, a.fecha_de_compra\n"
                    + "FROM compra a INNER JOIN cliente b INNER JOIN ensamble c INNER JOIN\n"
                    + "mueble d ON a.codigo_de_cliente = b.nit and a.codigo_de_ensamble = c.codigo_de_ensamble and c.codigo_de_mueble = d.nombre\n"
                    + "and a.fecha_de_compra BETWEEN ? AND ?;");
            query.setString(1, primeraFecha);
            query.setString(2, segundaFecha);
            ResultSet resultado = query.executeQuery();
            return resultado;
        } catch (Exception e) {
            return null;
        }
    }

    public ResultSet devolucionesEnFechas(String primeraFecha, String segundaFecha) {
        try {
            PreparedStatement query = CONEXION.prepareStatement(
                    "SELECT a.codigo_de_cliente, b.nombre, a.codigo_de_ensamble, c.codigo_de_mueble, a.motivo, a.perdida, a.fecha_de_devolucion  FROM devolucion a INNER JOIN cliente b \n"
                    + "INNER JOIN ensamble c ON a.codigo_de_cliente = b.nit and\n"
                    + "c.codigo_de_ensamble = a.codigo_de_ensamble and a.fecha_de_devolucion BETWEEN ? AND ?;");
            query.setString(1, primeraFecha);
            query.setString(2, segundaFecha);
            ResultSet resultado = query.executeQuery();
            return resultado;
        } catch (Exception e) {
            return null;
        }
    }

    public ResultSet usuarioConMasVentasEnFechas(String primeraFecha, String segundaFecha) {
        try {
            PreparedStatement query = CONEXION.prepareStatement(
                    "SELECT usuario_vendedor , COUNT( usuario_vendedor ) AS numero_de_ventas\n"
                    + "FROM  compra ON fecha_de_compra BETWEEN ? and ?\n"
                    + "GROUP BY usuario_vendedor\n"
                    + "ORDER BY numero_de_ventas DESC ");
            query.setString(1, primeraFecha);
            query.setString(2, segundaFecha);
            ResultSet resultado = query.executeQuery();
            return resultado;
        } catch (Exception e) {
            return null;
        }
    }

    public ResultSet muebleMasVendidoEnFechas(String primeraFecha, String segundaFecha) {
        try {
            PreparedStatement query = CONEXION.prepareStatement(//preparamos la query que nos dara los parametors nescesarios para la infomacion
                    "SELECT b.codigo_de_mueble , COUNT( b.codigo_de_mueble ) AS numero_de_ventas\n"
                    + "FROM  compra a INNER JOIN ensamble b \n"
                    + "ON a.codigo_de_ensamble = b.codigo_de_ensamble AND a.fecha_de_compra BETWEEN ? and ?\n"
                    + "GROUP BY b.codigo_de_mueble\n"
                    + "ORDER BY numero_de_ventas DESC");
            //damos valores a los ?
            query.setString(1, primeraFecha);
            query.setString(2, segundaFecha);
            //ejecutamos la query
            ResultSet resultado = query.executeQuery();
            //retornamos el resultset
            return resultado;
        } catch (Exception e) {
            return null;
        }
    }
    public ResultSet muebleMenosVendidoEnFechas(String primeraFecha, String segundaFecha) {
        try {
            PreparedStatement query = CONEXION.prepareStatement(//preparamos la query que nos dara los parametors nescesarios para la infomacion
                    "SELECT b.codigo_de_mueble , COUNT( b.codigo_de_mueble ) AS numero_de_ventas\n"
                    + "FROM  compra a INNER JOIN ensamble b \n"
                    + "ON a.codigo_de_ensamble = b.codigo_de_ensamble AND a.fecha_de_compra BETWEEN ? and ?\n"
                    + "GROUP BY b.codigo_de_mueble\n"
                    + "ORDER BY numero_de_ventas ASC");
            //damos valores a los ?
            query.setString(1, primeraFecha);
            query.setString(2, segundaFecha);
            //ejecutamos la query
            ResultSet resultado = query.executeQuery();
            //retornamos el resultset
            return resultado;
        } catch (Exception e) {
            return null;
        }
    }

    public ResultSet ventasDeUsuarioEnFechas(String primeraFecha, String segundaFecha, String usuario) {
        try {
            PreparedStatement query = CONEXION.prepareStatement(
                    "SELECT a.usuario_vendedor, a.codigo_de_factura, b.nit, b.nombre, b.direccion, c.codigo_de_ensamble, d.nombre, d.precio, a.fecha_de_compra\n"
                    + "FROM compra a INNER JOIN cliente b INNER JOIN ensamble c INNER JOIN\n"
                    + "mueble d ON a.codigo_de_cliente = b.nit and a.codigo_de_ensamble = c.codigo_de_ensamble and c.codigo_de_mueble = d.nombre\n"
                    + "and a.fecha_de_compra BETWEEN ? AND ? and a.usuario_vendedor like ?;");
            query.setString(1, primeraFecha);
            query.setString(2, segundaFecha);
            query.setString(3, "%" + usuario + "%");
            ResultSet resultado = query.executeQuery();
            return resultado;
        } catch (Exception e) {
            return null;
        }
    }

    public ResultSet ventasDeUnMueble(String primeraFecha, String segundaFecha, String mueble) {
        try {
            PreparedStatement query = CONEXION.prepareStatement(
                    "SELECT a.codigo_de_factura, a.codigo_de_ensamble , b.codigo_de_mueble, a.usuario_vendedor, a.fecha_de_compra FROM compra a INNER JOIN ensamble b\n"
                    + "ON a.fecha_de_compra BETWEEN  ? AND ? AND b.codigo_de_ensamble = a.codigo_de_ensamble \n"
                    + "and b.codigo_de_mueble like ?;");
            query.setString(1, primeraFecha);
            query.setString(2, segundaFecha);
            query.setString(3, "%" + mueble + "%");
            ResultSet resultado = query.executeQuery();
            return resultado;
        } catch (Exception e) {
            return null;
        }
    }

    public static String obtenerFechaYHoraActual() {
        java.util.Date fecha = new java.util.Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH.mm.ss");
        String fechaConFormato = formato.format(fecha);
        return fechaConFormato;
    }
}
