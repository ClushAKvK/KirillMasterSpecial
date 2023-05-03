package com.company.task3.Main;

import com.company.task3.Frame.DrawFrame;
import com.company.task3.methods.AdamsMethod;
import com.company.task3.objects.Dot;
import com.company.task3.objects.Function;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Program {

    public static double A;

    public static double B;

    public static double [] start;

    public static int system_count;

    public static String expression;

    public static Function [] functions;

    public static Function [] ansFunctions;

    private void input() throws FileNotFoundException {
        String path = Path.of("").toAbsolutePath() + "\\resources\\task3\\input3.txt";
        File file = new File(path);
        Scanner sc = new Scanner(file);
        sc.useLocale(Locale.UK);

        A = sc.nextDouble();
        B = sc.nextDouble();
        system_count = sc.nextInt();

        start = new double[system_count];
        for (int i = 0; i < system_count; i++)
            start[i] = sc.nextDouble();

        sc.nextLine();


        functions = new Function[system_count];
        for (int i = 0; i < system_count; i++) {
            expression = sc.nextLine();
            functions[i] = new Function(expression);
        }

        ansFunctions = new Function[system_count];
        for (int i = 0; i < system_count; i++) {
            expression = sc.nextLine();
            ansFunctions[i] = new Function(expression);
        }

    }

    public void start() {
        try {
            input();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<List<Dot>> dots = AdamsMethod.run(functions, system_count, A, B, start);
        DrawFrame.draw(system_count, dots, ansFunctions);

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
