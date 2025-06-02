package main;

import Modelo.Producto;
import dao.ProductoDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ProductoUI extends JFrame {
    private JTextField txtNombre, txtPrecio, txtStock;
    private JButton btnGuardar, btnListar;
    private JTextArea areaProductos;
    private ProductoDAO productoDAO;

    public ProductoUI() {
        setTitle("Gestión de Productos");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        productoDAO = new ProductoDAO();

        JLabel lbl1 = new JLabel("Nombre:");
        lbl1.setBounds(20, 20, 80, 25);
        add(lbl1);

        txtNombre = new JTextField();
        txtNombre.setBounds(100, 20, 200, 25);
        add(txtNombre);

        JLabel lbl2 = new JLabel("Precio:");
        lbl2.setBounds(20, 60, 80, 25);
        add(lbl2);

        txtPrecio = new JTextField();
        txtPrecio.setBounds(100, 60, 200, 25);
        add(txtPrecio);

        JLabel lbl3 = new JLabel("Stock:");
        lbl3.setBounds(20, 100, 80, 25);
        add(lbl3);

        txtStock = new JTextField();
        txtStock.setBounds(100, 100, 200, 25);
        add(txtStock);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(100, 140, 100, 30);
        add(btnGuardar);

        btnListar = new JButton("Listar");
        btnListar.setBounds(210, 140, 100, 30);
        add(btnListar);

        areaProductos = new JTextArea();
        JScrollPane scroll = new JScrollPane(areaProductos);
        scroll.setBounds(20, 190, 340, 150);
        add(scroll);

        btnGuardar.addActionListener((ActionEvent e) -> {
            try {
                String nombre = txtNombre.getText().trim();
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                int stock = Integer.parseInt(txtStock.getText().trim());

                if (nombre.isEmpty() || precio < 0 || stock < 0) {
                    JOptionPane.showMessageDialog(this, "Datos inválidos.");
                    return;
                }

                Producto producto = new Producto(nombre, precio, stock);
                if (productoDAO.insertar(producto)) {
                    JOptionPane.showMessageDialog(this, "Producto guardado correctamente.");
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al guardar el producto.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnListar.addActionListener((ActionEvent e) -> {
            areaProductos.setText("");
            List<Producto> lista = productoDAO.obtenerTodos();
            for (Producto p : lista) {
                areaProductos.append("ID: " + p.getIdProducto() +
                        " | Nombre: " + p.getNombre() +
                        " | Precio: $" + p.getPrecio() +
                        " | Stock: " + p.getStock() + "\n");
            }
        });
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
    }

    public static void main(String[] args) {
        new ProductoUI().setVisible(true);
    }
}
