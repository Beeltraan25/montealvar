package com.javafit.gui;

import com.javafit.controller.AuthController;
import com.javafit.model.Administrador;
import com.javafit.model.Socio;
import com.javafit.model.Usuario;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Optional;

/**
 * Ventana de inicio de sesión para JavaFit.
 */
public class LoginFrame extends JFrame {
    private final AuthController authController;

    public LoginFrame(AuthController authController) {
        this.authController = authController;
        setTitle("JavaFit - Acceso");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 280);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel brand = new JLabel("JavaFit", JLabel.CENTER);
        brand.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        add(brand, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JComboBox<String> rolCombo = new JComboBox<>(new String[]{"Administrador", "Socio"});
        JTextField correoField = new JTextField();
        JPasswordField claveField = new JPasswordField();
        JButton loginButton = new JButton("Entrar");
        JButton registroButton = new JButton("Registrar socio");

        formPanel.add(new JLabel("Acceso como:"));
        formPanel.add(rolCombo);
        formPanel.add(new JLabel("Correo:"));
        formPanel.add(correoField);
        formPanel.add(new JLabel("Clave:"));
        formPanel.add(claveField);
        formPanel.add(new JLabel());
        formPanel.add(new JLabel());
        formPanel.add(loginButton);
        formPanel.add(registroButton);
        add(formPanel, BorderLayout.CENTER);

        rolCombo.addActionListener(e -> registroButton.setEnabled("Socio".equals(rolCombo.getSelectedItem())));
        registroButton.setEnabled(false);

        loginButton.addActionListener(e -> {
            String correo = correoField.getText().trim();
            String clave = new String(claveField.getPassword());
            String rol = (String) rolCombo.getSelectedItem();

            if (correo.isBlank() || clave.isBlank()) {
                JOptionPane.showMessageDialog(this, "Debes introducir correo y clave", "JavaFit", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Optional<Usuario> usuario = authController.login(correo, clave);
            if (usuario.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "JavaFit", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario u = usuario.get();
            if ("Administrador".equals(rol) && !(u instanceof Administrador)) {
                JOptionPane.showMessageDialog(this, "Ese usuario no tiene rol de administrador", "JavaFit", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if ("Socio".equals(rol) && !(u instanceof Socio)) {
                JOptionPane.showMessageDialog(this, "Ese usuario no tiene rol de socio", "JavaFit", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (u instanceof Administrador) {
                new AdminFrame(authController.getService(), this).setVisible(true);
            } else {
                new SocioFrame(authController.getService(), (Socio) u, this).setVisible(true);
            }
            setVisible(false);
        });

        registroButton.addActionListener(e -> abrirRegistroSocio());
    }

    private void abrirRegistroSocio() {
        JTextField nombre = new JTextField();
        JTextField correo = new JTextField();
        JPasswordField clave = new JPasswordField();
        JTextField telefono = new JTextField();
        JTextField direccion = new JTextField();
        JTextField tarjeta = new JTextField();
        JCheckBox vip = new JCheckBox("VIP");

        JPanel panel = new JPanel(new GridLayout(0, 2, 6, 6));
        panel.add(new JLabel("Nombre:"));
        panel.add(nombre);
        panel.add(new JLabel("Correo:"));
        panel.add(correo);
        panel.add(new JLabel("Clave:"));
        panel.add(clave);
        panel.add(new JLabel("Teléfono:"));
        panel.add(telefono);
        panel.add(new JLabel("Dirección:"));
        panel.add(direccion);
        panel.add(new JLabel("Tarjeta:"));
        panel.add(tarjeta);
        panel.add(new JLabel("Tipo:"));
        panel.add(vip);

        int result = JOptionPane.showConfirmDialog(this, panel, "Registro de socio", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        if (nombre.getText().isBlank() || correo.getText().isBlank() || clave.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Nombre, correo y clave son obligatorios", "JavaFit", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Socio nuevo = new Socio(
                nombre.getText().trim(),
                correo.getText().trim(),
                new String(clave.getPassword()),
                telefono.getText().trim(),
                direccion.getText().trim(),
                tarjeta.getText().trim(),
                vip.isSelected()
        );

        boolean ok = authController.registrarSocio(nuevo);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Socio registrado correctamente", "JavaFit", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar (correo ya existente)", "JavaFit", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void show(AuthController authController) {
        SwingUtilities.invokeLater(() -> new LoginFrame(authController).setVisible(true));
    }
}
