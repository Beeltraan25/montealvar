package com.javafit.app;

import com.javafit.controller.AuthController;
import com.javafit.data.JavaFitData;
import com.javafit.data.PersistenceManager;
import com.javafit.gui.LoginFrame;
import com.javafit.service.JavaFitService;

import java.nio.file.Path;

/**
 * Punto de entrada de la aplicación JavaFit.
 */
public class JavaFitApplication {

    public static void main(String[] args) {
        Path storage = Path.of("data", "javafit.ser");
        PersistenceManager persistenceManager = new PersistenceManager(storage);
        JavaFitData data = persistenceManager.load();
        JavaFitService service = new JavaFitService(data, persistenceManager);

        service.inicializarDatosSiVacio();
        Runtime.getRuntime().addShutdownHook(new Thread(service::guardar));

        AuthController authController = new AuthController(service);
        LoginFrame.show(authController);
    }
}
