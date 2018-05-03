import java.util.ArrayList;
import java.util.List;

public class Task_7 {

    public static void main(String[] args) {

        List<Double> firstThread = new ArrayList<>();
        List<Double> secondThread = new ArrayList<>();

        List<Double> firstResult = new ArrayList<>();
        List<Double> secondResult = new ArrayList<>();

        double lyambda = 0.3;

        for (int i = 0; i < 1000; i++) {
            double temp = Math.random();

            //  3/4
            if (temp < 0.75) {
                double x = (-1 / lyambda) * Math.log(Math.random());
                firstThread.add(x);
            } else {
                double x = (-1 / lyambda) * Math.log(Math.random());
                secondThread.add(x);
            }
        }


        for (int i = 0; i < firstThread.size() - 1; i++) {
            firstResult.add(firstThread.get(i + 1) - firstThread.get(i));
        }

        for (int i = 0; i < secondThread.size() - 1; i++) {
            secondResult.add(secondThread.get(i + 1) - secondThread.get(i));
        }

        System.out.println(firstResult);
        System.out.println(secondResult);
    }
}
