package com.company.task2.Frame;

import com.company.task2.objects.Dot;
import com.company.task2.objects.Function;
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

import static com.company.task2.Main.Program.A;
import static com.company.task2.Main.Program.B;

public class DrawFrame {

    public static void draw(List<Dot> dots, Function ansFunc) {
        XYSeries series = new XYSeries("Численное решение");
        XYSeries series2 = new XYSeries("Точное решение");

        for (int i = 0; i < dots.size(); i++) {
            series.add(
                    dots.get(i).x,
                    dots.get(i).y
            );
        }

        for (double i = A; i < B; i+=0.1) {
            series2.add(i, ansFunc.getValueIn(i));
        }

        XYSeriesCollection xyDataset = new XYSeriesCollection(series);
        xyDataset.addSeries(series2);

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
