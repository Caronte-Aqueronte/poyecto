
package AreaDeFabrica;

public class Pieza {
    private String nombreDePieza;
    private double precio;
    private int existencias;

    public Pieza(String nombreDePieza, double precio, int existencias) {
        this.nombreDePieza = nombreDePieza;
        this.precio = precio;
        this.existencias = existencias;
    }

    public String getNombreDePieza() {
        return nombreDePieza;
    }

    public void setNombreDePieza(String nombreDePieza) {
        this.nombreDePieza = nombreDePieza;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }
    
}
