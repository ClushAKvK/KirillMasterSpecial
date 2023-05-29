package com.company.task4.methods;

import com.company.task4.objects.Dot;
import com.company.task4.objects.Function;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class AdamsMethod {

    public static double splits = 30;

    public static List<List<Dot>> dots;

    private static List<List<Double>> getRungeKutBegin(Function[] func, int system_count, double h, double [] start, double A) {
        dots = new ArrayList<>();
        List<List<Double>> begin_y = new ArrayList<>();

        for (int i = 0; i < system_count; i++) {
            begin_y.add(new ArrayList<>());
            dots.add(new ArrayList<>());
        }

        double [] y = start;

        double t = A;

        for (int i = 0; i < system_count; i++) {
            begin_y.get(i).add(y[i]);
            dots.get(i).add(new Dot(t, y[i]));
        }

        while (begin_y.get(0).size() < 5) {
            t += h;

            double [] newY = new double[system_count];
            for (int i = 0; i < system_count; i++) {
                Function tempF = func[i];


                double k1 = 1/3f * h * tempF.getValueIn(t, y);

                double [] params = new double[system_count];

                for (int j = 0; j < system_count; j++)
                    params[j] = y[j] + k1;
                double k2 = 1/3f * h * tempF.getValueIn(t + 1/3f * h, params);

                for (int j = 0; j < system_count; j++)
                    params[j] = y[j] + 1/2f * k1 + 1/2f * k2;
                double k3 = 1/3f * h * tempF.getValueIn(t + 1/3f * h, params);

                for (int j = 0; j < system_count; j++)
                    params[j] = y[j] + 3/8f * k1 + 9/8f * k3;
                double k4 = 1/3f * h * tempF.getValueIn(t + 1/2f * h, params);

                for (int j = 0; j < system_count; j++)
                    params[j] = y[j] + 3/2f * k1 - 9/2f * k3 + 6 * k4;
                double k5 = 1/3f * h * tempF.getValueIn(t + h, params);

                double ny = y[i] + 1/2f * (k1 + 4 * k4 + k5);

                newY[i] = ny;
                begin_y.get(i).add(ny);
                dots.get(i).add(new Dot(t, ny));
            }
            y = newY;
        }

        return begin_y;
    }

    public static List<Dot> run(Function[] func, int system_count, double A, double B, double [] start) {
        double t = A;
        double h = abs((B - A) / splits);

        List<List<Double>> by = getRungeKutBegin(func, system_count, h, start, A);

        List<List<Double>> ly = new ArrayList<>(system_count);
        for (int i = 0; i < 5; i++) {
            ly.add(new ArrayList<>());
            for (int j = 0; j < system_count; j++) {
                ly.get(i).add(by.get(j).get(i));
            }
        }

        List<Double> last4Y = ly.get(0), last3Y = ly.get(1), last2Y = ly.get(2), lastY = ly.get(3), y = ly.get(4);
        List<Double> last4F  = new ArrayList<>(), last3F  = new ArrayList<>(), last2F  = new ArrayList<>(), lastF  = new ArrayList<>(), f = new ArrayList<>();
        for (int i = 0; i < system_count; i++) {
            Function tempF = func[i];
            last4F.add(tempF.getValueIn(t, last4Y));
            last3F.add(tempF.getValueIn(t + h, last3Y));
            last2F.add(tempF.getValueIn(t + h * 2f, last2Y));
            lastF.add(tempF.getValueIn(t + h * 3f, lastY));
            f.add(tempF.getValueIn(t + h * 4f, y));
        }

        List<Double> newY = new ArrayList<>(system_count);
        List<Double> newF = new ArrayList<>(system_count);

        t += h * 5f;
        while (t <= B + 0.1) {
            for (int i = 0; i < system_count; i++) {
                double ny = y.get(i) + (h / 720f) * (1901f * f.get(i) - 2774f * lastF.get(i) + 2616f * last2F.get(i) - 1274f * last3F.get(i) + 251f * last4F.get(i));
                newY.add(ny);
                dots.get(i).add(new Dot(t, ny));
            }

            for (int i = 0; i < system_count; i++) {
                Function tempF = func[i];
                newF.add(tempF.getValueIn(t, newY));
            }

            last4F = last3F;
            last3F = last2F;
            last2F = lastF;
            lastF = f;
            f = newF;

            last4Y = last3Y;
            last3Y = last2Y;
            last2Y = lastY;
            lastY = y;
            y = newY;

            t += h;
            newY = new ArrayList<>(system_count);
            newF = new ArrayList<>(system_count);
        }

        return dots.get(0);
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
