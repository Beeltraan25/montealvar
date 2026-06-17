package com.javafit.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * Actividad base del sistema.
 */
public class Actividad implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String id;
    private String titulo;
    private TipoActividad tipo;
    private Sala sala;
    private Horario horario;
    private String monitor;
    private String imagen;

    public Actividad(String titulo, TipoActividad tipo, Sala sala, Horario horario, String monitor, String imagen) {
        this.id = UUID.randomUUID().toString();
        this.titulo = titulo;
        this.tipo = tipo;
        this.sala = sala;
        this.horario = horario;
        this.monitor = monitor;
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public TipoActividad getTipo() {
        return tipo;
    }

    public void setTipo(TipoActividad tipo) {
        this.tipo = tipo;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean esEspecial() {
        return false;
    }
}
