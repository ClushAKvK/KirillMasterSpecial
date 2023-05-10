package com.company.task4.Main;

import com.company.task4.Frame.DrawFrame;
import com.company.task4.methods.AdamsMethod;
import com.company.task4.methods.ShootingMethod;
import com.company.task4.objects.Dot;
import com.company.task4.objects.Function;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Program {

    public static double a;

    public static double b;

    public static double A;

    public static double B;

    public static double [] start;

    public static int system_count = 2;

    public static String expression;

    public static Function[] functions;

    public static Function ansFunction;

    private void input() throws FileNotFoundException {
        String path = Path.of("").toAbsolutePath() + "\\resources\\task4\\input3.txt";
        File file = new File(path);
        Scanner sc = new Scanner(file);
        sc.useLocale(Locale.UK);

        a = sc.nextDouble();
        b = sc.nextDouble();

        A = sc.nextDouble();
        B = sc.nextDouble();

        sc.nextLine();

        functions = new Function[system_count];
        for (int i = 0; i < system_count; i++) {
            expression = sc.nextLine();
            functions[i] = new Function(expression);
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

        List<Dot> dots = ShootingMethod.run(functions, a, b, A, B);
        DrawFrame.draw(dots, ansFunction);
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
