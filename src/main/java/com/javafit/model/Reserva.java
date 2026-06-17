package com.javafit.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Reserva de un socio para una actividad.
 */
public class Reserva implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String id;
    private final Socio socio;
    private final Actividad actividad;
    private final LocalDateTime fechaReserva;
    private final double precioFinal;

    public Reserva(Socio socio, Actividad actividad, LocalDateTime fechaReserva, double precioFinal) {
        this.id = UUID.randomUUID().toString();
        this.socio = socio;
        this.actividad = actividad;
        this.fechaReserva = fechaReserva;
        this.precioFinal = precioFinal;
    }

    public String getId() {
        return id;
    }

    public Socio getSocio() {
        return socio;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }
}
