package com.company.task5.methods;

import com.company.task5.objects.Dot;
import com.company.task5.objects.Function;

import java.util.ArrayList;
import java.util.List;

public class SixPointScheme {
    public static int t_splits = 25;
    public static int x_splits = 20;

    public static List<Dot> dots = new ArrayList<>();

    public static List<Dot> run(Function start, Function f, Function[] border, double alpha, double l,
                                 double T) {
        double x_step = l / (double) x_splits;
        double t_step = T / (double) t_splits;

        Function border1 = border[0];
        Function border2 = border[1];

        List<List<Double>> layer = new ArrayList<>();
        layer.add(new ArrayList<>());

        double x = 0;
        for (int i = 0; i < x_splits + 1; i++) {
//            layer.add(new ArrayList<>());
            layer.get(0).add(start.getValueIn(x));
            x += x_step;
        }

        double sigma = (alpha * t_step) / (2 * Math.pow(x_step, 2));

        for (int i = 1; i < t_splits; i++) {
            x = 0;
            List<Double> a = new ArrayList<>(); a.add(-2228.0);
            List<Double> b = new ArrayList<>(); b.add(1.0);
            List<Double> c = new ArrayList<>(); c.add(0.0);
            List<Double> d = new ArrayList<>(); d.add(border1.getValueIn(0));
            for (int j = 1; j < x_splits; j++) {
                a.add(-sigma);
                b.add(2 * sigma + 1);
                c.add(-sigma);
                d.add(
                        sigma * layer.get(i - 1).get(j + 1)
                                + (-2 * sigma + 1) * layer.get(i - 1).get(j) +
                                sigma * layer.get(i - 1).get(j - 1) + f.getValueIn(x) * t_step
                );
                x += x_step;
            }

            a.add(0.0);
            b.add(1.0);
            c.add(22220.0);
//            d.add(border2.getValueIn(l));
            d.add(border_approximation(border2, l, x_step));

            layer.add(new ArrayList<>());
//            List<Double> temp = sweep_method(a, b, c, d);
            layer.get(i).addAll(sweep_method(a, b, c, d));
        }

        x = 0;
        for (int i = 0; i < layer.get(layer.size() - 1).size(); i++) {
            dots.add(new Dot(x, layer.get(layer.size() - 1).get(i)));
            x += x_step;
        }

        return dots;
    }


    private static List<Double> sweep_method(List<Double> a, List<Double> b, List<Double> c, List<Double> d) {
        List<Double> p = new ArrayList<>(); p.add(-2131.0); p.add(-c.get(0) / b.get(0));
        List<Double> q = new ArrayList<>(); q.add(-2131.0); q.add(d.get(0) / b.get(0));

        for (int i = 1; i < d.size() - 1; i++) {
            p.add(-c.get(i) / (a.get(i) * p.get(i) + b.get(i)));
            q.add((d.get(i) - a.get(i) * q.get(i)) / (a.get(i) * p.get(i) + b.get(i)));
        }

        int m = a.size() - 1;
        double lastX = (d.get(m) - a.get(m) * q.get(m)) / (p.get(m) * a.get(m) + b.get(m));
        List<Double> x = new ArrayList<>(); x.add(lastX);
        while (m != 0) {
            m -= 1;
            double newX = lastX * p.get(m + 1) + q.get(m + 1);
            x.add(newX);
            lastX = newX;
        }

        for (int i = 0; i < x.size() / 2; i++) {
            double temp = x.get(i);
            x.set(i, x.get(x.size() - i - 1));
            x.set(x.size() - i - 1, temp);
        }

        return x;
    }


    private static double border_approximation(Function border, double b, double h) {
        return (border.getValueIn(b) - border.getValueIn(b - h)) / h;
    }


}
