package main;

import Modelo.Cliente;
import Modelo.DetalleVenta;
import Modelo.Producto;
import Modelo.Venta;
import dao.ClienteDAO;
import dao.DetalleVentaDAO;
import dao.ProductoDAO;
import dao.VentaDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class VentaUI extends JFrame {
    private final JComboBox<Cliente> comboClientes;
    private final JComboBox<Producto> comboProductos;
    private final JTextField txtCantidad;
    private final JTextArea areaDetalle;
    private final JButton btnAgregar;
    private final JButton btnGuardar;
    private final List<DetalleVenta> detalles = new ArrayList<>();

    public VentaUI() {
        setTitle("Registro de Venta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        comboClientes = new JComboBox<>();
        comboProductos = new JComboBox<>();
        txtCantidad = new JTextField(5);
        areaDetalle = new JTextArea(10, 40);
        areaDetalle.setEditable(false);

        btnAgregar = new JButton("Agregar producto");
        btnGuardar = new JButton("Guardar venta");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(new JLabel("Cliente:"));
        panel.add(comboClientes);
        panel.add(new JLabel("Producto:"));
        panel.add(comboProductos);
        panel.add(new JLabel("Cantidad:"));
        panel.add(txtCantidad);
        panel.add(btnAgregar);
        panel.add(btnGuardar);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(areaDetalle), BorderLayout.CENTER);

        // Cargar datos
        cargarClientes();
        cargarProductos();

        // Eventos
        btnAgregar.addActionListener((ActionEvent e) -> agregarProducto());
        btnGuardar.addActionListener((ActionEvent e) -> guardarVenta());

        setVisible(true);
    }

    private void cargarClientes() {
        comboClientes.removeAllItems();
        ClienteDAO clienteDAO = new ClienteDAO();
        for (Cliente c : clienteDAO.obtenerTodos()) {
            comboClientes.addItem(c);
        }
    }

    private void cargarProductos() {
        ProductoDAO productoDAO = new ProductoDAO();
        List<Producto> productos = productoDAO.obtenerTodos();
        comboProductos.removeAllItems();
        for (Producto producto : productos) {
            comboProductos.addItem(producto);
        }
    }

    private void agregarProducto() {
        Producto producto = (Producto) comboProductos.getSelectedItem();
        int cantidad;

        try {
            cantidad = Integer.parseInt(txtCantidad.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida");
            return;
        }

        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a cero.");
            return;
        }

        if (producto == null || cantidad > producto.getStock()) {
            JOptionPane.showMessageDialog(this, "Stock insuficiente para el producto seleccionado.");
            return;
        }

        DetalleVenta detalle = new DetalleVenta();
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);
        detalle.setSubtotal(producto.getPrecio() * cantidad);
        detalles.add(detalle);

        areaDetalle.append("Producto: " + producto.getNombre() +
                ", Cantidad: " + cantidad +
                ", Subtotal: $" + detalle.getSubtotal() + "\n");

        // Descontar el stock en memoria
        producto.setStock(producto.getStock() - cantidad);
        comboProductos.repaint(); // Refrescar el combo
        txtCantidad.setText("");
    }

    private void guardarVenta() {
        Cliente cliente = (Cliente) comboClientes.getSelectedItem();

        if (cliente == null || detalles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente y agregar al menos un producto.");
            return;
        }

        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setFecha(new java.sql.Date(System.currentTimeMillis()));
        venta.setTotal(calcularTotal());

        VentaDAO ventaDAO = new VentaDAO();
        int idVenta = ventaDAO.insertar(venta);

        DetalleVentaDAO detalleDAO = new DetalleVentaDAO();
        ProductoDAO productoDAO = new ProductoDAO();

        for (DetalleVenta d : detalles) {
            d.setVenta(venta);
            detalleDAO.insertar(d);

            // Actualizar el stock en la base de datos
            Producto p = d.getProducto();
            productoDAO.actualizar(p);
        }

        JOptionPane.showMessageDialog(this, "Venta guardada con éxito.");
        detalles.clear();
        areaDetalle.setText("");
        txtCantidad.setText("");
        cargarProductos(); // Recargar productos con stock actualizado
    }

    private double calcularTotal() {
        double total = 0;
        for (DetalleVenta d : detalles) {
            total += d.getSubtotal();
        }
        return total;
    }
}

