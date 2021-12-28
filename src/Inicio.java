import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
//import static Acciones.serializar;

public class Inicio {
    public static Vendedor[] vendedores = new Vendedor[401];
    public static Producto productos[] = new Producto[200];
    public static Cliente clientes[] = new Cliente[100];
    public static Sucursal sucursales[] = new Sucursal[50];
    public static Factura facturas[] = new Factura[200];
    public JFrame login, ventanaAdm, ventanaVend;
    private OperacionCompra operacion;
    private Acciones accion;
    private DefaultTableModel modelo_sucursales, modelo_productos, modelo_clientes, modelo_vendedores, modelo_Facturas;
    private JTable tabla_sucursales, tabla_productos, tabla_clientes, tabla_vendedores;
    private JPanel panel_productos, panel_vendedores, panel_clientes, ventas;
    private GenerarGrafica gen;
    private JComboBox jcomboCliente;
    private JTable tabla_Facturas;

    public Inicio() {
        accion = new Acciones();
        gen = new GenerarGrafica();
        // reset
        //Acciones.borrarTodo(vendedores,"vendedores.dat",0);
        //Acciones.borrarTodo(productos, "productos.dat",0);
        //Acciones.borrarTodo(clientes, "clientes.dat",0);
        //Acciones.borrarTodo(sucursales, "sucursales.dat",0);
        //Acciones.borrarTodo(facturas, "facturas.dat",0);

        vendedores[0] = new Vendedor(1000, "admin", "admin", 0, 0, 'a', "admin");
        //Acciones.serializar(vendedores, "vendedores.dat");
        vendedores = (Vendedor[]) Acciones.leerArregloSerializado("vendedores.dat");
        Vendedor.contador_vendedores = Acciones.contador_Actual(vendedores);

        productos = (Producto[]) Acciones.leerArregloSerializado("productos.dat");
        Producto.contador_productos = Acciones.contador_Actual(productos);
        clientes = (Cliente[]) Acciones.leerArregloSerializado("clientes.dat");
        Cliente.contador_clientes = Acciones.contador_Actual(clientes);
        sucursales = (Sucursal[]) Acciones.leerArregloSerializado("sucursales.dat");
        Sucursal.contador_sucursales = Acciones.contador_Actual(sucursales);

        facturas = (Factura[]) Acciones.leerArregloSerializado("facturas.dat");
        Factura.no_factura = Acciones.contador_Actual(facturas) + 1001;
        ventana_login();
    }

