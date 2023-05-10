package com.company.task4.methods;

import com.company.task4.objects.Dot;
import com.company.task4.objects.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShootingMethod {

    public static List<Dot> dots;

    public static double eps = 0.0001;

    public static List<Dot> run(Function[] func, double a, double b, double A, double B) {
        Random random = new Random();

//        double last_n = -100f;
        double last_n = random.nextDouble() * 10;
        List<Dot> last_y = AdamsMethod.run(func, 2, a, b, new double[] {A, last_n});

//        double n = 100f;
        double n = random.nextDouble() * 100;
        List<Dot> y = AdamsMethod.run(func, 2, a, b, new double[] {A, n});

        while ((get_b_el(last_y, a, b).y - b) * (get_b_el(y, a, b).y - b) > 0) {
            n = random.nextDouble() * 100 * Math.pow(-1, (int)n);
            y = AdamsMethod.run(func, 2, a, b, new double[] {A, n});
        }

        double mid_n = (n + last_n) / 2;
        List<Dot> mid_y = AdamsMethod.run(func, 2, a, b, new double[] {A, mid_n});

        while (Math.abs(get_b_el(mid_y, a, b).y - B) > eps) {
            if ((get_b_el(mid_y, a, b).y - B) * (get_b_el(last_y, a, b).y - B) <= 0) {
                n = mid_n;
                y = mid_y;
            }
            else {
                last_n = mid_n;
                last_y = mid_y;
            }

            mid_n = (n + last_n) / 2;
            mid_y = AdamsMethod.run(func, 2, a, b, new double[] {A, mid_n});
        }

        dots = mid_y;
        return dots;
    }

    private static Dot get_b_el(List<Dot> ys, double a, double b) {
        Dot close_el = new Dot(a, a);
        for (int i = 0; i < ys.size(); i++) {
            if (Math.abs(ys.get(i).x - b) < Math.abs(close_el.x - b)) {
                close_el = ys.get(i);
            }
        }
        return close_el;
    }
}
