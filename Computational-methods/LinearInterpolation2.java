package JAVASSU;

/*
    Linear interpolation between two known points.
    Add new table:

    x1 | (x1+x2)/2  | x2 | ... | xn - line segment - random
    f1 |  f1 - f2   | f2 | ... | fn - sum

    Mathematical formula: Σ (-1)^k * ((9x)^2k+1 / (2k + 1)!));

*/

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

public class LinearInterpolation2 {
    private static final float constant = 0.00001f;

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

    public static void main(String[] args) {
        double x;
        double firstL, lastL, intervalL;

        System.out.print("Задайте диапозон вводимых значений");

        System.out.print(" от: ");
        Scanner firstLength = new Scanner(System.in);
        firstL = firstLength.nextDouble();

        System.out.print(" до: ");
        Scanner lastLength = new Scanner(System.in);
        lastL = lastLength.nextDouble();

        System.out.println("Введите интервал между точками: ");
        Scanner intervalLength = new Scanner(System.in);
        intervalL = intervalLength.nextDouble();


        for (x = firstL; x < lastL + 0.001; x = x + intervalL) {
            BigDecimal tempForX, bigDecimalX, bigDecimalResult, tempForMiddlePoint, bigSum, bigMiddleSum;
            double sumPoint = 0, transformationX, value;
            double first, second, resultTwo, totalTwoNum, valueMiddle, sumMiddlePoint = 0;
            double sumPointOutput, sumMiddlePointOutput;
            String valueX, resultTwoNumbers, bigSumString, bigMiddleSumString;
            int i = 0, countFirstSequence = 1, countSecondSequence = 1;

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

            tempForMiddlePoint = function(totalTwoNum, i);
            valueMiddle = tempForMiddlePoint.doubleValue();
            sumMiddlePoint = sumMiddlePoint + valueMiddle;

            while (Math.abs(value) > constant) {
                countFirstSequence++;
                i++;
                BigDecimal helper;
                helper = function(transformationX, i);
                double converter = helper.doubleValue();
                sumPoint = sumPoint + converter;
                value = converter;
            }
            i = 0;
            //Error. Need here just doing operation f () = f(i) - f(i-1). Done in third task.
            while (Math.abs(valueMiddle) > constant) {
                countSecondSequence++;
                i++;
                BigDecimal helper;
                helper = function(totalTwoNum, i);
                double converter = helper.doubleValue();
                sumMiddlePoint = sumMiddlePoint + converter;
                valueMiddle = converter;
            }
            //Округляем значения по красоте.
            bigSum = new BigDecimal(sumPoint);
            bigMiddleSum = new BigDecimal(sumMiddlePoint);
            bigSumString = bigSum.setScale(3, BigDecimal.ROUND_HALF_EVEN).toString();
            bigMiddleSumString = bigMiddleSum.setScale(3, BigDecimal.ROUND_HALF_EVEN).toString();
            sumPointOutput = Double.parseDouble(bigSumString);
            sumMiddlePointOutput = Double.parseDouble(bigMiddleSumString);

            System.out.println("  X:     " + transformationX + "  | " + " f(" + transformationX +
                    "): " + sumPointOutput + " N: " + countFirstSequence);
            System.out.println("средняя: " + totalTwoNum + " | " + " f(" + totalTwoNum +
                    "): " + sumMiddlePointOutput + " N: " + countSecondSequence);
        }
    }
}

