import java.io.Serializable;

public class Producto implements Serializable {
    public static int contador_productos=0;
    private int codigo;
    private String nombre;
    private String descripcion;
    private int cantidad;
    private double precio;

    public Producto (int codigo, String nombre, String descripcion, int cantidad, double precio){
        setCodigo(codigo);
        setNombre(nombre);
        setDescripcion(descripcion);
        setCantidad(cantidad);
        setPrecio(precio);
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    @Override
    public String toString() {
        return "Productos{" + "codigo=" + codigo + ", nombre=" + nombre + ", descripcion=" + descripcion + ", cantidad=" + cantidad + ", precio=" + precio + '}';
    }

}
