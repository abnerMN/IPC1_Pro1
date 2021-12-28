import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class GenerarPdf {
    public void generarPDFSucursal(Sucursal[] sucursales) {
        Document documento = new Document();

        try {
            String ruta = "reportes/sucursales.pdf";
            PdfWriter.getInstance(documento, new FileOutputStream(ruta));

            Font fuente_titulo = new Font();
            fuente_titulo.setSize(25);
            fuente_titulo.setColor(BaseColor.BLUE);
            fuente_titulo.setFamily("Bauhaus 93");

            Font fuente_sub = new Font();
            fuente_sub.setSize(10);
            fuente_sub.setColor(BaseColor.BLACK);
            fuente_sub.setFamily("COURIER");

            Paragraph titulo = new Paragraph();
            titulo.setAlignment(Paragraph.ALIGN_CENTER);

            documento.open();

            documento.add(new Paragraph("Blue Mall", fuente_titulo));
            documento.add(new Paragraph("Reporte Sucursales Registradas\n\n", fuente_sub));

            PdfPTable tabla = new PdfPTable(5);
            tabla.addCell("Codigo");
            tabla.addCell("Nombre");
            tabla.addCell("Dirección");
            tabla.addCell("Correo");
            tabla.addCell("Teléfono");

            try {
                for (int i = 0; i < sucursales.length; i++) {
                    if (sucursales[i] != null) {
                        tabla.addCell(String.valueOf(sucursales[i].getCodigo()));
                        tabla.addCell(sucursales[i].getNombre());
                        tabla.addCell(sucursales[i].getDireccion());
                        tabla.addCell(sucursales[i].getCorreo());
                        tabla.addCell(String.valueOf(sucursales[i].getTelefono()));
                    } else {
                        break;
                    }
                }
                documento.add(tabla);
            } catch (DocumentException e) {
            }
            documento.close();
            JOptionPane.showMessageDialog(null, "Reporte Creado");
        } catch (DocumentException | HeadlessException | FileNotFoundException e) {
        }
    }

    public void generarPDFProducto(Producto[] productos) {
        Document documento = new Document();

        try {
            String ruta = "reportes/productos.pdf";
            PdfWriter.getInstance(documento, new FileOutputStream(ruta));

            Font fuente_titulo = new Font();
            fuente_titulo.setSize(25);
            fuente_titulo.setColor(BaseColor.BLUE);
            fuente_titulo.setFamily("Bauhaus 93");

            Font fuente_sub = new Font();
            fuente_sub.setSize(10);
            fuente_sub.setColor(BaseColor.BLACK);
            fuente_sub.setFamily("COURIER");

            Paragraph titulo = new Paragraph();
            titulo.setAlignment(Paragraph.ALIGN_CENTER);

            documento.open();

            documento.add(new Paragraph("Blue Mall", fuente_titulo));
            documento.add(new Paragraph("Reporte Productos Registrados\n\n", fuente_sub));

            PdfPTable tabla = new PdfPTable(5);
            tabla.addCell("Codigo");
            tabla.addCell("Nombre");
            tabla.addCell("Descripción");
            tabla.addCell("Cantidad");
            tabla.addCell("Precio");

            try {
                for (int i = 0; i < productos.length; i++) {
                    if (productos[i] != null) {
                        tabla.addCell(String.valueOf(productos[i].getCodigo()));
                        tabla.addCell(productos[i].getNombre());
                        tabla.addCell(productos[i].getDescripcion());
                        tabla.addCell(String.valueOf(productos[i].getCantidad()));
                        tabla.addCell(String.valueOf(productos[i].getPrecio()));
                    } else {
                        break;
                    }
                }
                documento.add(tabla);
            } catch (DocumentException e) {
            }
            documento.close();
            JOptionPane.showMessageDialog(null, "Reporte Creado");
        } catch (DocumentException | HeadlessException | FileNotFoundException e) {
        }
    }

    public void generarPDFCliente(Cliente[] clientes) {
        Document documento = new Document();

        try {
            String ruta = "reportes/clientes.pdf";
            PdfWriter.getInstance(documento, new FileOutputStream(ruta));

            Font fuente_titulo = new Font();
            fuente_titulo.setSize(25);
            fuente_titulo.setColor(BaseColor.BLUE);
            fuente_titulo.setFamily("Bauhaus 93");

            Font fuente_sub = new Font();
            fuente_sub.setSize(10);
            fuente_sub.setColor(BaseColor.BLACK);
            fuente_sub.setFamily("COURIER");

            Paragraph titulo = new Paragraph();
            titulo.setAlignment(Paragraph.ALIGN_CENTER);

            documento.open();

            documento.add(new Paragraph("Blue Mall", fuente_titulo));
            documento.add(new Paragraph("Reporte Clientes Registrados\n\n", fuente_sub));

            PdfPTable tabla = new PdfPTable(5);
            tabla.addCell("Codigo");
            tabla.addCell("Nombre");
            tabla.addCell("NIT");
            tabla.addCell("Correo");
            tabla.addCell("Genero");

            try {
                for (int i = 0; i < clientes.length; i++) {
                    if (clientes[i] != null) {
                        tabla.addCell(String.valueOf(clientes[i].getCodigo()));
                        tabla.addCell(clientes[i].getNombre());
                        tabla.addCell(String.valueOf(clientes[i].getNit()));
                        tabla.addCell(clientes[i].getCorreo());
                        tabla.addCell(String.valueOf(clientes[i].getGenero()));
                    } else {
                        break;
                    }
                }
                documento.add(tabla);
            } catch (DocumentException e) {
            }
            documento.close();
            JOptionPane.showMessageDialog(null, "Reporte Creado");
        } catch (DocumentException | HeadlessException | FileNotFoundException e) {
        }
    }

    public void generarPDFVendedor(Vendedor[] vendedores) {
        Document documento = new Document();

        try {
            String ruta = "reportes/vendedores.pdf";
            PdfWriter.getInstance(documento, new FileOutputStream(ruta));

            Font fuente_titulo = new Font();
            fuente_titulo.setSize(25);
            fuente_titulo.setColor(BaseColor.BLUE);
            fuente_titulo.setFamily("Bauhaus 93");

            Font fuente_sub = new Font();
            fuente_sub.setSize(10);
            fuente_sub.setColor(BaseColor.BLACK);
            fuente_sub.setFamily("COURIER");

            Paragraph titulo = new Paragraph();
            titulo.setAlignment(Paragraph.ALIGN_CENTER);

            documento.open();

            documento.add(new Paragraph("Blue Mall", fuente_titulo));
            documento.add(new Paragraph("Reporte Vendedores Registrados\n\n", fuente_sub));

            PdfPTable tabla = new PdfPTable(6);
            tabla.addCell("Codigo");
            tabla.addCell("Nombre");
            tabla.addCell("Correo");
            tabla.addCell("Caja");
            tabla.addCell("Ventas");
            tabla.addCell("Genero");

            try {
                for (int i = 1; i < vendedores.length; i++) {
                    if (vendedores[i] != null) {
                        tabla.addCell(String.valueOf(vendedores[i].getCodigo()));
                        tabla.addCell(vendedores[i].getNombre());
                        tabla.addCell(vendedores[i].getCorreo());
                        tabla.addCell(String.valueOf(vendedores[i].getCaja()));
                        tabla.addCell(String.valueOf(vendedores[i].getVentas()));
                        tabla.addCell(String.valueOf(vendedores[i].getGenero()));
                    } else {
                        break;
                    }
                }
                documento.add(tabla);
            } catch (DocumentException e) {
            }
            documento.close();
            JOptionPane.showMessageDialog(null, "Reporte Creado");
        } catch (DocumentException | HeadlessException | FileNotFoundException e) {
        }
    }

    public void generarPDFFactura(Factura factura) {
        Document documento = new Document();

        try {
            int caja = factura.getCaja();
            int total = factura.getTotal();
            int no_factura = factura.getNumeroFac();
            String nit = factura.getNit();
            String nombre = factura.getNombre_cliente();
            String fecha = factura.getFecha();
            Compra[] compras = factura.getCompras();
            String nombre_archivo = "facturas/" + no_factura + ".pdf";
            String ruta = nombre_archivo;
            PdfWriter.getInstance(documento, new FileOutputStream(ruta));

            Font fuente_titulo = new Font();
            fuente_titulo.setSize(25);
            fuente_titulo.setColor(BaseColor.BLUE);
            fuente_titulo.setFamily("Bauhaus 93");

            Font fuente_sub = new Font();
            fuente_sub.setSize(10);
            fuente_sub.setColor(BaseColor.BLACK);
            fuente_sub.setFamily("COURIER");

            Paragraph num_factura = _izquierda("No. Factura: "+ no_factura);
            Paragraph fecha_factura=_izquierda("Fecha: " + fecha);
            Paragraph nombre_factura=_izquierda("Nombre: " + nombre);
            Paragraph nit_factura=_izquierda("Nit: " + nit);
            Paragraph caja_factura=_izquierda("Fue atendido por No. Caja: " + caja+"\n\n");
            Paragraph total_factura= _derecha("Total: "+total);

            documento.open();

            documento.add(new Paragraph("Blue Mall", fuente_titulo));
            documento.add(new Paragraph("Siempre Cerca de ti\n\n", fuente_sub));
            documento.add(num_factura);
            documento.add(fecha_factura);
            documento.add(nombre_factura);
            documento.add(nit_factura);
            documento.add(caja_factura);

            PdfPTable tabla = new PdfPTable(5);
            tabla.addCell("Codigo");
            tabla.addCell("Nombre");
            tabla.addCell("Cantidad");
            tabla.addCell("Precio");
            tabla.addCell("Subtotal");

            try {
                for (int i = 0; i < compras.length; i++) {
                    if (compras[i] != null) {
                        tabla.addCell(String.valueOf(compras[i].getCodigo_producto()));
                        tabla.addCell(compras[i].getNombre_producto());
                        tabla.addCell(String.valueOf(compras[i].getCantidad()));
                        tabla.addCell(String.valueOf(compras[i].getPrecio()));
                        tabla.addCell(String.valueOf(compras[i].getSubtotal()));
                    } else {
                        break;
                    }
                }
                documento.add(tabla);
            } catch (DocumentException e) {
            }
            documento.add(total_factura);

            documento.close();
            JOptionPane.showMessageDialog(null, "Reporte Creado");
        } catch (DocumentException | HeadlessException | FileNotFoundException e) {
        }
    }

    public Paragraph _izquierda(String texto) {
        Paragraph izquierda = new Paragraph(texto);
        izquierda.setAlignment(Paragraph.ALIGN_LEFT);
        return izquierda;
    }

    public Paragraph _derecha(String texto) {
        Paragraph derecha = new Paragraph(texto);
        derecha.setAlignment(Paragraph.ALIGN_RIGHT);
        return derecha;
    }

    public Paragraph centro (String texto) {
        Paragraph centro = new Paragraph(texto);
        centro.setAlignment(Paragraph.ALIGN_CENTER);
        return centro;
    }
}
