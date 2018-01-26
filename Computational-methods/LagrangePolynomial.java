package JAVASSU;

/*
    Realization Lagrange polynomial.

    Mathematical formula: Σ (-1)^k * ((9x)^2k+1 / (2k + 1)!));

*/

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class LagrangePolynomial {
    private static ArrayList<Double> numbersX = new ArrayList<>();
    private static ArrayList<Double> functionX = new ArrayList<>();
    private static ArrayList<Double> middleValuesOfPoint = new ArrayList<>();
    private static ArrayList<Double> intValuesOfPoint = new ArrayList<>();
    private static ArrayList<Double> middleValuesFunX = new ArrayList<>();
    private static ArrayList<Double> commonValuesFunX = new ArrayList<>();
    private static ArrayList<Double> testCommonFunValues = new ArrayList<>();
    private static ArrayList<Double> testMiddleFunValues = new ArrayList<>();
    private static ArrayList<Double> testMiddleValues = new ArrayList<>();
    private static ArrayList<Double> testCommonValues = new ArrayList<>();
    private static final float constant = 0.00001f;

    public static void main(String[] args) {
        double firstL, lastL, intervalL;
        int testFirstL, testLastL, testIntervalL;
        System.out.println("Value range:");

        System.out.print("from: ");
        Scanner firstLength = new Scanner(System.in);
        firstL = firstLength.nextDouble();

        System.out.print("until: ");
        Scanner lastLength = new Scanner(System.in);
        lastL = lastLength.nextDouble();

        System.out.print("Enter interval: ");
        Scanner intervalLength = new Scanner(System.in);
        intervalL = intervalLength.nextDouble();

        System.out.println("Enter values for testing:");

        System.out.print("from: ");
        Scanner testFirstLength = new Scanner(System.in);
        testFirstL = testFirstLength.nextInt();

        System.out.print("until: ");
        Scanner testLastLength = new Scanner(System.in);
        testLastL = testLastLength.nextInt();

        System.out.print("Enter interval: ");
        Scanner testIntervalLength = new Scanner(System.in);
        testIntervalL = testIntervalLength.nextInt();

        for (int i = testFirstL; i <= testLastL; i = i + testIntervalL) {
            int qw;
            testCommonValues.add((double) i);
            System.out.print("Set value of function f(" + i + "): ");
            Scanner scanner = new Scanner(System.in);
            qw = scanner.nextInt();
            testCommonFunValues.add((double) qw);
        }
        setTestMiddleValues(testCommonValues);

        //First task
        magic(firstL, lastL, intervalL);

        //Copy real list without middle point for future
        commonValuesFunX.addAll(functionX);

        //This is second task, - create middle f(x)
        createMiddleFunction(functionX);

        //converting array to massive
        Double[] masCommonX = commonValuesFunX.toArray(new Double[commonValuesFunX.size()]);
        Double[] masIntegerPoints = intValuesOfPoint.toArray(new Double[intValuesOfPoint.size()]);
        Double[] masMiddlePoints = middleValuesOfPoint.toArray(new Double[middleValuesOfPoint.size()]);

        basicLagrangePolynomial(masCommonX, masIntegerPoints, masMiddlePoints);
        //merge middle point in exact point by index
        mergeIndex(middleValuesFunX, commonValuesFunX);

        //TESTING
        Double[] testMasCommonV = testCommonValues.toArray(new Double[testCommonValues.size()]);
        Double[] testMasMiddleV = testMiddleValues.toArray(new Double[testMiddleValues.size()]);
        Double[] testCommonFun = testCommonFunValues.toArray(new Double[testCommonFunValues.size()]);
        test(testCommonFun, testMasCommonV, testMasMiddleV);

        System.out.println("Проверка теста:");
        mergeIndex(testMiddleValues, testCommonValues);
        System.out.println("x: " + testCommonValues);
        System.out.println("func: " + testCommonFunValues);

        System.out.println("Первая таблица:");
        System.out.println("x: " + numbersX);
        System.out.println("f(): " + functionX);
        System.out.println("Вторая таблица:");
        System.out.println("x: " + numbersX);
        System.out.println("f(): " + commonValuesFunX);
    }

    private static void test(Double[] masTestCommon, Double[] comPoints, Double[] midPoints) {
        testLagrangePolynomial(masTestCommon, comPoints, midPoints);
        mergeIndex(testMiddleFunValues, testCommonFunValues);
    }

    private static void setTestMiddleValues(ArrayList<Double> list) {
        for (int i = 1; i < list.size(); i++) {
            double temp;
            temp = ((list.get(i) + list.get(i - 1)) / 2);
            testMiddleValues.add(temp);
        }
    }

    private static void testLagrangePolynomial(Double[] massiveCommonX, Double[] commonPoints, Double[] middlePoints) {
        for (int importantX = 0; importantX <= middlePoints.length - 1; importantX++) {
            double number = middlePoints[importantX];
            double example;
            int countA = 0;
            int countB = 0;
            double comp3 = 0;
            for (int q = 0; q <= massiveCommonX.length - 1; q++) {
                double comp = 1;
                double comp2 = 1;
                double function = massiveCommonX[q];
                for (int a = 0; a <= commonPoints.length - 1; a++) {
                    example = commonPoints[a];
                    if (countA != a) {
                        comp = comp * (number - example);
                    } else {
                        for (int b = 0; b <= commonPoints.length - 1; b++) {
                            double nextN = commonPoints[b];
                            if (countB != b) {
                                comp2 = comp2 * (example - nextN);
                            }
                        }
                    }
                }
                comp3 = comp3 + function * (comp / comp2);
                countA++;
                countB++;
            }
            //Rounding off values for perfect view
            BigDecimal test = new BigDecimal(comp3);
            String round = test.setScale(3, BigDecimal.ROUND_HALF_EVEN).toString();
            double comp3Out;
            comp3Out = Double.parseDouble(round);
            testMiddleFunValues.add(comp3Out);
        }
    }

    private static void basicLagrangePolynomial(Double[] massiveCommonX, Double[] commonPoints, Double[] middlePoints) {
        for (int importantX = 0; importantX <= middlePoints.length - 1; importantX++) {
            double number = middlePoints[importantX];
            double example;
            int countA = 0;
            int countB = 0;
            double comp3 = 0;
            for (int q = 0; q <= massiveCommonX.length - 1; q++) {
                double comp = 1;
                double comp2 = 1;
                double function = massiveCommonX[q];
                for (int a = 0; a <= commonPoints.length - 1; a++) {
                    example = commonPoints[a];
                    if (countA != a) {
                        comp = comp * (number - example);
                    } else {
                        for (int b = 0; b <= commonPoints.length - 1; b++) {
                            double nextN = commonPoints[b];
                            if (countB != b) {
                                comp2 = comp2 * (example - nextN);
                            }
                        }
                    }
                }
                comp3 = comp3 + function * (comp / comp2);
                countA++;
                countB++;
            }
            //Rounding off values for perfect view
            BigDecimal test = new BigDecimal(comp3);
            String round = test.setScale(3, BigDecimal.ROUND_HALF_EVEN).toString();
            double comp3Out;
            comp3Out = Double.parseDouble(round);
            middleValuesFunX.add(comp3Out);
        }
    }

    private static BigDecimal function(double x, int k) {
        BigDecimal resultFirst;
        BigDecimal bigIntX = null, constantSign;
        BigDecimal constant = new BigDecimal(-1);
        constantSign = constant.pow(k);

        BigDecimal consq = new BigDecimal(13 * x);
        bigIntX = consq.pow(2 * k + 1);

        resultFirst = constantSign.multiply(bigIntX);

        BigDecimal resultSecond;
        BigInteger fact = factorial(2 * k + 1);
        BigDecimal factorialDecimal = new BigDecimal(fact);

        resultSecond = resultFirst.divide(factorialDecimal, BigDecimal.ROUND_HALF_EVEN, 0);

        return resultSecond;
    }

    private static BigInteger factorial(int n) {
        BigInteger ret = BigInteger.ONE;
        for (int i = 1; i <= n; ++i) ret = ret.multiply(BigInteger.valueOf(i));
        return ret;
    }

    private static void magic(double firstL, double lastL, double intervalL) {
        double x;

        for (x = firstL; x <= lastL; x = x + intervalL) {
            BigDecimal tempForX, bigDecimalX, bigDecimalResult, bigSum;
            double sumPoint = 0, transformationX, value;
            double first, second, resultTwo, totalTwoNum;
            double sumPointOutput;
            String valueX, resultTwoNumbers, bigSumString;
            int i = 0;

            first = x;
            second = first + intervalL;
            resultTwo = (first + second) / 2;

            bigDecimalX = new BigDecimal(x);
            bigDecimalResult = new BigDecimal(resultTwo);

            valueX = bigDecimalX.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString();
            resultTwoNumbers = bigDecimalResult.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString();

            transformationX = Double.parseDouble(valueX);
            totalTwoNum = Double.parseDouble(resultTwoNumbers);

            tempForX = function(transformationX, i);
            value = tempForX.doubleValue();
            sumPoint = sumPoint + value;

            while (Math.abs(value) > constant) {
                i++;
                BigDecimal helper;
                helper = function(transformationX, i);
                double converter = helper.doubleValue();
                sumPoint = sumPoint + converter;
                value = converter;
            }
            i = 0;

            //Rounding off values for perfect view
            bigSum = new BigDecimal(sumPoint);
            bigSumString = bigSum.setScale(3, BigDecimal.ROUND_HALF_EVEN).toString();
            sumPointOutput = Double.parseDouble(bigSumString);

            intValuesOfPoint.add(transformationX);
            numbersX.add(transformationX);
            functionX.add(sumPointOutput);

            if (totalTwoNum < lastL) {
                numbersX.add(totalTwoNum);
                middleValuesOfPoint.add(totalTwoNum);
            }
        }
    }

    private static void createMiddleFunction(ArrayList<Double> task2) {
        int k = 1;
        for (int i = 0; i < task2.size() - 1; i = i + 2) {
            double first, second;
            first = task2.get(i);
            second = task2.get(k);
            double result;
            result = (second + first) / 2;
            task2.add(k, result);
            k = k + 2;
        }
    }

    private static void mergeIndex(ArrayList<Double> middle, ArrayList<Double> common) {
        int temp = 0;
        for (int i = 0; i <= middle.size() - 1; i++) {
            temp++;
            common.add(temp, middle.get(i));
            temp++;
        }
    }
}