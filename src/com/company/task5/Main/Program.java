package com.company.task5.Main;

import com.company.task5.Frame.DrawFrame;
import com.company.task5.methods.SixPointScheme;
import com.company.task5.objects.Dot;
import com.company.task5.objects.Function;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Program {

    public static double thermal_conductivity;

    public static double l;

    public static double T;

    public static Function f;

    public static Function start;

    public static Function[] border;

    public static String expression;

    public static Function ansFunction;

    private void input() throws FileNotFoundException {
        String path = Path.of("").toAbsolutePath() + "\\resources\\task5\\input2.txt";
        File file = new File(path);
        Scanner sc = new Scanner(file);
        sc.useLocale(Locale.UK);

        thermal_conductivity = sc.nextDouble();
        l = sc.nextDouble();
        T = sc.nextDouble();

        sc.nextLine();

        expression = sc.nextLine();
        f = new Function(expression);

        expression = sc.nextLine();
        start = new Function(expression);

        border = new Function[2];
        for (int i = 0; i < 2; i++) {
            expression = sc.nextLine();
            border[i] = new Function(expression);
        }

        expression = sc.nextLine();
        ansFunction = new Function(expression);
    }

    public void start() {
        try {
            input();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        List<Dot> dots = SixPointScheme.run(start, f, border, thermal_conductivity, l, T);

        SixPointScheme.x_splits = 40;
        SixPointScheme.t_splits = 45;
        SixPointScheme.dots = new ArrayList<>();
        List<Dot> dots1 = SixPointScheme.run(start, f, border, thermal_conductivity, l, T);

        DrawFrame.draw(dots1, dots, ansFunction, T);
//        List<Dot> dots = ShootingMethod.run(functions, a, b, A, B);
//        DrawFrame.draw(dots, ansFunction);
//        List<List<Dot>> dots = AdamsMethod.run(functions, system_count, A, B, start);
//        DrawFrame.draw(system_count, dots, ansFunctions);

//        for (List<Dot> listDots : dots) {
//            for (Dot dot : listDots) {
//                System.out.println(dot.x + " " + dot.y);
//            }
//            System.out.println();
//        }
//        List<Dot> dots = AdamsMethod.run(function, A, B, y0);
//
//        double discrepancy = AdamsMethod.calcDiscrepancy(function, ansFunction, dots);
//
//        System.out.println("Погрешность: " + discrepancy);

//        for (int i = 0; i < dots.size(); i++) {
//            System.out.println(dots.get(i).x + " " + dots.get(i).y);
//        }

//        DrawFrame.draw(dots, ansFunction);
    }

}
