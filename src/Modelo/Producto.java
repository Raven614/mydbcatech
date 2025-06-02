package Modelo;

public class Producto {
    private int idProducto;
    private String nombre;
    private double precio;
    private int stock;
    private String descripcion;

    // Constructor vacío
    public Producto() {}

      public Producto(String nombre, double precio, int stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() { // <--- Agregado
        return descripcion;
    }

    public void setDescripcion(String descripcion) { // <--- Agregado
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return nombre + " ($" + precio + ") - Stock: " + stock;
    }

}
