package com.javafit.service;

import com.javafit.data.JavaFitData;
import com.javafit.data.PersistenceManager;
import com.javafit.model.Actividad;
import com.javafit.model.ActividadEspecial;
import com.javafit.model.Administrador;
import com.javafit.model.Horario;
import com.javafit.model.Reserva;
import com.javafit.model.Sala;
import com.javafit.model.Socio;
import com.javafit.model.TipoActividad;
import com.javafit.model.Usuario;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Servicio de negocio principal de JavaFit.
 */
public class JavaFitService {
    private final JavaFitData data;
    private final PersistenceManager persistenceManager;

    public JavaFitService(JavaFitData data, PersistenceManager persistenceManager) {
        this.data = data;
        this.persistenceManager = persistenceManager;
    }

    /**
     * Inicializa datos de prueba cuando no existe información previa.
     */
    public void inicializarDatosSiVacio() {
        if (!data.getUsuarios().isEmpty() || !data.getActividades().isEmpty()) {
            return;
        }

        data.getUsuarios().add(new Administrador("admin@javafit.com", "admin"));

        Socio s1 = new Socio("Ana Ruiz", "ana@correo.com", "1234", "600100100", "Calle Sol 1", "1111-2222-3333-4444", false);
        Socio s2 = new Socio("Luis Pérez", "luis@correo.com", "1234", "600200200", "Calle Luna 4", "5555-6666-7777-8888", true);
        Socio s3 = new Socio("Marta León", "marta@correo.com", "1234", "600300300", "Av. Mar 2", "9999-0000-1111-2222", false);
        Socio s4 = new Socio("Jorge Gil", "jorge@correo.com", "1234", "600400400", "Plaza Norte 7", "3333-4444-5555-6666", true);
        data.getUsuarios().addAll(List.of(s1, s2, s3, s4));

        Sala salaA = new Sala("Sala Zen", 12);
        Sala salaB = new Sala("Sala Fuerza", 20);
        Sala salaC = new Sala("Piscina 1", 10);

        Actividad a1 = new Actividad("Yoga Iniciación", TipoActividad.YOGA, salaA,
                new Horario(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(11, 0)), "Carla", "images/yoga1.png");
        Actividad a2 = new Actividad("Cardio Express", TipoActividad.CARDIO, salaB,
                new Horario(DayOfWeek.TUESDAY, LocalTime.of(18, 0), LocalTime.of(19, 0)), "Raúl", "images/cardio.png");
        Actividad a3 = new Actividad("Musculación Total", TipoActividad.MUSCULACION, salaB,
                new Horario(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(20, 0)), "Irene", "images/musculacion.png");
        Actividad a4 = new Actividad("Nado Libre", TipoActividad.NATACION, salaC,
                new Horario(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(18, 0)), "Pablo", "images/natacion.png");
        Actividad a5 = new ActividadEspecial("Yoga Avanzado", TipoActividad.YOGA, salaA,
                new Horario(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(10, 30)), "Carla", "images/yoga2.png", 15.0, "Técnicas avanzadas");
        Actividad a6 = new ActividadEspecial("Aquafitness Premium", TipoActividad.NATACION, salaC,
                new Horario(DayOfWeek.SATURDAY, LocalTime.of(11, 0), LocalTime.of(12, 0)), "Pablo", "images/aqua.png", 20.0, "Sesión especial en piscina");

        data.getActividades().addAll(List.of(a1, a2, a3, a4, a5, a6));
        data.getReservas().add(new Reserva(s1, a1, LocalDateTime.now().minusDays(3), 0));
        data.getReservas().add(new Reserva(s2, a5, LocalDateTime.now().minusDays(2), s2.calcularPrecioEspecial(15.0)));
        data.getReservas().add(new Reserva(s3, a2, LocalDateTime.now().minusDays(1), 0));
    }

    /**
     * Guarda el estado actual de la aplicación en el fichero serializado.
     */
    public void guardar() {
        persistenceManager.save(data);
    }

