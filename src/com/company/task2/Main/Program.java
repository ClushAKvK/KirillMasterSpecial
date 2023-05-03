package com.company.task2.Main;

import com.company.task2.Frame.DrawFrame;
import com.company.task2.methods.AdamsMethod;
import com.company.task2.objects.Dot;
import com.company.task2.objects.Function;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Program {

    public static double A;

    public static double B;

    public static double y0;

    public static String expression;

    public static Function function;

    public static Function ansFunction;

    private void input() throws FileNotFoundException {
        String path = Path.of("").toAbsolutePath() + "\\resources\\task2\\input3.txt";
        File file = new File(path);
        Scanner sc = new Scanner(file);
        sc.useLocale(Locale.UK);

        A = sc.nextDouble();
        B = sc.nextDouble();
        y0 = sc.nextDouble();

        sc.nextLine();

        expression = sc.nextLine();
        function = new Function(expression);

        expression = sc.nextLine();
        ansFunction = new Function(expression);
    }

    public void start() {
        try {
            input();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<Dot> dots = AdamsMethod.run(function, A, B, y0);

        double discrepancy = AdamsMethod.calcDiscrepancy(function, ansFunction, dots);

        System.out.println("Погрешность: " + discrepancy);

//        for (int i = 0; i < dots.size(); i++) {
//            System.out.println(dots.get(i).x + " " + dots.get(i).y);
//        }

        DrawFrame.draw(dots, ansFunction);
    }

}
