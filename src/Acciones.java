import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Acciones {
    //buscar usuario
    public boolean buscarUsuario(Vendedor user, Vendedor[] vendedores) {
        boolean bandera = false;
        for (int i = 0; i < vendedores.length; i++) {
            if (vendedores[i] != null) {
                if (vendedores[i].getCorreo().equals(user.getCorreo())) {
                    bandera = true;
                    break;
                }
            } else {
                break;
            }
        }
        return bandera;
    }

    //validacion contraseña
    public boolean validacionContra(Vendedor user, Vendedor[] vendedores) {
        boolean bandera = false;
        String correo = user.getCorreo();
        String psw = user.getPassword();
        for (int i = 0; i < vendedores.length; i++) {
            if (vendedores[i] != null) {
                if (vendedores[i].getCorreo().equals(correo)) {
                    if (vendedores[i].getPassword().equals(psw)) {
                        bandera = true;
                    } else {
                        break;
                    }
                }
            } else {
                break;
            }
        }
        return bandera;
    }

    //impresion del arreglo
    public void impresion(Object[] valores) {
        for (Object valore : valores) {
            if (valore != null) {
                System.out.println(valore);
            } else {
                break;
            }
        }
    }

    //impresion null
    public void impresionNull(Object[] var) {
        for (Object var1 : var) {
            System.out.println(var1);
        }
    }

    //productos
    //carga masiva productos
    public void cMasiva_productos(Producto[] productos) {
        try {
            JsonArray datos = leerArchivoJson();
            cdata_productos(datos, productos);
        } catch (Exception e) {
            System.out.println("No se agregaron los datos");
        }
    }

    //creacion y asignacion de los objetos al arreglo de objetos (productos)
    public void cdata_productos(JsonArray arreglo, Producto[] productos) {
        for (int i = 0; i < arreglo.size(); i++) {
            // obtener el objeto del arreglo JSON
            JsonObject objeto = arreglo.get(i).getAsJsonObject();
            int codigo = objeto.get("codigo").getAsInt();
            String nombre = objeto.get("nombre").getAsString();
            String descripcion = objeto.get("descripcion").getAsString();
            int cantidad = objeto.get("cantidad").getAsInt();
            double precio = objeto.get("precio").getAsDouble();
            int valor = buscar_producto(codigo, productos);
            if (valor < 0) {
                if (Producto.contador_productos <= productos.length) {
                    Producto var = new Producto(codigo, nombre, descripcion, cantidad, precio);
                    productos[Producto.contador_productos] = var;
                    Producto.contador_productos++;
                } else {
                    JOptionPane.showMessageDialog(null, "Se ha alzanzado el limite de datos para guardar");
                    break;
                }
            }
        }
        serializar(productos, "productos.dat");
    }

    //metodo para buscar producto
    public int buscar_producto(int codigo, Producto[] var) {
        int locacion = -3;
        for (int i = 0; i < var.length; i++) {
            if (var[i] != null) {
                if (var[i].getCodigo() == codigo) {
                    locacion = i;
                    break;
                }
            } else {
                break;
            }
        }
        return locacion;
    }

    //metodo para agregar un producto
    public void agregar_producto(int codigo, String nombre, String descripcion, int cantidad, double precio, Producto[] productos) {
        int var = buscar_producto(codigo, productos);
        if (var < 0) {
            if (Producto.contador_productos <= productos.length) {
                Producto nuevo = new Producto(codigo, nombre, descripcion, cantidad, precio);
                productos[Producto.contador_productos] = nuevo;
                serializar(productos, "productos.dat");
                Producto.contador_productos++;
            } else {
                JOptionPane.showMessageDialog(null, "Se ha alzanzado el limite de datos para guardar");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Codigo de Producto Registrado");
        }
    }

    //metodo para eliminar un producto
    public void eliminar_producto(int codigo, Producto[] productos) {
        int var = buscar_producto(codigo, productos);
        if (var >= 0) {
            productos[var] = null;
            Ordenamiento(productos);
            serializar(productos, "productos.dat");
            Producto.contador_productos--;
        } else {
            JOptionPane.showMessageDialog(null, "Producto No Registrado");
        }
    }

    //metodo para actualiar los datos de un producto
    public void actualizar_producto(int codigo, String nombre, String descripcion, int cantidad, double precio, Producto[] productos) {
        int var = buscar_producto(codigo, productos);
        if (var >= 0) {
            productos[var].setNombre(nombre);
            productos[var].setDescripcion(descripcion);
            productos[var].setCantidad(cantidad);
            productos[var].setPrecio(precio);
            serializar(productos, "productos.dat");
        } else {
            JOptionPane.showMessageDialog(null, "Producto no Registrado");
        }
    }

    //clientes
    //carga masiva clientes
    public void cMasiva_clientes(Cliente[] clientes) {
        try {
            JsonArray datos = leerArchivoJson();
            cdata_clientes(datos, clientes);
        } catch (Exception e) {
            System.out.println("No se agregaron los datos");
        }
    }

    //creacion y asignacion de los objetos al arreglo de clientes
    public void cdata_clientes(JsonArray arreglo, Cliente[] clientes) {
        for (int i = 0; i < arreglo.size(); i++) {
            // obtener el objeto del arreglo JSON
            JsonObject objeto = arreglo.get(i).getAsJsonObject();
            int codigo = objeto.get("codigo").getAsInt();
            String nombre = objeto.get("nombre").getAsString();
            String nit = objeto.get("nit").getAsString();
            String correo = objeto.get("correo").getAsString();
            char genero = objeto.get("genero").getAsCharacter();
            int valor = buscar_cliente(codigo, clientes);
            if (valor < 0) {
                if (Cliente.contador_clientes <= clientes.length) {
                    Cliente var = new Cliente(codigo, nombre, nit, correo, genero);
                    clientes[Cliente.contador_clientes] = var;
                    Cliente.contador_clientes++;
                } else {
                    JOptionPane.showMessageDialog(null, "Se ha alzanzado el limite de datos para guardar");
                    break;
                }

            }
        }
        serializar(clientes, "clientes.dat");
    }

    //metodo para a cliente por el codigo
    public int buscar_cliente(int codigo, Cliente[] var) {
        int locacion = -3;
        for (int i = 0; i < var.length; i++) {
            if (var[i] != null) {
                if (var[i].getCodigo() == codigo) {
                    locacion = i;
                    break;
                }
            } else {
                break;
            }
        }
        return locacion;
    }

    //metodo para agregar un cliente
    public void agregar_cliente(int codigo, String nombre, String nit, String correo, char genero, Cliente[] clientes) {
        int var = buscar_cliente(codigo, clientes);
        if (var < 0) {
            if (Cliente.contador_clientes <= clientes.length) {
                Cliente nuevo = new Cliente(codigo, nombre, nit, correo, genero);
                clientes[Cliente.contador_clientes] = nuevo;
                serializar(clientes, "clientes.dat");
                Cliente.contador_clientes++;
            } else {
                JOptionPane.showMessageDialog(null, "Se ha alzanzado el limite de datos para guardar");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Codigo de Cliente Registrado");
        }
    }

    //metodo para eliminar un cliente
    public void eliminar_cliente(int codigo, Cliente[] clientes) {
        int var = buscar_cliente(codigo, clientes);
        if (var >= 0) {
            clientes[var] = null;
            Ordenamiento(clientes);
            serializar(clientes, "clientes.dat");
            Cliente.contador_clientes--;
        } else {
            JOptionPane.showMessageDialog(null, "Cliente No Registrado");
        }
    }

    //metodo para actualiar los datos de un Cliente
    public void actualizar_cliente(int codigo, String nombre, String nit, String correo, char genero, Cliente[] clientes) {
        int var = buscar_cliente(codigo, clientes);
        if (var >= 0) {
            clientes[var].setNombre(nombre);
            clientes[var].setNit(nit);
            clientes[var].setCorreo(correo);
            clientes[var].setGenero(genero);
            serializar(clientes, "clientes.dat");
        } else {
            JOptionPane.showMessageDialog(null, "Cliente no Registrado");
        }
    }

    //sucursales
    //carga masiva sucursales
    public void cMasiva_sucursales(Sucursal[] sucursales) {
        try {
            JsonArray datos = leerArchivoJson();
            cdata_sucursales(datos, sucursales);
        } catch (Exception e) {
            System.out.println("No se agregaron los datos");
        }
    }

    //creacion y asignacion de los objetos al arreglo de sucursales
    public void cdata_sucursales(JsonArray arreglo, Sucursal[] sucursales) {
        for (int i = 0; i < arreglo.size(); i++) {
            // obtener el objeto del arreglo JSON
            JsonObject objeto = arreglo.get(i).getAsJsonObject();
            int codigo = objeto.get("codigo").getAsInt();
            String nombre = objeto.get("nombre").getAsString();
            String direccion = objeto.get("direccion").getAsString();
            String correo = objeto.get("correo").getAsString();
            int telefono = objeto.get("telefono").getAsInt();
            int valor = buscar_sucursal(codigo, sucursales);
            if (valor < 0) {
                if (Sucursal.contador_sucursales <= sucursales.length) {
                    Sucursal var = new Sucursal(codigo, nombre, direccion, correo, telefono);
                    sucursales[Sucursal.contador_sucursales] = var;
                    Sucursal.contador_sucursales++;
                } else {
                    JOptionPane.showMessageDialog(null, "Se ha alzanzado el limite de datos para guardar");
                    break;
                }

            }
        }
        serializar(sucursales, "sucursales.dat");
    }

    //metodo para buscra sucursal por el codigo
    public int buscar_sucursal(int codigo, Sucursal[] var) {
        int locacion = -3;
        for (int i = 0; i < var.length; i++) {
            if (var[i] != null) {
                if (var[i].getCodigo() == codigo) {
                    locacion = i;
                    break;
                }
            } else {
                break;
            }
        }
        return locacion;
    }

    //metodo para agregar un sucursal
    public void agregar_sucursal(int codigo, String nombre, String direccion, String correo, int telefono, Sucursal[] sucursales) {
        int var = buscar_sucursal(codigo, sucursales);
        if (var < 0) {
            if (Sucursal.contador_sucursales <= sucursales.length) {
                Sucursal nuevo = new Sucursal(codigo, nombre, direccion, correo, telefono);
                sucursales[Sucursal.contador_sucursales] = nuevo;
                serializar(sucursales, "sucursales.dat");
                Sucursal.contador_sucursales++;
            } else {
                JOptionPane.showMessageDialog(null, "Se ha alzanzado el limite de datos para guardar");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Codigo de Sucursal Registrado");
        }
    }

    //metodo para eliminar un sucursal
    public void eliminar_sucursal(int codigo, Sucursal[] sucursales) {
        int var = buscar_sucursal(codigo, sucursales);
        if (var >= 0) {
            sucursales[var] = null;
            Ordenamiento(sucursales);
            serializar(sucursales, "sucursales.dat");
            Sucursal.contador_sucursales--;
        } else {
            JOptionPane.showMessageDialog(null, "Sucursal No Registrado");
        }
    }

    //metodo para actualiar los datos de una Sucursal
    public void actualizar_sucursal(int codigo, String nombre, String direccion, String correo, int telefono, Sucursal[] sucursales) {
        int var = buscar_sucursal(codigo, sucursales);
        if (var >= 0) {
            sucursales[var].setNombre(nombre);
            sucursales[var].setDireccion(direccion);
            sucursales[var].setCorreo(correo);
            sucursales[var].setTelefono(telefono);
            serializar(sucursales, "sucursales.dat");
        } else {
            JOptionPane.showMessageDialog(null, "Sucursal no Registrado");
        }
    }

    //vendedores
    //carga masiva vendedores
    public void cMasiva_vendedores(Vendedor[] vendedor) {
        try {
            JsonArray datos = leerArchivoJson();
            cdata_vendedores(datos, vendedor);
        } catch (Exception e) {
            System.out.println("No se agregaron los datos");
        }
    }

    //creacion y asignacion de los objetos al arreglo de vendedores
    public void cdata_vendedores(JsonArray arreglo, Vendedor[] vendedores) {
        for (int i = 0; i < arreglo.size(); i++) {
            // obtener el objeto del arreglo JSON
            JsonObject objeto = arreglo.get(i).getAsJsonObject();
            int codigo = objeto.get("codigo").getAsInt();
            String nombre = objeto.get("nombre").getAsString();
            String correo = objeto.get("correo").getAsString();
            int caja = objeto.get("caja").getAsInt();
            int ventas = objeto.get("ventas").getAsInt();
            char genero = objeto.get("genero").getAsCharacter();
            String psw = objeto.get("password").getAsString();
            int valor = buscar_vendedor(codigo, vendedores);
            if (valor < 0) {
                if (Vendedor.contador_vendedores <= vendedores.length) {
                    int pos = buscar_vendedorCorreo(correo, vendedores);
                    if (pos < 0) {
                        Vendedor var = new Vendedor(codigo, nombre, correo, caja, ventas, genero, psw);
                        vendedores[Vendedor.contador_vendedores] = var;
                        Vendedor.contador_vendedores++;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Se ha alzanzado el limite de datos para guardar");
                    break;
                }

            }
        }
        serializar(vendedores, "vendedores.dat");
    }

    //metodo para buscar vendedor por el codigo
    public int buscar_vendedor(int codigo, Vendedor[] var) {
        int locacion = -3;
        for (int i = 0; i < var.length; i++) {
            if (var[i] != null) {
                if (var[i].getCodigo() == codigo) {
                    locacion = i;
                    break;
                }
            } else {
                break;
            }
        }
        return locacion;
    }

    //metodo para agregar un vendedor
    public void agregar_vendedor(int codigo, String nombre, String correo, int caja, int ventas, char genero, String psw, Vendedor[] vendedores) {
        int var = buscar_vendedor(codigo, vendedores);
        if (var < 0) {
            if (Vendedor.contador_vendedores <= vendedores.length) {
                Vendedor nuevo = new Vendedor(codigo, nombre, correo, caja, ventas, genero, psw);
                vendedores[Vendedor.contador_vendedores] = nuevo;
                serializar(vendedores, "vendedores.dat");
                Vendedor.contador_vendedores++;
            } else {
                JOptionPane.showMessageDialog(null, "Se ha alzanzado el limite de datos para guardar");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Codigo de Vendedor Registrado");
        }
    }

    //metodo para eliminar un vendedor
    public void eliminar_vendedor(int codigo, Vendedor[] vendedores) {
        int var = buscar_vendedor(codigo, vendedores);
        if (var >= 0) {
            vendedores[var] = null;
            Ordenamiento(vendedores);
            serializar(vendedores, "vendedores.dat");
            Vendedor.contador_vendedores--;
        } else {
            JOptionPane.showMessageDialog(null, "Vendedor No Registrado");
        }
    }

    //metodo para actualiar los datos de un vendedor
    public void actualizar_vendedor(int codigo, String nombre, String correo, int caja, int ventas, char genero, String psw, Vendedor[] vendedores) {
        int var = buscar_vendedor(codigo, vendedores);
        if (var >= 0) {
            vendedores[var].setNombre(nombre);
            vendedores[var].setCorreo(correo);
            vendedores[var].setCaja(caja);
            vendedores[var].setVentas(ventas);
            vendedores[var].setGenero(genero);
            vendedores[var].setPassword(psw);
            serializar(vendedores, "vendedores.dat");
        } else {
            JOptionPane.showMessageDialog(null, "Vendedor no Registrado");
        }
    }

    public int buscar_vendedorCorreo(String correo, Vendedor[] var) {
        int locacion = -3;
        for (int i = 0; i < var.length; i++) {
            if (var[i] != null) {
                if (var[i].getCorreo().equals(correo)) {
                    locacion = i;
                    break;
                }
            } else {
                break;
            }
        }
        return locacion;
    }

    //static
    //metodo para serializar los datos
    public static void serializar(Object[] arreglo, String ruta) {
        try {
            ObjectOutputStream escribirArchivo = new ObjectOutputStream(new FileOutputStream(ruta));
            escribirArchivo.writeObject(arreglo);
            escribirArchivo.close();
        } catch (Exception e) {
        }
    }

    //metodo para leer los datos serializados
    public static Object[] leerArregloSerializado(String ruta) {
        try {
            ObjectInputStream leerArchivo = new ObjectInputStream(new FileInputStream(ruta));
            Object arregloCapturado[] = (Object[]) leerArchivo.readObject();
            leerArchivo.close();
            return arregloCapturado;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //metodo para ordenar los arreglos
    public static void Ordenamiento(Object[] var) {
        Object aux = null;
        for (int i = 0; i < var.length - 1; i++) {
            for (int j = 0; j < var.length - i - 1; j++) {
                if (var[j + 1] != null && var[j] == null) {
                    aux = var[j + 1];
                    var[j + 1] = var[j];
                    var[j] = aux;
                }
            }
        }
    }

    public static JsonArray leerArchivoJson() {
        String texto = "";
        JFileChooser ventanaSeleccion = new JFileChooser();
        try {
            int op = ventanaSeleccion.showOpenDialog(ventanaSeleccion);
            if (op == JFileChooser.APPROVE_OPTION) {
                BufferedReader br = null;
                try {
                    File archivo = ventanaSeleccion.getSelectedFile();
                    br = new BufferedReader(new FileReader(archivo));
                    String linea = br.readLine();

                    while (linea != null) {
                        texto += linea;
                        linea = br.readLine();
                    }
                } catch (Exception e) {
                    //en caso de error se muestra el mensaje
                    System.out.println("Ocurrió un error al leer el archivo");
                } finally {
                    // si el bufer es nulo es por que se abrió el archivo
                    if (br != null) {
                        //se cierra el buffer
                        br.close();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //pasando los datos leidos a un arreglo de objetos tipo json
        JsonParser parser = new JsonParser();
        JsonArray arregloJon = parser.parse(texto).getAsJsonArray();
        return arregloJon;
    }

    //metodo para tener los contadores actuales
    public static int contador_Actual(Object[] var) {
        int contador = 0;
        for (int i = 0; i < var.length; i++) {
            if (var[i] != null) {
                contador++;
            } else {
                break;
            }
        }
        return contador;
    }

    public static void borrarTodo (Object [] arreglo, String ruta, int inicio){
        for (int i = inicio; i < arreglo.length; i++) {
            arreglo[i]=null;
        }
        serializar(arreglo, ruta);
    }
}
