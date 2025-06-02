package main;

import Modelo.Cliente;
import dao.ClienteDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ClienteUI extends JFrame {
    private JTextField txtNombre, txtCorreo, txtTelefono;
    private JTextArea areaClientes;
    private JButton btnGuardar, btnListar;
    private ClienteDAO clienteDAO;

    public ClienteUI() {
        setTitle("Gestión de Clientes - Veterinaria");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        clienteDAO = new ClienteDAO();

        txtNombre = new JTextField(20);
        txtCorreo = new JTextField(20);
        txtTelefono = new JTextField(20);
        areaClientes = new JTextArea(10, 40);
        areaClientes.setEditable(false);

        btnGuardar = new JButton("Guardar Cliente");
        btnListar = new JButton("Listar Clientes");

        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Correo:"));
        add(txtCorreo);
        add(new JLabel("Teléfono:"));
        add(txtTelefono);
        add(btnGuardar);
        add(btnListar);
        add(new JScrollPane(areaClientes));

        btnGuardar.addActionListener((ActionEvent e) -> {
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            String telefono = txtTelefono.getText().trim();

            if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            Cliente cliente =   new Cliente(nombre, correo, telefono);

            if (clienteDAO.insertar(cliente)) {
                JOptionPane.showMessageDialog(this, "Cliente guardado exitosamente.");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el cliente.");
            }
        });

        btnListar.addActionListener((ActionEvent e) -> {
            areaClientes.setText("");
            List<Cliente> lista = clienteDAO.obtenerTodos();
            for (Cliente c : lista) {
                areaClientes.append("ID: " + c.getIdCliente() +
                        " | Nombre: " + c.getNombre() +
                        " | Correo: " + c.getCorreo() +
                        " | Teléfono: " + c.getTelefono() + "\n");
            }
        });
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
    }

    public static void main(String[] args) {
        new ClienteUI().setVisible(true);
    }
}
