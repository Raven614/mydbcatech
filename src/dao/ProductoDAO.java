package dao;

import Modelo.Producto;
import Util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    private Connection con;

    public ProductoDAO() {
        con = Conexion.getConexion();
    }

    public boolean insertar(Producto producto) {
        String sql = "INSERT INTO productos(nombre, descripcion, precio) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al insertar producto: " + e.getMessage());
            return false;
        }
    }

    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("idProducto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getDouble("precio"));
                lista.add(producto);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }
        return lista;
    }
    public List<Producto> obtenerTodos() {
        List<Producto> lista = new ArrayList<>();
        try (Connection conn = Conexion.getConexion()) {
            String sql = "SELECT * FROM productos";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("idProducto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setStock(rs.getInt("stock"));
                lista.add(producto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }


    public boolean actualizar(Producto producto) {
        String sql = "UPDATE productos SET nombre=?, descripcion=?, precio=? WHERE idProducto=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio());
            ps.setInt(4, producto.getIdProducto());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idProducto) {
        String sql = "DELETE FROM productos WHERE idProducto=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idProducto);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }
    public boolean estaEnUso(int idProducto) {
        String sql = "SELECT COUNT(*) FROM detalle_ventas WHERE idProducto = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProducto);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public Producto obtenerPorId(int id) {
        String sql = "SELECT * FROM productos WHERE idProducto = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("idProducto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getDouble("precio"));
                return producto;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
