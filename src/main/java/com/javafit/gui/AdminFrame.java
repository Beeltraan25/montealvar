package com.javafit.gui;

import com.javafit.model.Actividad;
import com.javafit.model.ActividadEspecial;
import com.javafit.model.Horario;
import com.javafit.model.Reserva;
import com.javafit.model.Sala;
import com.javafit.model.Socio;
import com.javafit.model.TipoActividad;
import com.javafit.service.JavaFitService;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Ventana principal para el rol administrador.
 */
public class AdminFrame extends JFrame {
    private final JavaFitService service;
    private final LoginFrame loginFrame;

    private final DefaultTableModel actividadesModel = new DefaultTableModel(new String[]{"ID", "Título", "Tipo", "Monitor", "Sala", "Horario", "Especial"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private final DefaultTableModel sociosModel = new DefaultTableModel(new String[]{"Nombre", "Correo", "Teléfono", "VIP"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private final DefaultTableModel reservasModel = new DefaultTableModel(new String[]{"Fecha", "Socio", "Actividad", "Precio"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public AdminFrame(JavaFitService service, LoginFrame loginFrame) {
        this.service = service;
        this.loginFrame = loginFrame;
        setTitle("JavaFit - Panel Administrador");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                loginFrame.setVisible(true);
            }
        });

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Gestión actividades", buildGestionActividadesPanel());
        tabs.addTab("Consulta actividades", buildConsultaActividadesPanel());
        tabs.addTab("Consulta socios", buildConsultaSociosPanel());
        tabs.addTab("Consulta reservas", buildConsultaReservasPanel());
        add(tabs);
    }

    private JPanel buildGestionActividadesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTable table = new JTable(actividadesModel);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton crear = new JButton("Crear");
        JButton editar = new JButton("Modificar");
        JButton eliminar = new JButton("Eliminar");
        JButton refrescar = new JButton("Refrescar");
        buttons.add(crear);
        buttons.add(editar);
        buttons.add(eliminar);
        buttons.add(refrescar);
        panel.add(buttons, BorderLayout.NORTH);

        crear.addActionListener(e -> {
            Actividad actividad = promptActividad(null);
            if (actividad != null) {
                service.crearActividad(actividad);
                cargarActividades(service.getActividades());
            }
        });

        editar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona una actividad");
                return;
            }
            String id = (String) actividadesModel.getValueAt(row, 0);
            Optional<Actividad> actual = service.getActividades().stream().filter(a -> a.getId().equals(id)).findFirst();
            if (actual.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontró la actividad", "JavaFit", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Actividad nueva = promptActividad(actual.get());
            if (nueva != null) {
                service.actualizarActividad(id, nueva);
                cargarActividades(service.getActividades());
            }
        });

        eliminar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona una actividad");
                return;
            }
            String id = (String) actividadesModel.getValueAt(row, 0);
            service.eliminarActividad(id);
            cargarActividades(service.getActividades());
        });

        refrescar.addActionListener(e -> cargarActividades(service.getActividades()));
        cargarActividades(service.getActividades());
        return panel;
    }

    private JPanel buildConsultaActividadesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Título", "Tipo", "Monitor", "Sala", "Horario", "Especial"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel filters = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> tipo = new JComboBox<>(new String[]{"TODAS", "YOGA", "CARDIO", "MUSCULACION", "NATACION"});
        JTextField monitor = new JTextField(10);
        JComboBox<String> dia = new JComboBox<>(new String[]{"TODOS", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"});
        JButton buscar = new JButton("Buscar");
        JButton limpiar = new JButton("Limpiar");

        filters.add(new JLabel("Tipo:"));
        filters.add(tipo);
        filters.add(new JLabel("Monitor:"));
        filters.add(monitor);
        filters.add(new JLabel("Día:"));
        filters.add(dia);
        filters.add(buscar);
        filters.add(limpiar);
        panel.add(filters, BorderLayout.NORTH);

        buscar.addActionListener(e -> {
            Optional<TipoActividad> optTipo = "TODAS".equals(tipo.getSelectedItem()) ? Optional.empty() : Optional.of(TipoActividad.valueOf((String) tipo.getSelectedItem()));
            Optional<String> optMonitor = monitor.getText().isBlank() ? Optional.empty() : Optional.of(monitor.getText().trim());
            Optional<DayOfWeek> optDia = "TODOS".equals(dia.getSelectedItem()) ? Optional.empty() : Optional.of(DayOfWeek.valueOf((String) dia.getSelectedItem()));
            fillActividadModel(model, service.buscarActividades(optTipo, optMonitor, optDia));
        });

        limpiar.addActionListener(e -> {
            tipo.setSelectedIndex(0);
            monitor.setText("");
            dia.setSelectedIndex(0);
            fillActividadModel(model, service.getActividades());
        });

        fillActividadModel(model, service.getActividades());
        return panel;
    }

    private JPanel buildConsultaSociosPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTable table = new JTable(sociosModel);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel filters = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField nombre = new JTextField(10);
        JTextField correo = new JTextField(12);
        JComboBox<String> vip = new JComboBox<>(new String[]{"TODOS", "SI", "NO"});
        JButton buscar = new JButton("Buscar");
        JButton limpiar = new JButton("Limpiar");

        filters.add(new JLabel("Nombre:"));
        filters.add(nombre);
        filters.add(new JLabel("Correo:"));
        filters.add(correo);
        filters.add(new JLabel("VIP:"));
        filters.add(vip);
        filters.add(buscar);
        filters.add(limpiar);
        panel.add(filters, BorderLayout.NORTH);

        buscar.addActionListener(e -> {
            Optional<String> optNombre = nombre.getText().isBlank() ? Optional.empty() : Optional.of(nombre.getText().trim());
            Optional<String> optCorreo = correo.getText().isBlank() ? Optional.empty() : Optional.of(correo.getText().trim());
            Optional<Boolean> optVip = switch ((String) vip.getSelectedItem()) {
                case "SI" -> Optional.of(true);
                case "NO" -> Optional.of(false);
                default -> Optional.empty();
            };
            fillSociosModel(service.buscarSocios(optNombre, optCorreo, optVip));
        });

        limpiar.addActionListener(e -> {
            nombre.setText("");
            correo.setText("");
            vip.setSelectedIndex(0);
            fillSociosModel(service.buscarSocios(Optional.empty(), Optional.empty(), Optional.empty()));
        });

        fillSociosModel(service.buscarSocios(Optional.empty(), Optional.empty(), Optional.empty()));
        return panel;
    }

    private JPanel buildConsultaReservasPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTable table = new JTable(reservasModel);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField fechaDesde = new JTextField(10);
        JButton filtrar = new JButton("Filtrar");
        JButton todas = new JButton("Todas");
        top.add(new JLabel("Desde (yyyy-MM-dd):"));
        top.add(fechaDesde);
        top.add(filtrar);
        top.add(todas);
        panel.add(top, BorderLayout.NORTH);

        filtrar.addActionListener(e -> {
            try {
                Optional<LocalDate> desde = fechaDesde.getText().isBlank()
                        ? Optional.empty()
                        : Optional.of(LocalDate.parse(fechaDesde.getText().trim()));
                fillReservasModel(service.listarReservasOrdenadas(desde));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha inválido (yyyy-MM-dd)", "JavaFit", JOptionPane.ERROR_MESSAGE);
            }
        });

        todas.addActionListener(e -> {
            fechaDesde.setText("");
            fillReservasModel(service.listarReservasOrdenadas(Optional.empty()));
        });

        fillReservasModel(service.listarReservasOrdenadas(Optional.empty()));
        return panel;
    }

    private void cargarActividades(List<Actividad> actividades) {
        fillActividadModel(actividadesModel, actividades);
    }

    private void fillActividadModel(DefaultTableModel model, List<Actividad> actividades) {
        model.setRowCount(0);
        for (Actividad a : actividades) {
            String horario = a.getHorario().getDia() + " " + a.getHorario().getHoraInicio() + "-" + a.getHorario().getHoraFin();
            model.addRow(new Object[]{a.getId(), a.getTitulo(), a.getTipo(), a.getMonitor(), a.getSala().getNombre(), horario, a.esEspecial() ? "Sí" : "No"});
        }
    }

    private void fillSociosModel(List<Socio> socios) {
        sociosModel.setRowCount(0);
        for (Socio s : socios) {
            sociosModel.addRow(new Object[]{s.getNombre(), s.getCorreo(), s.getTelefono(), s.isVip() ? "Sí" : "No"});
        }
    }

    private void fillReservasModel(List<Reserva> reservas) {
        reservasModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Reserva r : reservas) {
            reservasModel.addRow(new Object[]{formatter.format(r.getFechaReserva()), r.getSocio().getCorreo(), r.getActividad().getTitulo(), r.getPrecioFinal()});
        }
    }

    private Actividad promptActividad(Actividad actual) {
        JTextField titulo = new JTextField(actual != null ? actual.getTitulo() : "");
        JComboBox<TipoActividad> tipo = new JComboBox<>(TipoActividad.values());
        if (actual != null) {
            tipo.setSelectedItem(actual.getTipo());
        }
        JTextField salaNombre = new JTextField(actual != null ? actual.getSala().getNombre() : "Sala");
        JTextField aforo = new JTextField(actual != null ? String.valueOf(actual.getSala().getAforoMaximo()) : "10");
        JComboBox<DayOfWeek> dia = new JComboBox<>(DayOfWeek.values());
        JTextField hInicio = new JTextField(actual != null ? actual.getHorario().getHoraInicio().toString() : "10:00");
        JTextField hFin = new JTextField(actual != null ? actual.getHorario().getHoraFin().toString() : "11:00");
        JTextField monitor = new JTextField(actual != null ? actual.getMonitor() : "");
        JTextField imagen = new JTextField(actual != null ? actual.getImagen() : "images/default.png");
        JCheckBox especial = new JCheckBox("Actividad especial", actual instanceof ActividadEspecial);
        if (actual != null) {
            especial.setEnabled(false);
        }
        JTextField precio = new JTextField(actual instanceof ActividadEspecial ae ? String.valueOf(ae.getPrecio()) : "0");
        JTextField descripcion = new JTextField(actual instanceof ActividadEspecial ae ? ae.getDescripcion() : "");

        if (actual != null) {
            dia.setSelectedItem(actual.getHorario().getDia());
        }

        JPanel panel = new JPanel(new GridLayout(0, 2, 6, 6));
        panel.add(new JLabel("Título:"));
        panel.add(titulo);
        panel.add(new JLabel("Tipo:"));
        panel.add(tipo);
        panel.add(new JLabel("Sala:"));
        panel.add(salaNombre);
        panel.add(new JLabel("Aforo:"));
        panel.add(aforo);
        panel.add(new JLabel("Día:"));
        panel.add(dia);
        panel.add(new JLabel("Hora inicio (HH:mm):"));
        panel.add(hInicio);
        panel.add(new JLabel("Hora fin (HH:mm):"));
        panel.add(hFin);
        panel.add(new JLabel("Monitor:"));
        panel.add(monitor);
        panel.add(new JLabel("Imagen:"));
        panel.add(imagen);
        panel.add(new JLabel("Especial:"));
        panel.add(especial);
        panel.add(new JLabel("Precio (si especial):"));
        panel.add(precio);
        panel.add(new JLabel("Descripción (si especial):"));
        panel.add(descripcion);

        int result = JOptionPane.showConfirmDialog(this, panel,
                actual == null ? "Crear actividad" : "Modificar actividad",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) {
            return null;
        }

        try {
            if (titulo.getText().isBlank() || monitor.getText().isBlank() || salaNombre.getText().isBlank()) {
                throw new IllegalArgumentException("Título, monitor y sala son obligatorios");
            }
            Sala sala = new Sala(salaNombre.getText().trim(), Integer.parseInt(aforo.getText().trim()));
            Horario horario = new Horario(
                    (DayOfWeek) dia.getSelectedItem(),
                    LocalTime.parse(hInicio.getText().trim()),
                    LocalTime.parse(hFin.getText().trim())
            );
            if (especial.isSelected()) {
                return new ActividadEspecial(
                        titulo.getText().trim(),
                        (TipoActividad) tipo.getSelectedItem(),
                        sala,
                        horario,
                        monitor.getText().trim(),
                        imagen.getText().trim(),
                        Double.parseDouble(precio.getText().trim()),
                        descripcion.getText().trim()
                );
            }
            return new Actividad(
                    titulo.getText().trim(),
                    (TipoActividad) tipo.getSelectedItem(),
                    sala,
                    horario,
                    monitor.getText().trim(),
                    imagen.getText().trim()
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo interpretar la actividad: " + ex.getMessage(),
                    "JavaFit",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
