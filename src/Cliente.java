import java.io.Serializable;

public class Cliente implements Serializable {
    public static int contador_clientes = 0;
    private int codigo;
    private String nombre;
    private String nit;
    private String correo;
    private char genero;

    public Cliente(int codigo, String nombre, String nit, String correo, char genero) {
        setCodigo(codigo);
        setNombre(nombre);
        setNit(nit);
        setCorreo(correo);
        setGenero(genero);
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

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public char getGenero() {
        return genero;
    }

    public void setGenero(char genero) {
        String var = String.valueOf(genero);
        var = var.toUpperCase();
        char respuesta = var.charAt(0);
        this.genero = respuesta;
    }

    @Override
    public String toString() {
        return "Cliente{" + "codigo=" + codigo + ", nombre=" + nombre + ", nit=" + nit + ", correo=" + correo + ", genero=" + genero + '}';
    }

}
