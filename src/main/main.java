package main;

import javax.swing.SwingUtilities;

public class main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            // new ProductoUI().setVisible(true);  // Activa la interfaz de productos
            // solo se debe descomentar para activar la interfaz a ver.
            // new ClienteUI().setVisible(true); // Activa la interfaz de clientes
            new VentaUI().setVisible(true);   // Activa la interfaz de ventas
        });
    }
}
