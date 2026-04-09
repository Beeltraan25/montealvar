package com.javafit.gui;

import com.javafit.controller.AuthController;
import com.javafit.model.Administrador;
import com.javafit.model.Socio;
import com.javafit.model.Usuario;

import javax.swing.JButton;
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
 * Ventana de inicio de sesión base para JavaFit.
 */
public class LoginFrame extends JFrame {
    private final AuthController authController;

    public LoginFrame(AuthController authController) {
        this.authController = authController;
        setTitle("JavaFit - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 220);
        setLocationRelativeTo(null);

        JLabel brand = new JLabel("JavaFit", JLabel.CENTER);
        add(brand, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        JTextField correoField = new JTextField();
        JPasswordField claveField = new JPasswordField();
        JButton loginButton = new JButton("Entrar");

        formPanel.add(new JLabel("Correo:"));
        formPanel.add(correoField);
        formPanel.add(new JLabel("Clave:"));
        formPanel.add(claveField);
        formPanel.add(new JLabel());
        formPanel.add(loginButton);
        add(formPanel, BorderLayout.CENTER);

        loginButton.addActionListener(e -> {
            String correo = correoField.getText().trim();
            String clave = new String(claveField.getPassword());
            Optional<Usuario> usuario = authController.login(correo, clave);

            if (usuario.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "JavaFit", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String panel = usuario.get() instanceof Administrador ? "Panel Administrador" : "Panel Socio";
            String extra = usuario.get() instanceof Socio s ? "Bienvenido/a " + s.getNombre() : "Bienvenido/a Admin";
            JOptionPane.showMessageDialog(this, "JavaFit\n" + panel + "\n" + extra, "Login correcto", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public static void show(AuthController authController) {
        SwingUtilities.invokeLater(() -> new LoginFrame(authController).setVisible(true));
    }
}
