import java.io.Serializable;

public class Vendedor implements Serializable {
    public static int contador_vendedores=0;
    private int codigo;
    private String nombre;
    private int caja;
    private int ventas;
    private char genero;
    private String correo;
    private String password;

    //por quitar ya que el inicio será por correo
    public Vendedor(int codigo, String psw) {
        setCodigo(codigo);
        setPassword(psw);
    }

    public Vendedor (String correo, String psw){
        setCorreo(correo);
        setPassword(psw);
    }

    public Vendedor(int codigo, String nombre,String correo, int caja, int ventas, char genero, String psw) {
        setCodigo(codigo);
        setNombre(nombre);
        setCorreo(correo);
        setCaja(caja);
        setVentas(ventas);
        setGenero(genero);
        setPassword(psw);
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCaja() {
        return caja;
    }

    public void setCaja(int caja) {
        this.caja = caja;
    }

    public int getVentas() {
        return ventas;
    }

    public void setVentas(int ventas) {
        this.ventas = ventas;
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
        return "Vendedor{" + "nombre=" + nombre + ", psw=" + password + ", codigo=" + codigo + ", correo=" + correo + ", caja=" + caja + ", ventas=" + ventas + ", genero=" + genero + '}';
    }
}
