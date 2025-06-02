package main;

import Modelo.DetalleVenta;
import Modelo.Producto;
import Modelo.Venta;
import dao.DetalleVentaDAO;
import dao.ProductoDAO;
import dao.VentaDAO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PruebaInsercion {
    public static void main(String[] args) {
        ProductoDAO productoDAO = new ProductoDAO();
        VentaDAO ventaDAO = new VentaDAO();
        DetalleVentaDAO detalleDAO = new DetalleVentaDAO();

        Venta venta = new Venta();
        venta.setIdCliente(1);
        venta.setFecha(new Date(System.currentTimeMillis()));
        venta.setTotal(110000);

        int idVentaGenerado = ventaDAO.insertar(venta);
        if (idVentaGenerado != -1) {
            System.out.println("Venta insertada con ID: " + idVentaGenerado);

            List<DetalleVenta> detalles = new ArrayList<>();
            detalles.add(new DetalleVenta(0, idVentaGenerado, 1, 1, 45000)); // vacuna
            detalles.add(new DetalleVenta(0, idVentaGenerado, 2, 1, 30000)); // desparasitante
            detalles.add(new DetalleVenta(0, idVentaGenerado, 3, 1, 35000)); // croquetas

            for (DetalleVenta d : detalles) {
                if (detalleDAO.insertar(d)) {
                    System.out.println("Detalle insertado: producto " + d.getIdProducto());
                } else {
                    System.out.println("Error insertando detalle: producto " + d.getIdProducto());
                }
            }

        } else {
            System.out.println("Error insertando venta.");
        }

        Producto productoAEliminar = productoDAO.obtenerPorId(1);
        if (productoDAO.estaEnUso(productoAEliminar.getIdProducto())) {
            System.out.println("No se puede eliminar el producto porque está en uso en ventas.");
        } else {
            if (productoDAO.eliminar(productoAEliminar.getIdProducto())) {
                System.out.println("Producto eliminado.");
            } else {
                System.out.println("Error al eliminar producto.");
            }
        }
    }
}
