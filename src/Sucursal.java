import java.io.Serializable;

public class Sucursal implements Serializable {
    public static int contador_sucursales=0;
    private int codigo;
    private String nombre;
    private String direccion;
    private String correo;
    private int telefono;

    public Sucursal (int codigo, String nombre, String direccion, String correo, int telefono){
        setCodigo(codigo);
        setNombre(nombre);
        setDireccion(direccion);
        setCorreo(correo);
        setTelefono(telefono);
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Sucursales{" + "codigo=" + codigo + ", nombre=" + nombre + ", direccion=" + direccion + ", correo=" + correo + ", telefono=" + telefono + '}';
    }
}
