import java.util.ArrayList;
import java.util.Arrays;

public class DegenerateKernel {

    public static int length = 50;
    private static final double LAMBDA = 1;
    private static ArrayList<Double> answer = new ArrayList<>();
    private static final double[] alpha_x = new double[length];
    private static final double[] fi = new double[length];

    public static void main(String[] args) {

        double[] mas_x = new double[length];
        double[] mas_f = new double[length];
        double[] mas_y = new double[length];
        double[] mas_e = new double[length];

        double x = 0;
        for (int i = 0; i < mas_x.length; i++) {
            x = x + 1;
            mas_x[i] = x;
        }

        for (int i = 0; i < mas_f.length; i++) {
            mas_f[i] = -1 * mas_x[i] / 6 - 1 / 2;
        }

        for (int i = 0; i < mas_y.length; i++) {
            mas_y[i] = mas_x[i] + 1 / 2;
        }

        getQuadratureTrapeze_X(mas_x);
        getQuadratureTrapeze_F(mas_x);

        double[][] matrix = new double[mas_x.length][mas_x.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (i == j) {
                    double temp = 1 - LAMBDA * alpha_x[j];
                    matrix[i][j] = temp;
                } else if (j == matrix[0].length - 1) {
                    matrix[i][j] = fi[i];
                } else {
                    matrix[i][j] = -1 * LAMBDA * alpha_x[j];
                }
            }
        }
        GaussianElimination gauss = new GaussianElimination(matrix);
        gauss.printArray(matrix);
        gauss.gaussianEliminate();

        for (int i = 0; i < mas_x.length; i++) {
            if (gauss.matrix[i][mas_x.length - 1] < 0) {
                answer.add(-1 * gauss.matrix[i][mas_x.length - 1]);
            } else {
                answer.add(gauss.matrix[i][mas_x.length - 1]);
            }
        }

        for (int i = 0; i < mas_e.length; i++) {
            double qw = Math.abs(answer.get(i)) - Math.abs(mas_y[i]);
            mas_e[i] = Math.abs(qw);
        }

        System.out.println("Vector x: " + Arrays.toString(mas_x));
        System.out.println("Theoretical y: " + Arrays.toString(mas_y));
        System.out.println("Practical y: " + answer);
        System.out.println("e: " + Arrays.toString(mas_e));
    }


    private static void getQuadratureTrapeze_X(double[] mas_x) {
        double h = mas_x[1] - mas_x[0];
        for (int i = 0; i < mas_x.length; i++) {
            if (i < mas_x.length - 1) {
                alpha_x[i] = (((2 * mas_x[i] * mas_x[i] + 3 * mas_x[i] + 1) / 2) * h);
            }
        }
    }

    private static void getQuadratureTrapeze_F(double[] mas_x) {
        double h = mas_x[1] - mas_x[0];
        for (int i = 0; i < mas_x.length; i++) {
            if (i < mas_x.length - 1) {
                fi[i] = (((-1 * mas_x[i] / 6 - 0.5 - 1 * mas_x[i] / 6 - 0.5 * mas_x[i]) * h));
            }
        }
    }
}