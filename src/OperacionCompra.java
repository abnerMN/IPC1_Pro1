public class OperacionCompra {
    public int contador_factura(Factura[] facturas) {
        int contador = 0;
        for (int i = 0; i < facturas.length; i++) {
            if (facturas[i] != null) {
                contador++;
            } else {
                break;
            }
        }
        return contador;
    }

    public void borrar_compras(Compra[] compras_realizadas) {
        for (int i = 0; i < compras_realizadas.length; i++) {
            compras_realizadas[i] = null;
        }
    }

    public int contador_productos(Compra[] compras_realizadas) {
        int contador_compra = 0;
        for (int i = 0; i < compras_realizadas.length; i++) {
            if (compras_realizadas[i] != null) {
                contador_compra++;
            } else {
                break;
            }
        }
        return contador_compra;
    }

    public double total_compra(Compra[] compras_realizadas) {
        double total = 0;
        for (int i = 0; i < 10; i++) {
            if (compras_realizadas[i] != null) {
                total += compras_realizadas[i].getSubtotal();
            } else {
                break;
            }
        }
        return total;
    }

    public String[] cliente_opciones() {
        int contador = 0;
        for (int i = 0; i < Inicio.clientes.length; i++) {
            if (Inicio.clientes[i] != null) {
                contador++;
            } else {
                break;
            }
        }

        String[] var = new String[contador];
        int j = 0;
        for (int i = 0; i < Inicio.clientes.length; i++) {
            if (Inicio.clientes[i] != null) {
                var[j] = Inicio.clientes[i].getNombre();
                j++;
            } else {
                break;
            }
        }
        return var;
    }

    public void temp_impresio(Cliente[] var) {
        for (int i = 0; i < var.length; i++) {
            System.out.println(var[i]);
        }
    }

    public int buscarProducto(String codigo) {
        int posicion = -5;
        int respuesta = Integer.parseInt(codigo);
        for (int i = 0; i < Inicio.productos.length; i++) {
            if (Inicio.productos[i] != null) {
                if (Inicio.productos[i].getCodigo() == respuesta) {
                    posicion = i;
                    break;
                }
            } else {
                break;
            }
        }
        return posicion;
    }
}
