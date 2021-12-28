import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;

public class GenerarGrafica {
    //productos
    public void grafica_productos(Producto[] productos, JPanel panel_productos) {
        try {
            JLabel grafica_productos = new JLabel();
            grafica_productos.setBounds(525, 170, 220, 220);
            grafica_productos.setOpaque(true);
            grafica_productos.setVisible(true);
            grafica_productos.setBackground(Color.WHITE);
            panel_productos.add(grafica_productos);

            DefaultCategoryDataset datos = new DefaultCategoryDataset();
            Producto var[] = arg_temp(productos);
            for (int i = 0; i < 3; i++) {
                if (var[i] != null) {
                    datos.setValue(var[i].getCantidad(), "", var[i].getNombre());
                } else {
                    break;
                }
            }
            JFreeChart ct = ChartFactory.createBarChart3D("Top 3: Productos con \nmas disponibilidad", "", "",
                    datos, PlotOrientation.VERTICAL, false, true, false);
            ChartPanel panel_grafica = new ChartPanel(ct);
            panel_grafica.setMouseWheelEnabled(true);

            grafica_productos.setLayout(new java.awt.BorderLayout());
            grafica_productos.add(panel_grafica, BorderLayout.CENTER);
            grafica_productos.validate();
        }catch (Exception e){
        }
    }

    private Producto[] arg_temp(Producto[] productos) {
        int contador = 0;
        for (int i = 0; i < productos.length; i++) {
            if (productos[i] != null) {
                contador++;
            } else {
                break;
            }
        }
        Producto[] arg_temp = new Producto[contador];
        for (int i = 0; i < arg_temp.length; i++) {
            arg_temp[i] = productos[i];
        }

        //ordenando
        Producto aux = null;
        for (int i = 0; i < arg_temp.length - 1; i++) {
            for (int j = 0; j < arg_temp.length - i - 1; j++) {
                if (arg_temp[j + 1].getCantidad() > arg_temp[j].getCantidad()) {
                    aux = arg_temp[j + 1];
                    arg_temp[j + 1] = arg_temp[j];
                    arg_temp[j] = aux;
                }
            }
        }
        return arg_temp;
    }

    //vendedores
    public void grafica_vendedores(Vendedor[] vendedores, JPanel panel_vendedores) {
        try {
            JLabel grafica_vendedores = new JLabel();
            grafica_vendedores.setBounds(525, 170, 220, 220);
            grafica_vendedores.setOpaque(true);
            grafica_vendedores.setVisible(true);
            grafica_vendedores.setBackground(Color.WHITE);
            panel_vendedores.add(grafica_vendedores);

            DefaultCategoryDataset datos = new DefaultCategoryDataset();
            Vendedor var[] = arg_temp(vendedores);
            for (int i = 0; i < 3; i++) {
                if (var[i] != null) {
                    datos.setValue(var[i].getVentas(), "", var[i].getNombre());
                } else {
                    break;
                }
            }
            JFreeChart ct = ChartFactory.createBarChart3D("Top 3: Vendedores con \nmas ventas", "", "",
                    datos, PlotOrientation.VERTICAL, false, true, false);
            ChartPanel panel_grafica = new ChartPanel(ct);
            panel_grafica.setMouseWheelEnabled(true);

            grafica_vendedores.setLayout(new java.awt.BorderLayout());
            grafica_vendedores.add(panel_grafica, BorderLayout.CENTER);
            grafica_vendedores.validate();
        } catch (Exception e) {
        }
    }

    private Vendedor[] arg_temp(Vendedor[] vendedores) {
        int contador = 0;
        for (int i = 0; i < vendedores.length; i++) {
            if (vendedores[i] != null) {
                contador++;
            } else {
                break;
            }
        }
        Vendedor[] arg_temp = new Vendedor[contador];
        for (int i = 0; i < arg_temp.length; i++) {
            arg_temp[i] = vendedores[i];
        }

        //ordenando
        Vendedor aux = null;
        for (int i = 0; i < arg_temp.length - 1; i++) {
            for (int j = 0; j < arg_temp.length - i - 1; j++) {
                if (arg_temp[j + 1].getVentas() > arg_temp[j].getVentas()) {
                    aux = arg_temp[j + 1];
                    arg_temp[j + 1] = arg_temp[j];
                    arg_temp[j] = aux;
                }
            }
        }
        return arg_temp;
    }

    public void grafica_clientes(Cliente[] clientes, JPanel panel_clientes) {
        try {
            JLabel grafica_vendedores = new JLabel();
            grafica_vendedores.setBounds(525, 170, 220, 220);
            grafica_vendedores.setOpaque(true);
            grafica_vendedores.setVisible(true);
            grafica_vendedores.setBackground(Color.WHITE);
            panel_clientes.add(grafica_vendedores);

            int masculino = 0;
            int femenino = 0;
            for (int i = 0; i < clientes.length; i++) {
                if (clientes[i] != null) {
                    if (clientes[i].getGenero() == 'M') {
                        masculino++;
                    } else {
                        femenino++;
                    }
                } else {
                    break;
                }
            }

            DefaultPieDataset datos = new DefaultPieDataset();
            datos.setValue("Masculino", masculino);
            datos.setValue("Femenino", femenino);

            PieSectionLabelGenerator etiqueta = new StandardPieSectionLabelGenerator("{0} = {1}");
            JFreeChart jf = ChartFactory.createPieChart("Genero de clientes", datos, true, true, false);
            PiePlot pl = (PiePlot) jf.getPlot();
            pl.setLabelGenerator(etiqueta);

            ChartPanel panel_grafica = new ChartPanel(jf);
            panel_grafica.setMouseWheelEnabled(true);

            grafica_vendedores.setLayout(new java.awt.BorderLayout());
            grafica_vendedores.add(panel_grafica, BorderLayout.CENTER);
            grafica_vendedores.validate();

        }catch (Exception e){
        }
    }
}
