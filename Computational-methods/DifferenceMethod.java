import java.util.ArrayList;
import java.util.Arrays;

public class DifferenceMethod {

    private static ArrayList<Double> mas_y_practice = new ArrayList<>();

    public static void main(String[] args) {
        double x = 0;
        double h = 0.1;
        int set_len = 300;

        double[] mas_x = new double[set_len];
        double[] mas_q = new double[set_len];
        double[] mas_p = new double[set_len];
        double[] mas_y = new double[set_len];
        double[] mas_e = new double[set_len];

        for (int i = 0; i < mas_x.length; i++) {
            if (i == 0) {
                mas_x[i] = 0;
            } else if (i == mas_x.length - 1) {
                mas_x[i] = 0;
            } else {
                x = x + 0.1;
                mas_x[i] = x;
            }
        }

        for (int i = 0; i < mas_q.length; i++) {
            mas_q[i] = 9 * mas_x[i];
        }

        for (int i = 0; i < mas_p.length; i++) {
            mas_p[i] = 9 * mas_x[i] * mas_x[i];
        }

        for (int i = 0; i < mas_y.length; i++) {
            if (i == 0) {
                mas_y[i] = 0;
            } else if (i == mas_x.length - 1) {
                mas_y[i] = 0;
            } else {
                mas_y[i] = -1 * 9 * mas_x[i] * mas_x[i] * (mas_x[i] - 10);
            }
        }

        double[][] matrix = new double[set_len][4];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (i == 0) {
                    matrix[i][0] = 0.1;
                    matrix[i][1] = 0.1;
                    matrix[i][2] = 0.1;
                    matrix[i][3] = 0.1;
                    break;
                }
                if (i == matrix.length - 1) {
                    matrix[i][0] = 0.1;
                    matrix[i][1] = 0.1;
                    matrix[i][2] = 0.1;
                    matrix[i][3] = 0.1;
                    break;
                } else if ((j == 0) && (i == 1)) {
                    matrix[i][j] = 0;
                } else if ((j == 1) && (i == 1)) {
                    matrix[i][j] = (-4 + 2 * h * h * mas_q[i]);
                } else if ((j == 2) && (i == 1)) {
                    matrix[i][j] = (2 + h * mas_p[i]);
                } else if ((j == 3) && (i == 1)) {
                    matrix[i][j] = 2 * h * h * 9 * mas_x[i] * mas_x[i] * (mas_x[i] - 10);
                } else if (j == 0) {
                    matrix[i][j] = 2 - h * mas_q[i];
                } else if (j == 1) {
                    matrix[i][j] = (-4 + 2 * h * h * mas_q[i]);
                } else if (j == 2) {
                    matrix[i][j] = (2 + h * mas_p[i]);
                } else {
                    matrix[i][j] = 2 * h * h * 9 * mas_x[i] * mas_x[i] * (mas_x[i] - 10);
                }
            }
        }
        GaussianElimination gauss = new GaussianElimination(matrix);
        gauss.printArray(matrix);
        gauss.gaussianEliminate();
        gauss.printArray(gauss.matrix);

        for (int i = 0; i < set_len; i++) {
            mas_y_practice.add(gauss.matrix[i][3]);
        }
        System.out.println("X: " + Arrays.toString(mas_x));
        System.out.println();
        System.out.println("Y (exact): " + Arrays.toString(mas_y));
        System.out.println();
        System.out.print("Y practice: " + mas_y_practice);
        System.out.println();
        for (int i = 0; i < set_len; i++) {
            double t = mas_y[i] - mas_y_practice.get(i);
            mas_e[i] = Math.abs(t);
        }
        System.out.println("е: " + Arrays.toString(mas_e));
    }
}