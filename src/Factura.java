import java.io.Serializable;

public class Factura implements Serializable {
    private int caja;
    public static int no_factura=1001;
    private int numeroFac;
    private String nit;
    private String nombre_cliente;
    private Compra [] compras;
    private int total;
    private String fecha;

    public Factura (int caja, String nit, String nombre_cliente, Compra [] compras, String fecha){
        setCaja(caja);
        setNit(nit);
        setNombre_cliente(nombre_cliente);
        setFecha(fecha);
        this.compras=compras;
        this.numeroFac=no_factura;
        obtenerTotal();
        no_factura++;
    }

    public int getNumeroFac() {
        return numeroFac;
    }

    public void setNumeroFac(int numeroFac) {
        this.numeroFac = numeroFac;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }



    public int getCaja() {
        return caja;
    }

    public void setCaja(int caja) {
        this.caja = caja;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public Compra[] getCompras() {
        return compras;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    public void obtenerTotal (){
        int var=0;
        for (int i = 0; i < compras.length; i++) {
            if (compras[i]!=null) {
                var+=compras[i].getSubtotal();
            }else{
                break;
            }
        }
        this.total=var;
    }

    public void impresionCompras (){
        for (int i = 0; i < compras.length; i++) {
            if (compras[i]!=null) {
                System.out.println(compras[i]);
            }else{
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Factura{" + "caja=" + caja + ", nit=" + nit + ", nombre_cliente=" + nombre_cliente  + ", total=" + total + '}';
    }
}
