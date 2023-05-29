package com.company.task4.Frame;

import com.company.task4.objects.Dot;
import com.company.task4.objects.Function;
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

import static com.company.task4.Main.Program.a;
import static com.company.task4.Main.Program.b;

public class DrawFrame {

    public static void draw(List<Dot> dots, List<Dot> dots1, Function ansFunc) {
        XYSeries series = new XYSeries("Численное решение");
        XYSeries series2 = new XYSeries("Точное решение");

        double max_discrepancy_2h = -1;
        for (int i = 0; i < dots.size(); i++) {
            if (dots.get(i).x <= b)
                series.add(
                        dots.get(i).x,
                        dots.get(i).y
                );
            if (Math.abs(ansFunc.getValueIn(dots.get(i).x) - dots.get(i).y) > max_discrepancy_2h) {
                max_discrepancy_2h = Math.abs(ansFunc.getValueIn(dots.get(i).x) - dots.get(i).y);
            }
        }

        double max_discrepancy_h = -1;
        for (int i = 0; i < dots1.size(); i++) {
            if (Math.abs(ansFunc.getValueIn(dots1.get(i).x) - dots1.get(i).y) > max_discrepancy_h) {
                max_discrepancy_h = Math.abs(ansFunc.getValueIn(dots1.get(i).x) - dots1.get(i).y);
            }
        }

        System.out.println(max_discrepancy_h / max_discrepancy_2h);

        for (double i = a; i <= b + 0.01; i+=0.01) {
            series2.add(i, ansFunc.getValueIn(i));
        }

        XYSeriesCollection xyDataset = new XYSeriesCollection(series);
        xyDataset.addSeries(series2);

        JFreeChart chart = ChartFactory
                .createXYLineChart("y = sin(x)", "x", "y",
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, true);

        chart.getXYPlot().getDomainAxis().setRange(a, b);

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
