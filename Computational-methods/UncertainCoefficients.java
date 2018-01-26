import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;

public class UncertainCoefficients {

    public static void main(String[] args) {
        ArrayList<Double> y_practice = new ArrayList<>();
        ArrayList<Double> mas_pr = new ArrayList<>();

        double x = 0;
        int set_len = 40;

        double[] mas_x = new double[set_len],
                mas_q = new double[set_len],
                mas_y = new double[set_len],
                mas_p = new double[set_len],
                mas_e = new double[set_len];

        for (int i = 0; i < mas_x.length; i++) {
            x = x + 0.1;
            mas_x[i] = x;
        }
        for (int i = 0; i < mas_q.length; i++) {
            mas_q[i] = 9 * mas_x[i];
        }
        for (int i = 0; i < mas_p.length; i++) {
            mas_p[i] = 9 * mas_x[i] * mas_x[i];
        }
        for (int i = 0; i < mas_y.length; i++) {
            double t = 9 * mas_x[i] * mas_x[i] * (mas_x[i] - 10);
            t = new BigDecimal(t).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
            mas_y[i] = t;
        }

        double[][] matrix = new double[set_len][4];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (j == 0) {
                    matrix[i][j] = 6 * mas_x[i];
                } else if (j == 1) {
                    matrix[i][j] = (3 * mas_x[i] * mas_x[i] - 20 * mas_x[i]) * mas_p[i];
                } else if (j == 2) {
                    matrix[i][j] = ((mas_x[i] * mas_x[i] * mas_x[i]) - 10 * mas_x[i] * mas_x[i]) * mas_q[i];
                } else {
                    matrix[i][j] = mas_y[i];
                }
            }
        }
        GaussianElimination gauss = new GaussianElimination(matrix);
        gauss.printArray(matrix);
        gauss.gaussianEliminate();
        gauss.printArray(gauss.matrix);

        for (int i = 0; i < set_len; i++) {
            mas_pr.add(gauss.matrix[i][3]);
        }

        System.out.println(mas_pr);
        for (int i = 0; i < mas_e.length; i++) {
            double t = Math.abs(mas_y[i]) - Math.abs(mas_pr.get(i));
            mas_e[i] = t;
        }

        System.out.println("Vector x: " + Arrays.toString(mas_x));
        System.out.println();
        System.out.println("Practice y: " + mas_pr);
        System.out.println();
        System.out.println("Exact y: " + Arrays.toString(mas_y));
        System.out.println();
        System.out.println("Погрешность: " + Arrays.toString(mas_e));
    }
}