    // metodo que ejecuta la interfaz inicial
    public void ventana_login() {
        login = inicializar(400, 200, "Inicio");
        JPanel panel = defPanel(login);
        defEtiqueta(panel, 175, 10, 50, 30, "POS", 20);
        defEtiqueta(panel, 50, 50, 50, 25, "Correo");
        JTextField codigo = defAreaTexto(panel, 140, 50, 210, 25);
        defEtiqueta(panel, 50, 85, 80, 25, "Contraseña");
        JTextField psw = defAreaTexto(panel, 140, 85, 210, 25);
        JButton boton = defBoton(panel, 140, 120, 210, 25, "Ingresar");
        login.setVisible(true);
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = codigo.getText();
                String contra = psw.getText();
                Vendedor user = new Vendedor(nombre, contra);

                if (nombre.equals("") || contra.equals("")) {
                    JOptionPane.showMessageDialog(null, "No puede dejar campos en blanco");
                } else {
                    if (accion.buscarUsuario(user, vendedores)) {
                        if (accion.validacionContra(user, vendedores)) {
                            JOptionPane.showMessageDialog(null, "Bienvenido " + nombre);
                            if (nombre.equals("admin")) {
                                login.dispose();
                                codigo.setText("");
                                psw.setText("");
                                admin();
                            } else {
                                login.dispose();
                                codigo.setText("");
                                psw.setText("");
                                vendedores(nombre);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Contraseña Incorrecta");
                            codigo.setText("");
                            psw.setText("");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario No Registrado");
                        codigo.setText("");
                        psw.setText("");
                    }
                }
            }
        });
    }

    //metodo que ejecuta la intefaz del admin
    public void admin() {
        ventanaAdm = inicializar(800, 500, "Administracion");
        JButton salir = defBoton(ventanaAdm, 665, 5, 115, 30, "Cerrar Sesion");
        botonsalir(salir, ventanaAdm);
        JTabbedPane pestañas = defPestaña(ventanaAdm, 20, 20, 760, 440);
        JPanel sucursal = defPanel(pestañas, "Sucursales");
        panel_productos = defPanel(pestañas, "Productos");
        panel_clientes = defPanel(pestañas, "Clientes");
        panel_vendedores = defPanel(pestañas, "Vendedores");
        ventanaAdm.setVisible(true);

        //graficas
//        try {
//            gen.grafica_productos(productos, panel_productos);
//            gen.grafica_vendedores(vendedores, panel_vendedores);
//            gen.grafica_clientes(clientes, panel_clientes);
//        } catch (Exception e) {
//        }

        //encabezados
        String[] encabezado_Sucursales = {"Código", "Nombre", "Dirección", "Correo", "Teléfono"};
        String[] encabezado_Productos = {"Código", "Nombre", "Descripcion", "Cantidad", "Precio"};
        String[] encabezado_Clientes = {"Código", "Nombre", "NIT", "Correo", "Genero"};
        String[] encabezado_Vendedores = {"Código", "Nombre", "Correo", "Caja", "Ventas", "Genero", "Contraseña"};

        //definiendo tablas
        tabla_sucursales = new JTable();
        tabla_productos = new JTable();
        tabla_clientes = new JTable();
        tabla_vendedores = new JTable();

        //definiendo modelos las tablas
        modelo_sucursales = tablas(encabezado_Sucursales, sucursal, 15, 15, 500, 380, tabla_sucursales);
        llenado_tablaSucur(sucursales, modelo_sucursales, tabla_sucursales);
        modelo_productos = tablas(encabezado_Productos, panel_productos, 15, 15, 500, 380, tabla_productos);
        llenado_tablaProd(productos, modelo_productos, tabla_productos);
        modelo_clientes = tablas(encabezado_Clientes, panel_clientes, 15, 15, 500, 380, tabla_clientes);
        llenado_tablaClien(clientes, modelo_clientes, tabla_clientes);
        modelo_vendedores = tablasVendores(encabezado_Vendedores, panel_vendedores, 15, 15, 500, 380, tabla_vendedores);
        llenado_tablaVendedor(vendedores, modelo_vendedores, tabla_vendedores);

        //definiendo llenado de tabla
        //definiendo botones Sucursales
        JButton suc_crear = defBoton(sucursal, 525, 20, 100, 40, "Crear");
        suc_crear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crear_Sucursal();
            }
        });

        JButton suc_carga = defBoton(sucursal, 630, 20, 115, 40, "Carga Masiva");
        suc_carga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accion.cMasiva_sucursales(sucursales);
                llenado_tablaSucur(sucursales, modelo_sucursales, tabla_sucursales);
            }
        });

        JButton suc_actualizar = defBoton(sucursal, 525, 70, 100, 40, "Actualizar");
        suc_actualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizar_Sucursal();
            }
        });

        JButton suc_eliminar = defBoton(sucursal, 630, 70, 115, 40, "Eliminar");
        suc_eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminar_Sucursal();
            }
        });

        JButton suc_exportar = defBoton(sucursal, 525, 120, 220, 40, "Exportar Listado a PDF");
        suc_exportar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GenerarPdf pdf = new GenerarPdf();
                pdf.generarPDFSucursal(sucursales);
            }
        });

        //definiendo botones Productos
        JButton pro_crear = defBoton(panel_productos, 525, 20, 100, 40, "Crear");
        pro_crear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crear_Producto();
            }
        });

        JButton pro_carga = defBoton(panel_productos, 630, 20, 115, 40, "Carga Masiva");
        pro_carga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accion.cMasiva_productos(productos);
                llenado_tablaProd(productos, modelo_productos, tabla_productos);
                gen.grafica_productos(productos, panel_productos);
            }
        });

        JButton pro_actualizar = defBoton(panel_productos, 525, 70, 100, 40, "Actualizar");
        pro_actualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizar_Producto();
            }
        });

        JButton pro_eliminar = defBoton(panel_productos, 630, 70, 115, 40, "Eliminar");
        pro_eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminar_Producto();
            }
        });

        JButton pro_exportar = defBoton(panel_productos, 525, 120, 220, 40, "Exportar Listado a PDF");
        pro_exportar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GenerarPdf pdf = new GenerarPdf();
                pdf.generarPDFProducto(productos);
            }
        });

        //definiendo botones clientes
        JButton cli_crear = defBoton(panel_clientes, 525, 20, 100, 40, "Crear");
        cli_crear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crear_Cliente();
            }
        });

        JButton cli_carga = defBoton(panel_clientes, 630, 20, 115, 40, "Carga Masiva");
        cli_carga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accion.cMasiva_clientes(clientes);
                llenado_tablaClien(clientes, modelo_clientes, tabla_clientes);
                gen.grafica_clientes(clientes, panel_clientes);
            }
        });

        JButton cli_actualizar = defBoton(panel_clientes, 525, 70, 100, 40, "Actualizar");
        cli_actualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizar_Cliente();
            }
        });

        JButton cli_eliminar = defBoton(panel_clientes, 630, 70, 115, 40, "Eliminar");
        cli_eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminar_Cliente();
            }
        });

        JButton cli_exportar = defBoton(panel_clientes, 525, 120, 220, 40, "Exportar Listado a PDF");
        cli_exportar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GenerarPdf pdf = new GenerarPdf();
                pdf.generarPDFCliente(clientes);
            }
        });

        //definiendo botones vendedores
        JButton vend_crear = defBoton(panel_vendedores, 525, 20, 100, 40, "Crear");
        vend_crear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crear_Vendedor();
            }
        });

        JButton vend_carga = defBoton(panel_vendedores, 630, 20, 115, 40, "Carga Masiva");
        vend_carga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accion.cMasiva_vendedores(vendedores);
                llenado_tablaVendedor(vendedores, modelo_vendedores, tabla_vendedores);
                gen.grafica_vendedores(vendedores, panel_vendedores);
            }
        });

        JButton vend_actualizar = defBoton(panel_vendedores, 525, 70, 100, 40, "Actualizar");
        vend_actualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizar_Vendedor();
            }
        });

        JButton vend_eliminar = defBoton(panel_vendedores, 630, 70, 115, 40, "Eliminar");
        vend_eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminar_Vendedor();
            }
        });

        JButton vend_eportar = defBoton(panel_vendedores, 525, 120, 220, 40, "Exportar Listado a PDF");
        vend_eportar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GenerarPdf pdf = new GenerarPdf();
                pdf.generarPDFVendedor(vendedores);
            }
        });

    }

    //funcion para ventanas de vendedores
    public void vendedores(String nombre) {
        ventanaVend = inicializar(800, 500, "Vendedores");
        int val = accion.buscar_vendedorCorreo(nombre, vendedores); // posicion del usuario en el arreglo
        defEtiqueta(ventanaVend, 535, 5, 120, 30, "¡Bienvenido " + vendedores[val].getNombre() + "!");
        JButton salir = defBoton(ventanaVend, 665, 5, 115, 30, "Cerrar Sesion");
        botonsalir(salir, ventanaVend);
        JTabbedPane pestañas = defPestaña(ventanaVend, 20, 20, 760, 440);
        JPanel nuevaVenta = defPanel(pestañas, "Nueva Venta");
        ventas = defPanel(pestañas, "Ventas");
        ventanaVend.setVisible(true);

        // fondo seleccionar

        JLabel fondo_seleccionar = defFondo(nuevaVenta, 15, 10, 720, 180);
        JLabel fondo_agregar = defFondo(nuevaVenta, 15, 200, 720, 200);
        seleccionar_Compra(fondo_seleccionar, fondo_agregar, val);

        //fondo Ventas
        JLabel fondo_Ventas = defFondo(ventas, 15, 10, 720, 390);
        ventas_factura(fondo_Ventas);

    }

    //seleccionar nuevo cliente
    public void seleccionar_Compra(JLabel fondo_seleccionar, JLabel fondo_agregar, int posicionVendedor) {
        Compra[] compras_realizadas = new Compra[20];
        //seleccionar cliente
        operacion = new OperacionCompra();
        // comiendo del codigo para el filtro .. filtro no funcional
        defTituloArea(fondo_seleccionar, 0, 0, 115, 25, "Seleccionar Cliente");
        defEtiquetaEstilo(fondo_seleccionar, 60, 35, 100, 25, "Filtrar por:", 2);
        defEtiqueta(fondo_seleccionar, 150, 35, 70, 25, "Nombre");
        JTextField areaNombre = defAreaTexto(fondo_seleccionar, 200, 35, 200, 25);
        defEtiqueta(fondo_seleccionar, 410, 35, 40, 25, "NIT");
        JTextField areaNit = defAreaTexto(fondo_seleccionar, 455, 35, 200, 25);
        defEtiqueta(fondo_seleccionar, 150, 70, 90, 25, "Correo");
        JTextField areaCorreo = defAreaTexto(fondo_seleccionar, 200, 70, 200, 25);
        defEtiqueta(fondo_seleccionar, 410, 70, 90, 25, "Genero");

        String[] datoslista = {" ", "M", "F"};
        JComboBox listaGenero = opcionLista(datoslista, 455, 70, 200, 25, fondo_seleccionar);

        // filtro no completado...
        defEtiquetaEstilo(fondo_seleccionar, 60, 140, 100, 25, "Filtrados: ", 2);
        JButton aplicar_filtro = defBoton(fondo_seleccionar, 230, 105, 400, 25, "Aplicar Filtro");
        defEtiqueta(fondo_seleccionar, 150, 140, 70, 25, "Cliente");

        jcomboCliente = opcionLista(operacion.cliente_opciones(), 200, 140, 200, 25, fondo_seleccionar);
        JButton nuevoCliente = defBoton(fondo_seleccionar, 420, 140, 150, 25, "Nuevo Cliente");
        nuevoCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crear_ClienteVendedor();
            }
        });

        //realizacion de la compra
        Calendar Cal = Calendar.getInstance();
        String fecha = Cal.get(Cal.DATE) + "/" + (Cal.get(Cal.MONTH) + 1) + "/" + Cal.get(Cal.YEAR);
        defEtiqueta(fondo_agregar, 450, 0, 115, 25, "Fecha: " + fecha);
        JLabel no_factura = etiquetaFactura(fondo_agregar, 570, 0, 125, 25, "No. " + Factura.no_factura);
        // defEtiqueta(fondo_agregar, 570, 0, 125, 25, "No. " + Factura.no_factura);
        defTituloArea(fondo_agregar, 0, 0, 115, 25, "Agregar Productos");
        defEtiqueta(fondo_agregar, 60, 35, 70, 25, "Codigo");
        JTextField areaCodigo = defAreaTexto(fondo_agregar, 110, 35, 200, 25);
        defEtiqueta(fondo_agregar, 320, 35, 70, 25, "Cantidad");
        JTextField areaCantidad = defAreaTexto(fondo_agregar, 380, 35, 200, 25);
        JButton agregar = defBoton(fondo_agregar, 600, 35, 90, 25, "Agregar");
        //tabla
        String[] encabezados = {"Codigo", "Nombre", "Cantidad", "Precio", "Subtotal"};
        JTable tabla_compra = new JTable();
        DefaultTableModel modelo_compra = tablas(encabezados, fondo_agregar, 60, 70, 635, 80, tabla_compra);
        JButton vender = defBoton(fondo_agregar, 250, 160, 200, 25, "Vender");
        vender.setEnabled(false);
        defEtiqueta(fondo_agregar, 550, 160, 90, 25, "Total");
        JTextField label_total = defAreaTexto(fondo_agregar, 590, 160, 100, 25);
        label_total.setEditable(false);

        //accion boton
        agregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int posicion = operacion.buscarProducto(areaCodigo.getText());
                if (posicion < 0) {
                    JOptionPane.showMessageDialog(null, "Codigo de Producto no Registrado");
                } else {
                    int contador_compra = operacion.contador_productos(compras_realizadas);
                    int codigoProducto = Integer.parseInt(areaCodigo.getText());
                    String nombreProducto = productos[posicion].getNombre();
                    int cantidad = Integer.parseInt(areaCantidad.getText());
                    double precio = productos[posicion].getPrecio();
                    Compra nuevo = new Compra(codigoProducto, nombreProducto, cantidad, precio);
                    compras_realizadas[contador_compra] = nuevo;
                    llenado_tablaCompra(compras_realizadas, modelo_compra, tabla_compra);
                    double total = operacion.total_compra(compras_realizadas);
                    label_total.setText(String.valueOf(total));
                    areaCodigo.setText("");
                    areaCantidad.setText("");
                    vender.setEnabled(true);
                }
            }
        });

        vender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int contador_facturas = operacion.contador_factura(facturas);
                int posicionCliente = jcomboCliente.getSelectedIndex();
                int caja = vendedores[posicionVendedor].getCaja();
                String nit = clientes[posicionCliente].getNit();
                String nombre_cliente = clientes[posicionCliente].getNombre();
                facturas[contador_facturas] = new Factura(caja, nit, nombre_cliente, compras_realizadas, fecha);
                vendedores[posicionVendedor].setVentas(vendedores[posicionVendedor].getVentas() + 1);
                Acciones.serializar(facturas, "facturas.dat");
                Acciones.serializar(vendedores, "vendedores.dat");
                GenerarPdf gf = new GenerarPdf();
                gf.generarPDFFactura(facturas[contador_facturas]);
                operacion.borrar_compras(compras_realizadas);
                areaCodigo.setText("");
                areaCantidad.setText("");
                label_total.setText("");
                borrartablaCompra(tabla_compra);
                vender.setEnabled(false);
                no_factura.setText("No. " + Factura.no_factura);
                llenado_tablaFacturas(facturas, modelo_Facturas, tabla_Facturas);

            }
        });

    }

    public void ventas_factura(JLabel fondo) {
        defTituloArea(fondo, 0, 0, 95, 25, "Listado General");
        defEtiquetaEstilo(fondo, 60, 35, 100, 25, "Filtrar por:", 2);
        defEtiqueta(fondo, 150, 35, 70, 25, "No. Factura");
        JTextField areaFactura = defAreaTexto(fondo, 220, 35, 180, 25);
        defEtiqueta(fondo, 410, 35, 40, 25, "NIT");
        JTextField areaNit = defAreaTexto(fondo, 455, 35, 200, 25);
        defEtiqueta(fondo, 150, 70, 90, 25, "Nombre");
        JTextField areaNombre = defAreaTexto(fondo, 220, 70, 180, 25);
        defEtiqueta(fondo, 410, 70, 90, 25, "Fecha");
        JTextField areaFecha = defAreaTexto(fondo, 455, 70, 200, 25);
        JButton aplicar_filtro = defBoton(fondo, 230, 105, 400, 25, "Aplicar Filtro");
        defEtiquetaEstilo(fondo, 60, 130, 100, 25, "Filtrados: ", 2);
        String[] encabezados = {"No. Factura", "NIT", "Nombre", "Fecha", "Total", "Acciones"};
        tabla_Facturas = new JTable();
        modelo_Facturas = tablasVenta(encabezados, fondo, 60, 160, 635, 220, tabla_Facturas);
        llenado_tablaFacturas(facturas, modelo_Facturas, tabla_Facturas);

    }

    public JComboBox opcionLista(String[] lista, int x, int y, int ancho, int alto, JLabel fondoLabel) {
        JComboBox caja = new JComboBox(lista);
        caja.setBounds(x, y, ancho, alto);
        fondoLabel.add(caja);
        return caja;
    }

    public JComboBox opcionLista(String[] lista, int x, int y, int ancho, int alto, JPanel panel) {
        JComboBox caja = new JComboBox(lista);
        caja.setBounds(x, y, ancho, alto);
        panel.add(caja);
        return caja;
    }

    //funcion para establecer un boton para salir
    public void botonsalir(JButton boton, JFrame frame1) {
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame1.dispose();
                login.setVisible(true);

            }
        });
    }

    //funcion para definir pestañas
    public JTabbedPane defPestaña(JFrame frame, int x, int y, int ancho, int alto) {
        JTabbedPane pestañas = new JTabbedPane();
        pestañas.setBounds(x, y, ancho, alto);
        pestañas.setVisible(true);
        frame.getContentPane().add(pestañas);
        return pestañas;
    }

    //funcion para definir un frame
    public JFrame inicializar(int x, int y, String titulo) {
        JFrame frame = new JFrame();
        frame.setSize(x, y);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(135, 206, 235));
        frame.setTitle(titulo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        return frame;
    }

    public JFrame inicializar_extras(int x, int y, String titulo) {
        JFrame frame = new JFrame();
        frame.setSize(x, y);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(135, 206, 235));
        frame.setTitle(titulo);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        return frame;
    }

    //funcion para hacer un panel
    public JPanel defPanel(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        panel.setLayout(null);
        panel.setVisible(true);
        panel.setBackground(new Color(135, 206, 235));
        frame.getContentPane().add(panel);
        return panel;
    }

    public JPanel defPanel(JTabbedPane pestaña, String nombre) {
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, pestaña.getWidth(), pestaña.getHeight());
        panel.setLayout(null);
        panel.setVisible(true);
        panel.setBackground(new Color(135, 206, 235));
        pestaña.addTab(nombre, panel);
        return panel;
    }

    //metodo para definir una etiqueta (se usará para el tutilo)
    public void defEtiqueta(JPanel panel, int x, int y, int ancho, int alto, String texto, int tamaño) {
        JLabel label = new JLabel(texto);
        label.setBounds(x, y, ancho, alto);
        label.setLayout(null);
        label.setFont(new Font("", 0, tamaño));
        label.setVisible(true);
        panel.add(label);
    }

    //metodo para definir una etiqueta (se usará para el cuerpo de todo)
    public void defEtiqueta(JPanel panel, int x, int y, int ancho, int alto, String texto) {
        JLabel label = new JLabel(texto);
        label.setBounds(x, y, ancho, alto);
        label.setLayout(null);
        label.setVisible(true);
        panel.add(label);
    }

    //metodo para definir una etiqueta (se usará para el cuerpo de vendedores)
    public void defEtiqueta(JLabel panel, int x, int y, int ancho, int alto, String texto) {
        JLabel label = new JLabel(texto);
        label.setBounds(x, y, ancho, alto);
        label.setLayout(null);
        label.setVisible(true);
        panel.add(label);
    }

    public JLabel etiquetaFactura(JLabel panel, int x, int y, int ancho, int alto, String texto) {
        JLabel label = new JLabel(texto);
        label.setBounds(x, y, ancho, alto);
        label.setLayout(null);
        label.setVisible(true);
        panel.add(label);
        return label;
    }

    //metodo para definir una etiqueta (se usará para el cuerpo de vendedores)
    public void defEtiquetaEstilo(JLabel panel, int x, int y, int ancho, int alto, String texto, int n) {
        JLabel label = new JLabel(texto);
        label.setBounds(x, y, ancho, alto);
        label.setLayout(null);
        label.setFont(new Font("", n, 12));
        label.setVisible(true);
        panel.add(label);
    }

    public void defEtiqueta(JFrame panel, int x, int y, int ancho, int alto, String texto) {
        JLabel label = new JLabel(texto);
        label.setBounds(x, y, ancho, alto);
        label.setLayout(null);
        label.setVisible(true);
        panel.add(label);
    }

    public void defTituloArea(JLabel panel, int x, int y, int ancho, int alto, String texto) {
        JLabel label = new JLabel(texto);
        label.setBounds(x, y, ancho, alto);
        label.setLayout(null);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label.setVisible(true);
        panel.add(label);
    }

    //metodo para definir una etiqueta se usará para el cuerpo de todo
    public JLabel defFondo(JPanel panel, int x, int y, int ancho, int alto) {
        JLabel label = new JLabel();
        label.setBounds(x, y, ancho, alto);
        label.setLayout(null);
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label.setVisible(true);
        panel.add(label);
        return label;
    }

    // funcion para definir y devolveruna caja de texto
    public JTextField defAreaTexto(JPanel panel, int x, int y, int ancho, int alto) {
        JTextField area = new JTextField();
        area.setBounds(x, y, ancho, alto);
        area.setLayout(null);
        area.setVisible(true);
        panel.add(area);
        return area;
    }

    public JTextField defAreaTexto(JLabel panel, int x, int y, int ancho, int alto) {
        JTextField area = new JTextField();
        area.setBounds(x, y, ancho, alto);
        area.setLayout(null);
        area.setVisible(true);
        panel.add(area);
        return area;
    }

    //funcion para definir  y revolver un boton
    public JButton defBoton(JPanel panel, int x, int y, int ancho, int alto, String texto) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, ancho, alto);
        boton.setLayout(null);
        boton.setVisible(true);
        panel.add(boton);
        return boton;
    }

    //funcion para definir  y revolver un boton
    public JButton defBoton(JLabel panel, int x, int y, int ancho, int alto, String texto) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, ancho, alto);
        boton.setLayout(null);
        boton.setVisible(true);
        panel.add(boton);
        return boton;
    }

    public JButton defBoton(JFrame frame, int x, int y, int ancho, int alto, String texto) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, ancho, alto);
        boton.setLayout(null);
        boton.setVisible(true);
        frame.add(boton);
        return boton;
    }

    public DefaultTableModel tablas(String[] var, JPanel panel, int x, int y, int ancho, int alto, JTable tabla) {
        DefaultTableModel modelo = new DefaultTableModel();
        //encabezados
        modelo.addColumn(var[0]);
        modelo.addColumn(var[1]);
        modelo.addColumn(var[2]);
        modelo.addColumn(var[3]);
        modelo.addColumn(var[4]);

        //datos
        tabla.setModel(modelo);
        tabla.setEnabled(false);
        JScrollPane barra = new JScrollPane(tabla);
        barra.setBounds(x, y, ancho, alto);
        panel.add(barra);
        return modelo;
    }

    public DefaultTableModel tablasVendores(String[] var, JPanel panel, int x, int y, int ancho, int alto, JTable tabla) {
        DefaultTableModel modelo = new DefaultTableModel();
        //encabezados
        modelo.addColumn(var[0]);
        modelo.addColumn(var[1]);
        modelo.addColumn(var[2]);
        modelo.addColumn(var[3]);
        modelo.addColumn(var[4]);
        modelo.addColumn(var[5]);
        modelo.addColumn(var[6]);

        //datos
        tabla.setModel(modelo);
        tabla.setEnabled(false);
        JScrollPane barra = new JScrollPane(tabla);
        barra.setBounds(x, y, ancho, alto);
        panel.add(barra);
        return modelo;
    }

    public DefaultTableModel tablas(String[] var, JLabel panel, int x, int y, int ancho, int alto, JTable tabla) {
        DefaultTableModel modelo = new DefaultTableModel();
        //encabezados
        modelo.addColumn(var[0]);
        modelo.addColumn(var[1]);
        modelo.addColumn(var[2]);
        modelo.addColumn(var[3]);
        modelo.addColumn(var[4]);

        //datos
        tabla.setModel(modelo);
        tabla.setEnabled(false);
        JScrollPane barra = new JScrollPane(tabla);
        barra.setBounds(x, y, ancho, alto);
        panel.add(barra);
        return modelo;
    }

    public void llenado_tablaProd(Producto[] productos, DefaultTableModel modelo_productos, JTable tabla_productos) {
        DefaultTableModel df = (DefaultTableModel) tabla_productos.getModel();
        int a = tabla_productos.getRowCount() - 1;
        for (int i = a; i >= 0; i--) {
            df.removeRow(df.getRowCount() - 1);
        }

        for (int i = 0; i < productos.length; i++) {
            if (productos[i] != null) {
                String datos[] = new String[5];
                datos[0] = String.valueOf(productos[i].getCodigo());
                datos[1] = productos[i].getNombre();
                datos[2] = productos[i].getDescripcion();
                datos[3] = String.valueOf(productos[i].getCantidad());
                datos[4] = String.valueOf(productos[i].getPrecio());
                modelo_productos.addRow(datos);
            } else {
                break;
            }
        }
    }

    public void llenado_tablaClien(Cliente[] clientes, DefaultTableModel modelo_clientes, JTable tabla_clientes) {
        DefaultTableModel df = (DefaultTableModel) tabla_clientes.getModel();
        int a = tabla_clientes.getRowCount() - 1;
        for (int i = a; i >= 0; i--) {
            df.removeRow(df.getRowCount() - 1);
        }

        for (int i = 0; i < clientes.length; i++) {
            if (clientes[i] != null) {
                String datos[] = new String[5];
                datos[0] = String.valueOf(clientes[i].getCodigo());
                datos[1] = clientes[i].getNombre();
                datos[2] = String.valueOf(clientes[i].getNit());
                datos[3] = clientes[i].getCorreo();
                datos[4] = String.valueOf(clientes[i].getGenero());
                modelo_clientes.addRow(datos);
            } else {
                break;
            }
        }
    }

    public void llenado_tablaSucur(Sucursal[] sucursales, DefaultTableModel modelo_sucursales, JTable tabla_sucursales) {
        DefaultTableModel df = (DefaultTableModel) tabla_sucursales.getModel();
        int a = tabla_sucursales.getRowCount() - 1;
        for (int i = a; i >= 0; i--) {
            df.removeRow(df.getRowCount() - 1);
        }

        for (int i = 0; i < sucursales.length; i++) {
            if (sucursales[i] != null) {
                String datos[] = new String[5];
                datos[0] = String.valueOf(sucursales[i].getCodigo());
                datos[1] = sucursales[i].getNombre();
                datos[2] = sucursales[i].getDireccion();
                datos[3] = sucursales[i].getCorreo();
                datos[4] = String.valueOf(sucursales[i].getTelefono());
                modelo_sucursales.addRow(datos);
            } else {
                break;
            }
        }
    }

    public void llenado_tablaVendedor(Vendedor[] vendedores, DefaultTableModel modelo_vendedores, JTable tabla_vendedores) {
        DefaultTableModel df = (DefaultTableModel) tabla_vendedores.getModel();
        int a = tabla_vendedores.getRowCount() - 1;
        for (int i = a; i >= 0; i--) {
            df.removeRow(df.getRowCount() - 1);
        }

        for (int i = 1; i < vendedores.length; i++) {
            if (vendedores[i] != null) {
                String datos[] = new String[7];
                datos[0] = String.valueOf(vendedores[i].getCodigo());
                datos[1] = vendedores[i].getNombre();
                datos[2] = vendedores[i].getCorreo();
                datos[3] = String.valueOf(vendedores[i].getCaja());
                datos[4] = String.valueOf(vendedores[i].getVentas());
                datos[5] = String.valueOf(vendedores[i].getGenero());
                datos[6] = vendedores[i].getPassword();
                modelo_vendedores.addRow(datos);
            } else {
                break;
            }
        }
    }

    public void llenado_tablaCompra(Compra[] compras, DefaultTableModel modelo_compra, JTable tabla_compra) {
        DefaultTableModel df = (DefaultTableModel) tabla_compra.getModel();
        int a = tabla_compra.getRowCount() - 1;
        for (int i = a; i >= 0; i--) {
            df.removeRow(df.getRowCount() - 1);
        }

        for (int i = 0; i < compras.length; i++) {
            if (compras[i] != null) {
                String datos[] = new String[5];
                datos[0] = String.valueOf(compras[i].getCodigo_producto());
                datos[1] = compras[i].getNombre_producto();
                datos[2] = String.valueOf(compras[i].getCantidad());
                datos[3] = String.valueOf(compras[i].getPrecio());
                datos[4] = String.valueOf(compras[i].getSubtotal());
                modelo_compra.addRow(datos);
            } else {
                break;
            }
        }
    }

    public void llenado_tablaFacturas(Factura[] facturas, DefaultTableModel modelo_factura, JTable tabla_factura) {
        DefaultTableModel df = (DefaultTableModel) tabla_factura.getModel();
        int a = tabla_factura.getRowCount() - 1;
        for (int i = a; i >= 0; i--) {
            df.removeRow(df.getRowCount() - 1);
        }

        for (int i = 0; i < facturas.length; i++) {
            if (facturas[i] != null) {
                String datos[] = new String[6];
                datos[0] = String.valueOf(facturas[i].getNumeroFac());
                datos[1] = String.valueOf(facturas[i].getNit());
                datos[2] = facturas[i].getNombre_cliente();
                datos[3] = facturas[i].getFecha();
                datos[4] = String.valueOf(facturas[i].getTotal());
                datos[5] = "Ver acciones";
                modelo_factura.addRow(datos);
            } else {
                break;
            }
        }
    }

    public void borrartablaCompra(JTable tabla) {
        DefaultTableModel df = (DefaultTableModel) tabla.getModel();
        int a = tabla.getRowCount() - 1;
        for (int i = a; i >= 0; i--) {
            df.removeRow(df.getRowCount() - 1);
        }
    }

    public DefaultTableModel tablasVenta(String[] var, JLabel panel, int x, int y, int ancho, int alto, JTable tabla) {
        DefaultTableModel modelo = new DefaultTableModel();
        //encabezados
        modelo.addColumn(var[0]);
        modelo.addColumn(var[1]);
        modelo.addColumn(var[2]);
        modelo.addColumn(var[3]);
        modelo.addColumn(var[4]);
        modelo.addColumn(var[5]);

        //datos
        tabla.setModel(modelo);
        tabla.setEnabled(false);
        JScrollPane barra = new JScrollPane(tabla);
        barra.setBounds(x, y, ancho, alto);
        panel.add(barra);
        return modelo;
    }

    ///productos
    public void crear_Producto() {
        JFrame frame = inicializar_extras(300, 400, "Crear Producto");
        JPanel panel = defPanel(frame);
        defEtiqueta(panel, 50, 10, 200, 30, "Crear Nuevo Producto", 20);
        defEtiqueta(panel, 30, 60, 100, 25, "Código");
        JTextField areaCodigo = defAreaTexto(panel, 100, 60, 150, 25);
        defEtiqueta(panel, 30, 100, 100, 25, "Nombre");
        JTextField areaNombre = defAreaTexto(panel, 100, 100, 150, 25);
        defEtiqueta(panel, 30, 140, 100, 25, "Descripcion");
        JTextField areaDescripcion = defAreaTexto(panel, 100, 140, 150, 25);
        defEtiqueta(panel, 30, 180, 100, 25, "Cantidad");
        JTextField areaCantidad = defAreaTexto(panel, 100, 180, 150, 25);
        defEtiqueta(panel, 30, 220, 100, 25, "Precio");
        JTextField areaPrecio = defAreaTexto(panel, 100, 220, 150, 25);
        JButton agregar = defBoton(panel, 100, 270, 100, 30, "Agregar");

        agregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo = Integer.parseInt(areaCodigo.getText());
                    String nombre = areaNombre.getText();
                    String descripcion = areaDescripcion.getText();
                    int cantidad = Integer.parseInt(areaCantidad.getText());
                    double precio = Double.parseDouble(areaPrecio.getText());
                    accion.agregar_producto(codigo, nombre, descripcion, cantidad, precio, productos);
                    llenado_tablaProd(productos, modelo_productos, tabla_productos);
                    gen.grafica_productos(productos, panel_productos);
                    frame.dispose();
                } catch (Exception num) {
                    JOptionPane.showMessageDialog(null, num);
                    areaCantidad.setText("");
                    areaCodigo.setText("");
                    areaDescripcion.setText("");
                    areaNombre.setText("");
                    areaPrecio.setText("");
                }
            }
        });

        frame.setVisible(true);
    }

    public void eliminar_Producto() {
        try {
            int codigo = Integer.parseInt(JOptionPane.showInputDialog("Ingrese codigo de producto a eliminar"));
            accion.eliminar_producto(codigo, productos);
            llenado_tablaProd(productos, modelo_productos, tabla_productos);
            gen.grafica_productos(productos, panel_productos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "El valor del codigo tiene que ser un numero");
        }
    }

    public void actualizar_Producto() {
        try {
            int codigo = Integer.parseInt(JOptionPane.showInputDialog("Ingrese codigo de producto a modificar"));
            int val = accion.buscar_producto(codigo, productos);
            if (val >= 0) {
                JFrame frame = inicializar_extras(300, 400, "Modificar Producto");
                JPanel panel = defPanel(frame);
                defEtiqueta(panel, 50, 10, 200, 30, "Modificar Producto", 20);
                defEtiqueta(panel, 30, 60, 100, 25, "Código");
                JTextField areaCodigo = defAreaTexto(panel, 100, 60, 150, 25);
                areaCodigo.setText(String.valueOf(codigo));
                areaCodigo.setEditable(false);
                defEtiqueta(panel, 30, 100, 100, 25, "Nombre");
                JTextField areaNombre = defAreaTexto(panel, 100, 100, 150, 25);
                areaNombre.setText(productos[val].getNombre());
                defEtiqueta(panel, 30, 140, 100, 25, "Descripcion");
                JTextField areaDescripcion = defAreaTexto(panel, 100, 140, 150, 25);
                areaDescripcion.setText(productos[val].getDescripcion());
                defEtiqueta(panel, 30, 180, 100, 25, "Cantidad");
                JTextField areaCantidad = defAreaTexto(panel, 100, 180, 150, 25);
                areaCantidad.setText(String.valueOf(productos[val].getCantidad()));
                defEtiqueta(panel, 30, 220, 100, 25, "Precio");
                JTextField areaPrecio = defAreaTexto(panel, 100, 220, 150, 25);
                areaPrecio.setText(String.valueOf(productos[val].getPrecio()));
                JButton agregar = defBoton(panel, 100, 270, 100, 30, "Agregar");
                agregar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            String nombre = areaNombre.getText();
                            String descripcion = areaDescripcion.getText();
                            int cantidad = Integer.parseInt(areaCantidad.getText());
                            double precio = Double.parseDouble(areaPrecio.getText());
                            accion.actualizar_producto(codigo, nombre, descripcion, cantidad, precio, productos);
                            llenado_tablaProd(productos, modelo_productos, tabla_productos);
                            gen.grafica_productos(productos, panel_productos);
                            frame.dispose();
                        } catch (Exception num) {
                            JOptionPane.showMessageDialog(null, num);
                            areaCantidad.setText("");
                            areaCodigo.setText("");
                            areaDescripcion.setText("");
                            areaNombre.setText("");
                            areaPrecio.setText("");
                        }

                    }
                });
                frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Codigo no registrado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "El valor del codigo tiene que ser un numero");
        }
    }

    //clientes
    public void crear_Cliente() {
        JFrame frame = inicializar_extras(300, 400, "Crear Cliente");
        JPanel panel = defPanel(frame);
        defEtiqueta(panel, 50, 10, 200, 30, "Crear Nuevo Cliente", 20);
        defEtiqueta(panel, 30, 60, 100, 25, "Código");
        JTextField areaCodigo = defAreaTexto(panel, 100, 60, 150, 25);
        defEtiqueta(panel, 30, 100, 100, 25, "Nombre");
        JTextField areaNombre = defAreaTexto(panel, 100, 100, 150, 25);
        defEtiqueta(panel, 30, 140, 100, 25, "NIT");
        JTextField areaNit = defAreaTexto(panel, 100, 140, 150, 25);
        defEtiqueta(panel, 30, 180, 100, 25, "Correo");
        JTextField areaCorreo = defAreaTexto(panel, 100, 180, 150, 25);
        defEtiqueta(panel, 30, 220, 100, 25, "Genero");
        JTextField areaGenero = defAreaTexto(panel, 100, 220, 150, 25);
        JButton agregar = defBoton(panel, 100, 270, 100, 30, "Agregar");
        agregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo = Integer.parseInt(areaCodigo.getText());
                    String nombre = areaNombre.getText();
                    String nit = areaNit.getText();
                    String correo = areaCorreo.getText();
                    char genero = areaGenero.getText().charAt(0);
                    accion.agregar_cliente(codigo, nombre, nit, correo, genero, clientes);
                    llenado_tablaClien(clientes, modelo_clientes, tabla_clientes);
                    gen.grafica_clientes(clientes, panel_clientes);
                    frame.dispose();
                } catch (Exception num) {
                    JOptionPane.showMessageDialog(null, num);
                    //                   JOptionPane.showMessageDialog(null, "Area de codigo, nit tienen que ser valores numericos");
                    areaCodigo.setText("");
                    areaNombre.setText("");
                    areaNit.setText("");
                    areaCorreo.setText("");
                    areaGenero.setText("");
                }
            }
        });
        frame.setVisible(true);
    }

    public void eliminar_Cliente() {
        try {
            int codigo = Integer.parseInt(JOptionPane.showInputDialog("Ingrese codigo de Cliente a eliminar"));
            accion.eliminar_cliente(codigo, clientes);
            llenado_tablaClien(clientes, modelo_clientes, tabla_clientes);
            gen.grafica_clientes(clientes, panel_clientes);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "El valor del codigo tiene que ser un numero");
        }
    }

    //modificando
    public void actualizar_Cliente() {
        try {
            int codigo = Integer.parseInt(JOptionPane.showInputDialog("Ingrese codigo de Cliente a modificar"));
            int val = accion.buscar_cliente(codigo, clientes);
            if (val >= 0) {
                JFrame frame = inicializar_extras(300, 400, "Modificar Cliente");
                JPanel panel = defPanel(frame);
                defEtiqueta(panel, 50, 10, 200, 30, "Modificar Cliente", 20);
                defEtiqueta(panel, 30, 60, 100, 25, "Código");
                JTextField areaCodigo = defAreaTexto(panel, 100, 60, 150, 25);
                areaCodigo.setText(String.valueOf(codigo));
                areaCodigo.setEditable(false);
                defEtiqueta(panel, 30, 100, 100, 25, "Nombre");
                JTextField areaNombre = defAreaTexto(panel, 100, 100, 150, 25);
                areaNombre.setText(clientes[val].getNombre());
                defEtiqueta(panel, 30, 140, 100, 25, "NIT");
                JTextField areaNit = defAreaTexto(panel, 100, 140, 150, 25);
                areaNit.setText(String.valueOf(clientes[val].getNit()));
                defEtiqueta(panel, 30, 180, 100, 25, "Correo");
                JTextField areaCorreo = defAreaTexto(panel, 100, 180, 150, 25);
                areaCorreo.setText(clientes[val].getCorreo());
                defEtiqueta(panel, 30, 220, 100, 25, "Genero");
                JTextField areaGenero = defAreaTexto(panel, 100, 220, 150, 25);
                areaGenero.setText(String.valueOf(clientes[val].getGenero()));
                JButton agregar = defBoton(panel, 100, 270, 100, 30, "Agregar");
                agregar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            String nombre = areaNombre.getText();
                            String nit = areaNit.getText();
                            String correo = areaCorreo.getText();
                            char genero = areaGenero.getText().charAt(0);
                            accion.actualizar_cliente(codigo, nombre, nit, correo, genero, clientes);
                            llenado_tablaClien(clientes, modelo_clientes, tabla_clientes);
                            gen.grafica_clientes(clientes, panel_clientes);
                            frame.dispose();
                        } catch (Exception num) {
                            JOptionPane.showMessageDialog(null, "Area de codigo tiene que ser valores numericos");
                        }

                    }
                });
                frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Codigo no registrado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "El valor del codigo tiene que ser un numero");
        }
    }

    //sucursales
    public void crear_Sucursal() {
        JFrame frame = inicializar_extras(300, 400, "Crear Sucursal");
        JPanel panel = defPanel(frame);
        defEtiqueta(panel, 50, 10, 200, 30, "Crear Nueva Sucursal", 20);
        defEtiqueta(panel, 30, 60, 100, 25, "Código");
        JTextField areaCodigo = defAreaTexto(panel, 100, 60, 150, 25);
        defEtiqueta(panel, 30, 100, 100, 25, "Nombre");
        JTextField areaNombre = defAreaTexto(panel, 100, 100, 150, 25);
        defEtiqueta(panel, 30, 140, 100, 25, "Dirección");
        JTextField areaDireccion = defAreaTexto(panel, 100, 140, 150, 25);
        defEtiqueta(panel, 30, 180, 100, 25, "Correo");
        JTextField areaCorreo = defAreaTexto(panel, 100, 180, 150, 25);
        defEtiqueta(panel, 30, 220, 100, 25, "Telefono");
        JTextField areaTelefono = defAreaTexto(panel, 100, 220, 150, 25);
        JButton agregar = defBoton(panel, 100, 270, 100, 30, "Agregar");
        agregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo = Integer.parseInt(areaCodigo.getText());
                    String nombre = areaNombre.getText();
                    String direccion = areaDireccion.getText();
                    String correo = areaCorreo.getText();
                    int telefono = Integer.parseInt(areaTelefono.getText());
                    accion.agregar_sucursal(codigo, nombre, direccion, correo, telefono, sucursales);
                    llenado_tablaSucur(sucursales, modelo_sucursales, tabla_sucursales);
                    frame.dispose();
                } catch (Exception num) {
                    JOptionPane.showMessageDialog(null, "Area de codigo, telefono tienen que ser valores numericos");
                    areaCodigo.setText("");
                    areaNombre.setText("");
                    areaDireccion.setText("");
                    areaCorreo.setText("");
                    areaTelefono.setText("");
                }
            }
        });
        frame.setVisible(true);
    }

    public void eliminar_Sucursal() {
        try {
            int codigo = Integer.parseInt(JOptionPane.showInputDialog("Ingrese codigo de Sucursal a eliminar"));
            accion.eliminar_sucursal(codigo, sucursales);
            llenado_tablaSucur(sucursales, modelo_sucursales, tabla_sucursales);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "El valor del codigo tiene que ser un numero");
        }
    }

    public void actualizar_Sucursal() {
        try {
            int codigo = Integer.parseInt(JOptionPane.showInputDialog("Ingrese codigo de Sucursal a modificar"));
            int val = accion.buscar_sucursal(codigo, sucursales);
            if (val >= 0) {
                JFrame frame = inicializar_extras(300, 400, "Modificar Sucursal");
                JPanel panel = defPanel(frame);
                defEtiqueta(panel, 50, 10, 200, 30, "Modificar Sucursal", 20);
                defEtiqueta(panel, 30, 60, 100, 25, "Código");
                JTextField areaCodigo = defAreaTexto(panel, 100, 60, 150, 25);
                areaCodigo.setText(String.valueOf(codigo));
                areaCodigo.setEditable(false);
                defEtiqueta(panel, 30, 100, 100, 25, "Nombre");
                JTextField areaNombre = defAreaTexto(panel, 100, 100, 150, 25);
                areaNombre.setText(sucursales[val].getNombre());
                defEtiqueta(panel, 30, 140, 100, 25, "Direccion");
                JTextField areaDireccion = defAreaTexto(panel, 100, 140, 150, 25);
                areaDireccion.setText(sucursales[val].getDireccion());
                defEtiqueta(panel, 30, 180, 100, 25, "Correo");
                JTextField areaCorreo = defAreaTexto(panel, 100, 180, 150, 25);
                areaCorreo.setText(sucursales[val].getCorreo());
                defEtiqueta(panel, 30, 220, 100, 25, "Telefono");
                JTextField areaTelefono = defAreaTexto(panel, 100, 220, 150, 25);
                areaTelefono.setText(String.valueOf(sucursales[val].getTelefono()));
                JButton agregar = defBoton(panel, 100, 270, 100, 30, "Agregar");
                agregar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            String nombre = areaNombre.getText();
                            String direccion = areaDireccion.getText();
                            String correo = areaCorreo.getText();
                            int telefono = Integer.parseInt(areaTelefono.getText());
                            accion.actualizar_sucursal(codigo, nombre, direccion, correo, telefono, sucursales);
                            llenado_tablaSucur(sucursales, modelo_sucursales, tabla_sucursales);
                            frame.dispose();
                        } catch (Exception num) {
                            JOptionPane.showMessageDialog(null, "Area de codigo, telefono tienen que ser valores numericos");
                            areaCodigo.setText("");
                            areaNombre.setText("");
                            areaDireccion.setText("");
                            areaCorreo.setText("");
                            areaTelefono.setText("");
                        }
                    }
                });
                frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Codigo no registrado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "El valor del codigo tiene que ser un numero");
        }
    }

    //vendedores
    public void crear_Vendedor() {
        JFrame frame = inicializar_extras(300, 400, "Crear Vendedor");
        JPanel panel = defPanel(frame);
        defEtiqueta(panel, 40, 10, 250, 30, "Crear Nuevo Vendedor", 20);
        defEtiqueta(panel, 30, 60, 100, 25, "Código");
        JTextField areaCodigo = defAreaTexto(panel, 100, 60, 150, 25);
        defEtiqueta(panel, 30, 100, 100, 25, "Nombre");
        JTextField areaNombre = defAreaTexto(panel, 100, 100, 150, 25);
        defEtiqueta(panel, 30, 140, 100, 25, "Correo");
        JTextField areaCorreo = defAreaTexto(panel, 100, 140, 150, 25);
        defEtiqueta(panel, 30, 180, 100, 25, "Caja");
        JTextField areaCaja = defAreaTexto(panel, 100, 180, 150, 25);
        defEtiqueta(panel, 30, 220, 100, 25, "Ventas");
        JTextField areaVentas = defAreaTexto(panel, 100, 220, 150, 25);
        defEtiqueta(panel, 30, 260, 100, 25, "Genero");
        JTextField areaGenero = defAreaTexto(panel, 100, 260, 150, 25);
        defEtiqueta(panel, 30, 300, 100, 25, "Contraseña");
        JTextField areaPsw = defAreaTexto(panel, 100, 300, 150, 25);
        JButton agregar = defBoton(panel, 100, 330, 100, 30, "Agregar");
        frame.setVisible(true);
        agregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo = Integer.parseInt(areaCodigo.getText());
                    String nombre = areaNombre.getText();
                    String correo = areaCorreo.getText();
                    int caja = Integer.parseInt(areaCaja.getText());
                    int ventas = Integer.parseInt(areaVentas.getText());
                    char genero = areaGenero.getText().charAt(0);
                    String psw = areaPsw.getText();
                    int pos = accion.buscar_vendedorCorreo(correo, vendedores);
                    if (pos < 0) {
                        accion.agregar_vendedor(codigo, nombre, correo, caja, ventas, genero, psw, vendedores);
                        llenado_tablaVendedor(vendedores, modelo_vendedores, tabla_vendedores);
                        gen.grafica_vendedores(vendedores, panel_vendedores);
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Correo en Uso");
                        areaCorreo.setText("");
                    }

                } catch (Exception unu) {
                    JOptionPane.showMessageDialog(null, "Area de caja y ventas tienen que ser valores numericos");
                    areaCodigo.setText("");
                    areaNombre.setText("");
                    areaCorreo.setText("");
                    areaCaja.setText("");
                    areaVentas.setText("");
                    areaGenero.setText("");
                    areaPsw.setText("");
                }

            }
        });
    }

    public void eliminar_Vendedor() {
        try {
            int codigo = Integer.parseInt(JOptionPane.showInputDialog("Ingrese codigo de Vendedor a eliminar"));
            accion.eliminar_vendedor(codigo, vendedores);
            llenado_tablaVendedor(vendedores, modelo_vendedores, tabla_vendedores);
            gen.grafica_vendedores(vendedores, panel_vendedores);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "El valor del codigo tiene que ser un numero");
        }
    }

    public void actualizar_Vendedor() {
        try {
            int codigo = Integer.parseInt(JOptionPane.showInputDialog("Ingrese codigo de Sucursal a modificar"));
            int val = accion.buscar_vendedor(codigo, vendedores);
            if (val >= 0) {
                JFrame frame = inicializar_extras(300, 400, "Actualizar Vendedor");
                JPanel panel = defPanel(frame);
                defEtiqueta(panel, 40, 10, 250, 30, "Actualizar Vendedor", 20);
                defEtiqueta(panel, 30, 60, 100, 25, "Código");
                JTextField areaCodigo = defAreaTexto(panel, 100, 60, 150, 25);
                areaCodigo.setText(String.valueOf(codigo));
                areaCodigo.setEditable(false);
                defEtiqueta(panel, 30, 100, 100, 25, "Nombre");
                JTextField areaNombre = defAreaTexto(panel, 100, 100, 150, 25);
                areaNombre.setText(vendedores[val].getNombre());
                defEtiqueta(panel, 30, 140, 100, 25, "Correo");
                JTextField areaCorreo = defAreaTexto(panel, 100, 140, 150, 25);
                areaCorreo.setText(vendedores[val].getCorreo());
                defEtiqueta(panel, 30, 180, 100, 25, "Caja");
                JTextField areaCaja = defAreaTexto(panel, 100, 180, 150, 25);
                areaCaja.setText(String.valueOf(vendedores[val].getCaja()));
                defEtiqueta(panel, 30, 220, 100, 25, "Ventas");
                JTextField areaVentas = defAreaTexto(panel, 100, 220, 150, 25);
                areaVentas.setText(String.valueOf(vendedores[val].getVentas()));
                defEtiqueta(panel, 30, 260, 100, 25, "Genero");
                JTextField areaGenero = defAreaTexto(panel, 100, 260, 150, 25);
                areaGenero.setText(String.valueOf(vendedores[val].getGenero()));
                defEtiqueta(panel, 30, 300, 100, 25, "Contraseña");
                JTextField areaPsw = defAreaTexto(panel, 100, 300, 150, 25);
                areaPsw.setText(vendedores[val].getPassword());
                JButton agregar = defBoton(panel, 100, 330, 100, 30, "Agregar");
                agregar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            String nombre = areaNombre.getText();
                            String correo = areaCorreo.getText();
                            int caja = Integer.parseInt(areaCaja.getText());
                            int ventas = Integer.parseInt(areaVentas.getText());
                            char genero = areaGenero.getText().charAt(0);
                            String psw = areaPsw.getText();
                            int pos = accion.buscar_vendedorCorreo(correo, vendedores);
                            if (pos < 0) {
                                accion.actualizar_vendedor(codigo, nombre, correo, caja, ventas, genero, psw, vendedores);
                                llenado_tablaVendedor(vendedores, modelo_vendedores, tabla_vendedores);
                                frame.dispose();
                                gen.grafica_vendedores(vendedores, panel_vendedores);
                            } else {
                                if (vendedores[pos].getCodigo()==codigo){
                                    accion.actualizar_vendedor(codigo, nombre, correo, caja, ventas, genero, psw, vendedores);
                                    llenado_tablaVendedor(vendedores, modelo_vendedores, tabla_vendedores);
                                    frame.dispose();
                                    gen.grafica_vendedores(vendedores, panel_vendedores);
                                }else{
                                    JOptionPane.showMessageDialog(null, "Correo en Uso");
                                    areaCorreo.setText("");
                                }
                            }

                        } catch (Exception num) {
                            JOptionPane.showMessageDialog(null, "Area de caja y ventas tienen que ser valores numericos");
                            areaCodigo.setText("");
                            areaNombre.setText("");
                            areaCorreo.setText("");
                            areaCaja.setText("");
                            areaVentas.setText("");
                            areaGenero.setText("");
                            areaPsw.setText("");
                        }
                    }
                });
                frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Codigo no registrado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "El valor del codigo tiene que ser un numero");
        }
    }

    public void crear_ClienteVendedor() {
        JFrame frame = inicializar_extras(300, 400, "Crear Cliente");
        JPanel panel = defPanel(frame);
        defEtiqueta(panel, 50, 10, 200, 30, "Crear Nuevo Cliente", 20);
        defEtiqueta(panel, 30, 60, 100, 25, "Código");
        JTextField areaCodigo = defAreaTexto(panel, 100, 60, 150, 25);
        defEtiqueta(panel, 30, 100, 100, 25, "Nombre");
        JTextField areaNombre = defAreaTexto(panel, 100, 100, 150, 25);
        defEtiqueta(panel, 30, 140, 100, 25, "NIT");
        JTextField areaNit = defAreaTexto(panel, 100, 140, 150, 25);
        defEtiqueta(panel, 30, 180, 100, 25, "Correo");
        JTextField areaCorreo = defAreaTexto(panel, 100, 180, 150, 25);
        defEtiqueta(panel, 30, 220, 100, 25, "Genero");
        JTextField areaGenero = defAreaTexto(panel, 100, 220, 150, 25);
        JButton agregar = defBoton(panel, 100, 270, 100, 30, "Agregar");
        agregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo = Integer.parseInt(areaCodigo.getText());
                    String nombre = areaNombre.getText();
                    String nit = areaNit.getText();
                    String correo = areaCorreo.getText();
                    char genero = areaGenero.getText().charAt(0);
                    accion.agregar_cliente(codigo, nombre, nit, correo, genero, clientes);
                    jcomboCliente.addItem(nombre);
                    frame.dispose();
                } catch (Exception num) {
                    JOptionPane.showMessageDialog(null, num);
                    areaCodigo.setText("");
                    areaNombre.setText("");
                    areaNit.setText("");
                    areaCorreo.setText("");
                    areaGenero.setText("");
                }
            }
        });
        frame.setVisible(true);
    }
}
