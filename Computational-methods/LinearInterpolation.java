package JAVASSU;

/*
    Identify the calculation of the sum of physical sequence at an arbitrarily chosen interval, dividing
    this interval by 10 points. Result of the sum of physical sequence put in the table.

    x1 | x2 | ... | xn - line segment - random
    S1 | S2 | ... | Sn - sum
    n1 | n2 | ... | nn - sequence n, each next term is equal to the sum of all previous

    Mathematical formula: Σ (-1)^k * ((9x)^2k+1 / (2k + 1)!));

*/

import java.math.BigDecimal;
import java.math.BigInteger;

public class LinearInterpolation {

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
        for (x = 4; x < 5; x = x + 1) {
            int counter = 1;
            double sum = 0, transformationX;
            String a;
            int i = 0;
            BigDecimal temp;
            BigDecimal bigDecimal = new BigDecimal(x);
            a = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString();
            transformationX = Double.parseDouble(a);
            temp = function(transformationX, i);
            double value = temp.doubleValue();
            sum = sum + value;

            while (Math.abs(value) > constant) {
                counter++;
                i++;
                BigDecimal example;
                example = function(transformationX, i);
                double converter = example.doubleValue();
                sum = sum + converter;
                value = converter;
            }

            BigDecimal transformSum = new BigDecimal(sum);
            String b;
            b = transformSum.setScale(3, BigDecimal.ROUND_HALF_EVEN).toString();
            double transSum;
            transSum = Double.parseDouble(b);
            System.out.println("  X:     " + transformationX + "  | " + " f(" + transformationX
                    + "): " + transSum + " n: " + counter);
        }
    }
}
