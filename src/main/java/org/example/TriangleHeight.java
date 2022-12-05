package org.example;

import java.util.Scanner;

public class TriangleHeight {
    public enum TRIANGLE_SIDE {
        A, B, C
    }

    private static double sideA;
    private static double sideB;
    private static double sideC;
    private static double heightToA;
    private static double heightToB;
    private static double heightToC;

    public static void main(String[] args) {
        readTriangleSidesFromInput();
        try {
            validateTriangleSides();
            calculateHeights();
            printHeights();
        } catch (TriangleStateException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void readTriangleSidesFromInput() {
        Scanner inputScanner = new Scanner(System.in);
        sideA = readTriangleSide("Введите длину стороны A: ", inputScanner);
        sideB = readTriangleSide("Введите длину стороны B: ", inputScanner);
        sideC = readTriangleSide("Введите длину стороны C: ", inputScanner);
    }

    public static double readTriangleSide(String inputPrompt, Scanner inputScanner) {
        System.out.print(inputPrompt);
        return inputScanner.nextDouble();
    }

    private static void validateTriangleSides() throws TriangleStateException {
        validateTriangleSides(sideA, sideB, sideC);
    }

    public static void validateTriangleSides(double sideA, double sideB, double sideC) throws TriangleStateException {
        if (isAnySideLessOrEqualsZero(sideA, sideB, sideC)) {
            throw new TriangleStateException("Длина стороны должна быть больше нуля");
        }

        if (isAnySideGreaterOrEqualsSumOfOthers(sideA, sideB, sideC)) {
            throw new TriangleStateException("Сумма любых двух сторон треугольника должна быть больше третьей");
        }
    }

    private static boolean isAnySideLessOrEqualsZero(double sideA, double sideB, double sideC) {
        return (sideA <= 0) || (sideB <= 0) || (sideC <= 0);
    }

    private static boolean isAnySideGreaterOrEqualsSumOfOthers(double sideA, double sideB, double sideC) {
        return (sideC >= sideA + sideB) || (sideB >= sideA + sideC) || (sideA >= sideB + sideC);
    }

    private static void calculateHeights() {
        double heightFormulaNumerator = calculateHeightFormulaNumerator(sideA, sideB, sideC);
        heightToA = calculateHeightToSide(heightFormulaNumerator, sideA, sideB, sideC, TRIANGLE_SIDE.A);
        heightToB = calculateHeightToSide(heightFormulaNumerator, sideA, sideB, sideC, TRIANGLE_SIDE.B);
        heightToC = calculateHeightToSide(heightFormulaNumerator, sideA, sideB, sideC, TRIANGLE_SIDE.C);
    }

    public static double calculateHeightFormulaNumerator(double sideA, double sideB, double sideC) {
        double halfPerimeter = calculateTrianglePerimeter(sideA, sideB, sideC) / 2;
        return 2 * Math.sqrt(
                halfPerimeter * (halfPerimeter - sideA) * (halfPerimeter - sideB) * (halfPerimeter - sideC)
        );
    }

    private static double calculateTrianglePerimeter(double sideA, double sideB, double sideC) {
        return sideA + sideB + sideC;
    }

    public static double calculateHeightToSide(
            double heightFormulaNumerator,
            double sideA, double sideB, double sideC, TRIANGLE_SIDE side) {
        double sideTarget = switch (side) {
            case A -> sideA;
            case B -> sideB;
            case C -> sideC;
        };
        return heightFormulaNumerator / sideTarget;
    }

    private static void printHeights() {
        printHeight(TRIANGLE_SIDE.A, heightToA);
        printHeight(TRIANGLE_SIDE.B, heightToB);
        printHeight(TRIANGLE_SIDE.C, heightToC);
    }

    public static void printHeight(TRIANGLE_SIDE side, double height) {
        System.out.printf("H(%s): %.2f\n", side, height);
    }
}