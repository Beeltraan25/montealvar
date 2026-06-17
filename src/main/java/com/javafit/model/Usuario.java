package com.javafit.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Clase base para los usuarios del sistema.
 */
public abstract class Usuario implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String correo;
    private String clave;

    protected Usuario(String correo, String clave) {
        this.correo = correo;
        this.clave = clave;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario usuario)) return false;
        return Objects.equals(correo, usuario.correo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(correo);
    }
}