    /**
     * Registra un nuevo socio.
     *
     * @param socio socio a registrar.
     * @return true si se registró, false si el correo ya estaba en uso.
     */
    public boolean registrarSocio(Socio socio) {
        boolean existe = data.getUsuarios().stream()
                .anyMatch(u -> u.getCorreo().equalsIgnoreCase(socio.getCorreo()));
        if (existe) {
            return false;
        }
        data.getUsuarios().add(socio);
        return true;
    }

    /**
     * Autentica un usuario por correo y clave.
     *
     * @param correo correo de login.
     * @param clave clave en texto plano.
     * @return usuario autenticado dentro de un Optional.
     */
    public Optional<Usuario> login(String correo, String clave) {
        return data.getUsuarios().stream()
                .filter(u -> u.getCorreo().equalsIgnoreCase(correo) && u.getClave().equals(clave))
                .findFirst();
    }

    /**
     * Crea una actividad y la añade al catálogo.
     *
     * @param actividad actividad nueva.
     */
    public void crearActividad(Actividad actividad) {
        data.getActividades().add(actividad);
    }

    /**
     * Actualiza los datos de una actividad existente.
     *
     * @param id identificador de actividad.
     * @param nueva actividad con valores actualizados.
     * @return true si se actualizó correctamente.
     */
    public boolean actualizarActividad(String id, Actividad nueva) {
        return data.getActividades().stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .map(a -> {
                    a.setTitulo(nueva.getTitulo());
                    a.setTipo(nueva.getTipo());
                    a.setSala(nueva.getSala());
                    a.setHorario(nueva.getHorario());
                    a.setMonitor(nueva.getMonitor());
                    a.setImagen(nueva.getImagen());
                    if (a instanceof ActividadEspecial actualEspecial && nueva instanceof ActividadEspecial nuevaEspecial) {
                        actualEspecial.setPrecio(nuevaEspecial.getPrecio());
                        actualEspecial.setDescripcion(nuevaEspecial.getDescripcion());
                    }
                    return true;
                }).orElse(false);
    }

    /**
     * Elimina una actividad y sus reservas asociadas.
     *
     * @param id identificador de actividad.
     * @return true si se eliminó al menos una actividad.
     */
    public boolean eliminarActividad(String id) {
        boolean removed = data.getActividades().removeIf(a -> a.getId().equals(id));
        if (removed) {
            data.getReservas().removeIf(r -> r.getActividad().getId().equals(id));
        }
        return removed;
    }

    /**
     * Busca actividades con filtros combinados opcionales.
     *
     * @param tipo tipo de actividad opcional.
     * @param monitor nombre del monitor opcional.
     * @param dia día de semana opcional.
     * @return lista filtrada de actividades.
     */
    public List<Actividad> buscarActividades(Optional<TipoActividad> tipo, Optional<String> monitor, Optional<DayOfWeek> dia) {
        return data.getActividades().stream()
                .filter(a -> tipo.map(t -> a.getTipo() == t).orElse(true))
                .filter(a -> monitor.map(m -> a.getMonitor().toLowerCase(Locale.ROOT).contains(m.toLowerCase(Locale.ROOT))).orElse(true))
                .filter(a -> dia.map(d -> a.getHorario().getDia() == d).orElse(true))
                .toList();
    }

    /**
     * Busca socios aplicando filtros opcionales por nombre, correo y tipo VIP.
     *
     * @param nombre parte del nombre opcional.
     * @param correo parte del correo opcional.
     * @param vip estado VIP opcional.
     * @return lista de socios filtrada.
     */
    public List<Socio> buscarSocios(Optional<String> nombre, Optional<String> correo, Optional<Boolean> vip) {
        return data.getUsuarios().stream()
                .filter(u -> u instanceof Socio)
                .map(u -> (Socio) u)
                .filter(s -> nombre.map(n -> s.getNombre().toLowerCase(Locale.ROOT).contains(n.toLowerCase(Locale.ROOT))).orElse(true))
                .filter(s -> correo.map(c -> s.getCorreo().toLowerCase(Locale.ROOT).contains(c.toLowerCase(Locale.ROOT))).orElse(true))
                .filter(s -> vip.map(v -> s.isVip() == v).orElse(true))
                .toList();
    }

