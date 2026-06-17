package com.javafit.gui;

import com.javafit.model.Actividad;
import com.javafit.model.ActividadEspecial;
import com.javafit.model.Reserva;
import com.javafit.model.Socio;
import com.javafit.model.TipoActividad;
import com.javafit.service.JavaFitService;

import javax.swing.BorderFactory;
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
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Ventana principal para el rol socio.
 */
public class SocioFrame extends JFrame {
    private final JavaFitService service;
    private final Socio socio;
    private final LoginFrame loginFrame;

    private final DefaultTableModel actividadesModel = new DefaultTableModel(new String[]{"ID", "Título", "Tipo", "Monitor", "Horario", "Especial", "Precio base"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private final DefaultTableModel reservasModel = new DefaultTableModel(new String[]{"ID", "Fecha", "Actividad", "Precio"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public SocioFrame(JavaFitService service, Socio socio, LoginFrame loginFrame) {
        this.service = service;
        this.socio = socio;
        this.loginFrame = loginFrame;
        setTitle("JavaFit - Panel Socio (" + socio.getNombre() + ")");
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
        tabs.addTab("Buscar actividades", buildBuscarActividadesPanel());
        tabs.addTab("Mis reservas", buildMisReservasPanel());
        tabs.addTab("Mi perfil", buildPerfilPanel());
        add(tabs);
    }

    private JPanel buildBuscarActividadesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTable table = new JTable(actividadesModel);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> tipo = new JComboBox<>(new String[]{"TODAS", "YOGA", "CARDIO", "MUSCULACION", "NATACION"});
        JTextField monitor = new JTextField(10);
        JComboBox<String> dia = new JComboBox<>(new String[]{"TODOS", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"});
        JButton buscar = new JButton("Buscar");
        JButton reservar = new JButton("Reservar seleccionada");

        top.add(new JLabel("Tipo:"));
        top.add(tipo);
        top.add(new JLabel("Monitor:"));
        top.add(monitor);
        top.add(new JLabel("Día:"));
        top.add(dia);
        top.add(buscar);
        top.add(reservar);
        panel.add(top, BorderLayout.NORTH);

        buscar.addActionListener(e -> {
            Optional<TipoActividad> optTipo = "TODAS".equals(tipo.getSelectedItem()) ? Optional.empty() : Optional.of(TipoActividad.valueOf((String) tipo.getSelectedItem()));
            Optional<String> optMonitor = monitor.getText().isBlank() ? Optional.empty() : Optional.of(monitor.getText().trim());
            Optional<DayOfWeek> optDia = "TODOS".equals(dia.getSelectedItem()) ? Optional.empty() : Optional.of(DayOfWeek.valueOf((String) dia.getSelectedItem()));
            fillActividades(service.buscarActividades(optTipo, optMonitor, optDia));
        });

        reservar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona una actividad");
                return;
            }
            String id = (String) actividadesModel.getValueAt(row, 0);
            Optional<Actividad> actividad = service.getActividades().stream().filter(a -> a.getId().equals(id)).findFirst();
            if (actividad.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Actividad no encontrada", "JavaFit", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                service.reservarActividad(socio, actividad.get(), Path.of("recibos"));
                JOptionPane.showMessageDialog(this, "Reserva realizada", "JavaFit", JOptionPane.INFORMATION_MESSAGE);
                fillReservas();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "JavaFit", JOptionPane.ERROR_MESSAGE);
            }
        });

        fillActividades(service.getActividades());
        return panel;
    }

    private JPanel buildMisReservasPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTable table = new JTable(reservasModel);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton cancelar = new JButton("Cancelar seleccionada");
        JButton refrescar = new JButton("Refrescar");
        top.add(cancelar);
        top.add(refrescar);
        panel.add(top, BorderLayout.NORTH);

        cancelar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona una reserva");
                return;
            }
            String reservaId = (String) reservasModel.getValueAt(row, 0);
            boolean ok = service.cancelarReserva(reservaId, socio.getCorreo());
            if (ok) {
                JOptionPane.showMessageDialog(this, "Reserva cancelada", "JavaFit", JOptionPane.INFORMATION_MESSAGE);
                fillReservas();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo cancelar la reserva", "JavaFit", JOptionPane.WARNING_MESSAGE);
            }
        });
        refrescar.addActionListener(e -> fillReservas());

        fillReservas();
        return panel;
    }

    private JPanel buildPerfilPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JTextField nombre = new JTextField(socio.getNombre());
        nombre.setEditable(false);
        JTextField correo = new JTextField(socio.getCorreo());
        correo.setEditable(false);
        JTextField telefono = new JTextField(socio.getTelefono());
        JTextField direccion = new JTextField(socio.getDireccion());
        JTextField tarjeta = new JTextField(socio.getTarjetaCredito());
        JCheckBox vip = new JCheckBox("VIP", socio.isVip());
        JButton guardar = new JButton("Guardar cambios");

        panel.add(new JLabel("Nombre:"));
        panel.add(nombre);
        panel.add(new JLabel("Correo:"));
        panel.add(correo);
        panel.add(new JLabel("Teléfono:"));
        panel.add(telefono);
        panel.add(new JLabel("Dirección:"));
        panel.add(direccion);
        panel.add(new JLabel("Tarjeta:"));
        panel.add(tarjeta);
        panel.add(new JLabel("Tipo:"));
        panel.add(vip);
        panel.add(new JLabel());
        panel.add(guardar);

        guardar.addActionListener(e -> {
            boolean ok = service.actualizarPerfilSocio(
                    socio.getCorreo(),
                    telefono.getText().trim(),
                    direccion.getText().trim(),
                    tarjeta.getText().trim(),
                    vip.isSelected()
            );
            if (ok) {
                JOptionPane.showMessageDialog(this, "Perfil actualizado", "JavaFit", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el perfil", "JavaFit", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private void fillActividades(List<Actividad> actividades) {
        actividadesModel.setRowCount(0);
        for (Actividad a : actividades) {
            String horario = a.getHorario().getDia() + " " + a.getHorario().getHoraInicio() + "-" + a.getHorario().getHoraFin();
            String precio = a instanceof ActividadEspecial esp
                    ? String.format(Locale.ROOT, "%.2f", esp.getPrecio())
                    : "0.00";
            actividadesModel.addRow(new Object[]{a.getId(), a.getTitulo(), a.getTipo(), a.getMonitor(), horario, a.esEspecial() ? "Sí" : "No", precio});
        }
    }

    private void fillReservas() {
        reservasModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        List<Reserva> reservas = service.getReservasPorSocio(socio.getCorreo());
        for (Reserva r : reservas) {
            reservasModel.addRow(new Object[]{r.getId(), formatter.format(r.getFechaReserva()), r.getActividad().getTitulo(), r.getPrecioFinal()});
        }
    }
}
