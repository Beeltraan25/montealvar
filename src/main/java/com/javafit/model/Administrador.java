package com.javafit.model;

import java.io.Serial;

/**
 * Usuario administrador del sistema.
 */
public class Administrador extends Usuario {
    @Serial
    private static final long serialVersionUID = 1L;

    public Administrador(String correo, String clave) {
        super(correo, clave);
    }
}
