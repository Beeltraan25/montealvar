package com.javafit.data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Gestor de persistencia por serialización en archivo.
 */
public class PersistenceManager {
    private final Path dataPath;

    public PersistenceManager(Path dataPath) {
        this.dataPath = dataPath;
    }

    public JavaFitData load() {
        if (Files.notExists(dataPath)) {
            return new JavaFitData();
        }
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(dataPath))) {
            return (JavaFitData) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException("No se pudo cargar la información serializada", e);
        }
    }

    public void save(JavaFitData data) {
        try {
            Files.createDirectories(dataPath.getParent());
            try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(dataPath))) {
                oos.writeObject(data);
            }
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo guardar la información serializada", e);
        }
    }
}
