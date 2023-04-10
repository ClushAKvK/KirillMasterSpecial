package com.company.methods;

import com.company.objects.Dot;
import com.company.objects.Function;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.company.Main.Program.B;
import static java.lang.Math.*;

public class AdamsMethod {

    private final static double splits = 50;

    public static List<Dot> dots = new ArrayList<>();

    private static List<Double> getRungeKutBegin(Function func, double h, double y0, double A) {
        List<Double> beginY = new ArrayList<>();

        double y = y0;
        double x = A;

        beginY.add(y);
        dots.add(new Dot(x, y));
        while (beginY.size() < 4) {
            x += h;

            double k1 = func.getValueIn(x, y);
            double k2 = func.getValueIn(x + 0.5 * h, y + 0.5 * h * k1);
            double k3 = func.getValueIn(x + 0.5 * h, y + 0.5 * h * k2);
            double k4 = func.getValueIn(x + h, y + h * k3);

            y = y + (h / 6) * (k1 + 2 * k2 + 2 * k3 + k4);
            beginY.add(y);
            dots.add(new Dot(x, y));
        }

        return beginY;
    }

    public static List<Dot> run(Function func, double A, double B, double y0) {
        double x = A;
        double h = abs((B - A) / splits);

        List<Double> lastY = getRungeKutBegin(func, h, y0, A);
        List<Double> lastF = new ArrayList<>();
        for (int i = 0; i < lastY.size(); i++) {
            lastF.add(func.getValueIn(x, lastY.get(i)));
            x += h;
        }

        while (x <= B) {
            double newY = lastY.get(3) + (h / 24) * (55 * lastF.get(3) - 59 * lastF.get(2) + 37 * lastF.get(1) - 9 * lastF.get(0));
            for (int i = 0; i < lastF.size() - 1; i++) {
                lastF.set(i, lastF.get(i + 1));
            }
            lastF.set(3, func.getValueIn(x, newY));

            for (int i = 0; i < lastY.size() - 1; i++) {
                lastY.set(i, lastY.get(i + 1));
            }
            lastY.set(3, newY);

            dots.add(new Dot(x, newY));

            x += h;
        }

        return dots;

    }

    public static Double calcDiscrepancy(Function func1, Function func2, List<Dot> dots) {
        double discrepancy = -1;

        for (Dot dot : dots) {
            double ansY = func2.getValueIn(dot.x);
            double dist = sqrt(
                    pow(ansY - dot.y, 2)
            );

            if (dist > discrepancy)
                discrepancy = dist;
        }

        return discrepancy;
    }

}
