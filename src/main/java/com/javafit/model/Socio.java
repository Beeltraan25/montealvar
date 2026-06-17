package com.javafit.model;

import java.io.Serial;

/**
 * Usuario socio del gimnasio.
 */
public class Socio extends Usuario {
    @Serial
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String telefono;
    private String direccion;
    private String tarjetaCredito;
    private boolean vip;

    public Socio(String nombre, String correo, String clave, String telefono, String direccion, String tarjetaCredito, boolean vip) {
        super(correo, clave);
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.tarjetaCredito = tarjetaCredito;
        this.vip = vip;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTarjetaCredito() {
        return tarjetaCredito;
    }

    public void setTarjetaCredito(String tarjetaCredito) {
        this.tarjetaCredito = tarjetaCredito;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    /**
     * Calcula el precio de una actividad especial según el tipo de socio.
     * El socio VIP aplica un 10% de descuento.
     *
     * @param precioBase precio definido en la actividad especial.
     * @return precio final a pagar por el socio.
     */
    public double calcularPrecioEspecial(double precioBase) {
        return vip ? precioBase * 0.9 : precioBase;
    }
}
