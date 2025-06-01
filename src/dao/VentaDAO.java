package dao;

import Modelo.Venta;
import Util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VentaDAO {
    public int insertar(Venta venta) {
        String sql = "INSERT INTO ventas (idCliente, fecha, total) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, venta.getIdCliente());
            stmt.setDate(2, venta.getFecha());
            stmt.setDouble(3, venta.getTotal());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // devuelve idVenta generado
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
