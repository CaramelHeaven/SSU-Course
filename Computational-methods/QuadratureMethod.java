import java.util.ArrayList;
import java.util.Arrays;

public class QuadratureMethod {

    public static int length = 50;
    private static ArrayList<Double> answer = new ArrayList<>();
    private static final double LAMBDA = 0.92;
    private static final double[] betta = new double[length];

    public static void main(String[] args) {

        double[] mas_x = new double[length];
        double[] mas_t = new double[length];
        double[] mas_f = new double[length];
        double[] mas_y = new double[length];
        double[] mas_e = new double[length];

        double x = 0;
        for (int i = 0; i < mas_x.length; i++) {
            // 100 - 0.0314 200 - 0.0157, 50 - 0.0628
            x = x + 0.0628;
            mas_x[i] = x;
        }

        double t = 0;
        for (int i = 0; i < mas_t.length; i++) {
            t = t + 0.0314;
            mas_t[i] = t;
        }

        for (int i = 0; i < mas_f.length; i++) {
            mas_f[i] = 25 - 16 * Math.pow(Math.sin(mas_x[i]), 2);
        }

        for (int i = 0; i < mas_y.length; i++) {
            mas_y[i] = 17 / 2 + 128 / 17 * Math.cos(2 * mas_x[i]);
        }

        getQuadratureTrapeze(mas_x, mas_f);

        double[][] matrix = new double[mas_x.length][mas_t.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (i == j) {
                    double temp = -1 * LAMBDA * betta[j] * (1 / 0.64 * Math.pow(Math.cos((mas_x[i] + mas_t[j]) / 2), 2) - 1) + 1;
                    matrix[i][j] = temp;
                } else if (j == matrix[0].length - 1) {
                    matrix[i][j] = mas_f[i];
                } else {
                    matrix[i][j] = -1 * LAMBDA * betta[j] * (1 / 0.64 * Math.pow(Math.cos((mas_x[i] + mas_t[j]) / 2), 2) - 1);
                }
            }
        }
        GaussianElimination gauss = new GaussianElimination(matrix);
        gauss.printArray(matrix);
        gauss.gaussianEliminate();

        for (int i = 0; i < mas_x.length; i++) {
            if (gauss.matrix[i][mas_t.length - 1] < 0) {
                answer.add(-1 * gauss.matrix[i][mas_t.length - 1]);
            } else {
                answer.add(gauss.matrix[i][mas_t.length - 1]);
            }
        }

        for (int i = 0; i < mas_e.length; i++) {
            double qw =  Math.abs(answer.get(i)) - Math.abs(mas_y[i]);
            mas_e[i] = Math.abs(qw);
        }

        System.out.println(Arrays.toString(mas_x) + " :x");
        System.out.println(Arrays.toString(mas_t) + " :t");
        System.out.println(Arrays.toString(mas_y) + " :y");
        System.out.println(answer + " :y - practice");
        System.out.println("e: " + Arrays.toString(mas_e));
    }


    private static void getQuadratureTrapeze(double[] mas_x, double[] mas_f) {
        double h = 0.18;
        for (int i = 0; i < mas_f.length; i++) {
            if (i < mas_f.length - 1) {
                betta[i] = (((17 / 2 + 128 / 17 * Math.cos(2 * mas_x[i]) + 17 / 2 + 128 / 17 * Math.cos(2 * mas_x[i + 1])) / 2) * h);
            }
        }
    }
}