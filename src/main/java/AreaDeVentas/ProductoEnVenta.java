/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AreaDeVentas;

/**
 *
 * @author Luis Monterroso
 */
public class ProductoEnVenta {

    private int codigoDeEnsamble;
    private String codigoMueble;
    private double precio;

    public ProductoEnVenta(int codigoDeEnsamble, String codigoMueble, double precio) {
        this.codigoDeEnsamble = codigoDeEnsamble;
        this.codigoMueble = codigoMueble;
        this.precio = precio;
    }

    public int getCodigoDeEnsamble() {
        return codigoDeEnsamble;
    }

    public String getCodigoMueble() {
        return codigoMueble;
    }

    public double getPrecio() {
        return precio;
    }
    
}
