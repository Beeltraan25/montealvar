package com.javafit.controller;

import com.javafit.model.Socio;
import com.javafit.model.Usuario;
import com.javafit.service.JavaFitService;

import java.util.Optional;

/**
 * Controlador MVC para operaciones de autenticación.
 */
public class AuthController {
    private final JavaFitService service;

    public AuthController(JavaFitService service) {
        this.service = service;
    }

    public Optional<Usuario> login(String correo, String clave) {
        return service.login(correo, clave);
    }

    public boolean registrarSocio(Socio socio) {
        return service.registrarSocio(socio);
    }

    public JavaFitService getService() {
        return service;
    }
}
