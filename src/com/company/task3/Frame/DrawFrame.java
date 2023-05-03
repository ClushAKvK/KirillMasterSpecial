package com.company.task3.Frame;

import com.company.task3.objects.Dot;
import com.company.task3.objects.Function;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import static com.company.task3.Main.Program.A;
import static com.company.task3.Main.Program.B;

public class DrawFrame {

    public static void draw(int system_count, List<List<Dot>> dots, Function [] ansFunc) {
        XYSeries [] series = new XYSeries[system_count];
        XYSeries [] series2 = new XYSeries[system_count];

        XYSeriesCollection xyDataset = new XYSeriesCollection();

        for (int i = 0; i < system_count; i++) {
            series[i] = new XYSeries("Функция " + (i + 1));
            for (int j = 0; j < dots.get(i).size(); j++) {
                series[i].add(
                        dots.get(i).get(j).x,
                        dots.get(i).get(j).y
                );
            }
            xyDataset.addSeries(series[i]);
        }
//        for (int i = 0; i < dots.size(); i++) {
//            series.add(
//                    dots.get(i).x,
//                    dots.get(i).y
//            );
//        }

        for (int j = 0; j < system_count; j++) {
            series2[j] = new XYSeries("Точное решение " + (j + 1));
            for (double i = A; i < B; i+=0.1) {
                series2[j].add(i, ansFunc[j].getValueIn(i));
            }
//            xyDataset.addSeries(series2[j]);
        }

//        XYSeriesCollection xyDataset = new XYSeriesCollection();

//        xyDataset.addSeries(series2);

        JFreeChart chart = ChartFactory
                .createXYLineChart("y = sin(x)", "x", "y",
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, true);

        chart.getXYPlot().getDomainAxis().setRange(A, B);

        XYLineAndShapeRenderer renderer =
                (XYLineAndShapeRenderer) chart.getXYPlot().getRenderer();
        renderer.setSeriesShapesVisible(1, true);



        JFrame frame = new JFrame("MinimalStaticChart");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
                System.exit(0);
            }

        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 27) {
                    frame.dispose();
                    System.exit(0);
                }
            }
        });

        // Помещаем график на фрейм
        frame.getContentPane().add(new ChartPanel(chart));
        frame.setSize(800,600);
        frame.show();
    }

}