    /**
     * Lista las reservas ordenadas por fecha, con filtro opcional desde una fecha.
     *
     * @param desde fecha mínima opcional.
     * @return reservas ordenadas ascendentemente por fecha.
     */
    public List<Reserva> listarReservasOrdenadas(Optional<LocalDate> desde) {
        return data.getReservas().stream()
                .filter(r -> desde.map(f -> !r.getFechaReserva().toLocalDate().isBefore(f)).orElse(true))
                .sorted(Comparator.comparing(Reserva::getFechaReserva))
                .toList();
    }

    /**
     * Registra una reserva verificando aforo y genera recibo si la actividad es especial.
     *
     * @param socio socio que reserva.
     * @param actividad actividad reservada.
     * @param carpetaRecibos carpeta de salida para recibos.
     * @return reserva creada.
     */
    public Reserva reservarActividad(Socio socio, Actividad actividad, Path carpetaRecibos) {
        long plazasOcupadas = data.getReservas().stream()
                .filter(r -> r.getActividad().getId().equals(actividad.getId()))
                .count();

        if (plazasOcupadas >= actividad.getSala().getAforoMaximo()) {
            throw new IllegalStateException("No hay plazas disponibles para la actividad");
        }

        double precioFinal = 0;
        if (actividad instanceof ActividadEspecial especial) {
            precioFinal = socio.calcularPrecioEspecial(especial.getPrecio());
            generarRecibo(socio, especial, precioFinal, carpetaRecibos);
        }

        Reserva reserva = new Reserva(socio, actividad, LocalDateTime.now(), precioFinal);
        data.getReservas().add(reserva);
        return reserva;
    }

    /**
     * Cancela una reserva por su identificador.
     *
     * @param reservaId identificador de la reserva.
     * @param correoSocio correo del socio solicitante.
     * @return true si se eliminó la reserva.
     */
    public boolean cancelarReserva(String reservaId, String correoSocio) {
        return data.getReservas().removeIf(r -> r.getId().equals(reservaId)
                && r.getSocio().getCorreo().equalsIgnoreCase(correoSocio));
    }

    /**
     * Actualiza los datos personales de un socio.
     *
     * @param correo correo del socio.
     * @param telefono nuevo teléfono.
     * @param direccion nueva dirección.
     * @param tarjeta nueva tarjeta.
     * @param vip nuevo estado VIP.
     * @return true si el socio existe y fue actualizado.
     */
    public boolean actualizarPerfilSocio(String correo, String telefono, String direccion, String tarjeta, boolean vip) {
        return data.getUsuarios().stream()
                .filter(u -> u instanceof Socio)
                .map(u -> (Socio) u)
                .filter(s -> s.getCorreo().equalsIgnoreCase(correo))
                .findFirst()
                .map(s -> {
                    s.setTelefono(telefono);
                    s.setDireccion(direccion);
                    s.setTarjetaCredito(tarjeta);
                    s.setVip(vip);
                    return true;
                }).orElse(false);
    }

    public List<Actividad> getActividades() {
        return new ArrayList<>(data.getActividades());
    }

    public List<Usuario> getUsuarios() {
        return new ArrayList<>(data.getUsuarios());
    }

    private void generarRecibo(Socio socio, ActividadEspecial actividad, double precioFinal, Path carpetaRecibos) {
        try {
            Files.createDirectories(carpetaRecibos);
            String archivo = "recibo_" + socio.getCorreo().replace("@", "_") + "_" + System.currentTimeMillis() + ".txt";
            Path salida = carpetaRecibos.resolve(archivo);
            String contenido = "JavaFit - Recibo de actividad especial\n"
                    + "Socio: " + socio.getNombre() + " (" + socio.getCorreo() + ")\n"
                    + "Actividad: " + actividad.getTitulo() + "\n"
                    + "Precio base: " + actividad.getPrecio() + " EUR\n"
                    + "Precio final: " + precioFinal + " EUR\n"
                    + "Fecha: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n";
            Files.writeString(salida, contenido);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo generar el recibo", e);
        }
    }
}
