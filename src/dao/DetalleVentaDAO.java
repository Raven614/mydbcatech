package dao;

import Modelo.DetalleVenta;
import Util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DetalleVentaDAO {
    public boolean insertar(DetalleVenta detalle) {
        String sql = "INSERT INTO detalle_ventas (idVenta, idProducto, cantidad, subtotal) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, detalle.getIdVenta());
            stmt.setInt(2, detalle.getIdProducto());
            stmt.setInt(3, detalle.getCantidad());
            stmt.setDouble(4, detalle.getSubtotal());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error al insertar detalle: " + e.getMessage());
            return false;
        }
    }
}
