
package AreaFinanciera;

public class VentaDeVendedor {
    private String usuarioDeVendedor;
    private double dineroEnVenta = 0;
    private double costoDeProduccion = 0;
    private double gananciaGenerada = 0;

    public VentaDeVendedor(String usuarioDeVendedor, double dineroEnVenta, double costoDeProduccion) {
        this.usuarioDeVendedor = usuarioDeVendedor;
        this.dineroEnVenta = dineroEnVenta;
        this.costoDeProduccion = costoDeProduccion;
    }

    public String getUsuarioDeVendedor() {
        return usuarioDeVendedor;
    }

    public double getDineroEnVenta() {
        return dineroEnVenta;
    }

    public double getGananciaGenerada() {
        return gananciaGenerada;
    }

    public double getCostoDeProduccion() {
        return costoDeProduccion;
    }

    public void setUsuarioDeVendedor(String usuarioDeVendedor) {
        this.usuarioDeVendedor = usuarioDeVendedor;
    }

    public void setDineroEnVenta(double dineroEnVenta) {
        this.dineroEnVenta = dineroEnVenta;
    }

    public void setCostoDeProduccion(double costoDeProduccion) {
        this.costoDeProduccion = costoDeProduccion;
    }

    public void setGananciaGenerada() {
        this.gananciaGenerada = this.dineroEnVenta - this.costoDeProduccion;
    }
    
}
