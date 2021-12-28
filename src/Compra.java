import java.io.Serializable;

public class Compra implements Serializable {
    private int codigo_producto;
    private String nombre_producto;
    private int cantidad;
    private double precio;
    private double subtotal;

    public Compra (int codigo_producto, String nombre_producto, int cantidad, double precio){
        setCodigo_producto(codigo_producto);
        setNombre_producto(nombre_producto);
        setCantidad(cantidad);
        setPrecio(precio);
        setSubtotal(precio, cantidad);
    }

    public int getCodigo_producto() {
        return codigo_producto;
    }

    public void setCodigo_producto(int codigo_producto) {
        this.codigo_producto = codigo_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double precio, int cantidad) {
        this.subtotal= precio*cantidad;
    }
}
