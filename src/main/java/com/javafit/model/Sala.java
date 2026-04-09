package com.javafit.model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Sala física donde se realiza una actividad.
 */
public class Sala implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String nombre;
    private int aforoMaximo;

    public Sala(String nombre, int aforoMaximo) {
        this.nombre = nombre;
        this.aforoMaximo = aforoMaximo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAforoMaximo() {
        return aforoMaximo;
    }

    public void setAforoMaximo(int aforoMaximo) {
        this.aforoMaximo = aforoMaximo;
    }
}
