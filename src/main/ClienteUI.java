package main;

import Modelo.Cliente;
import dao.ClienteDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        // Inicializar DAO
        clienteDAO = new ClienteDAO();

        // Crear componentes
        txtNombre = new JTextField(20);
        txtCorreo = new JTextField(20);
        txtTelefono = new JTextField(20);
        areaClientes = new JTextArea(10, 40);
        areaClientes.setEditable(false);

        btnGuardar = new JButton("Guardar Cliente");
        btnListar = new JButton("Listar Clientes");

        // Añadir al Frame
        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Correo:"));
        add(txtCorreo);
        add(new JLabel("Teléfono:"));
        add(txtTelefono);
        add(btnGuardar);
        add(btnListar);
        add(new JScrollPane(areaClientes));

        // Evento: Guardar cliente
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText();
                String correo = txtCorreo.getText();
                String telefono = txtTelefono.getText();

                Cliente cliente = new Cliente();
                cliente.setNombre(nombre);
                cliente.setCorreo(correo);
                cliente.setTelefono(telefono);

                if (clienteDAO.insertar(cliente)) {
                    JOptionPane.showMessageDialog(null, "Cliente guardado exitosamente.");
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al guardar el cliente.");
                }
            }
        });

        // Evento: Listar clientes
        btnListar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                areaClientes.setText("");
                List<Cliente> lista = clienteDAO.obtenerTodos();
                for (Cliente c : lista) {
                    areaClientes.append("ID: " + c.getIdCliente() +
                            " | Nombre: " + c.getNombre() +
                            " | Correo: " + c.getCorreo() +
                            " | Teléfono: " + c.getTelefono() + "\n");
                }
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
