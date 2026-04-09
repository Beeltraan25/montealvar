package com.javafit.data;

import com.javafit.model.Actividad;
import com.javafit.model.Reserva;
import com.javafit.model.Usuario;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Contenedor serializable del estado de la aplicación.
 */
public class JavaFitData implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<Usuario> usuarios = new ArrayList<>();
    private List<Actividad> actividades = new ArrayList<>();
    private List<Reserva> reservas = new ArrayList<>();

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
}
