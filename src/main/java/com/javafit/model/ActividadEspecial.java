package com.javafit.model;

import java.io.Serial;

/**
 * Actividad especial con precio y descripción.
 */
public class ActividadEspecial extends Actividad {
    @Serial
    private static final long serialVersionUID = 1L;

    private double precio;
    private String descripcion;

    public ActividadEspecial(String titulo, TipoActividad tipo, Sala sala, Horario horario,
                             String monitor, String imagen, double precio, String descripcion) {
        super(titulo, tipo, sala, horario, monitor, imagen);
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean esEspecial() {
        return true;
    }
}
