public class Task_6 {

    private static List<Double> expectedValue;
    private static List<Double> deviation;


    public static void main(String[] args) {

        expectedValue = List.of(0.643, 0.642, 0.641);
        deviation = List.of(0.011, 0.012, 0.013);

        double sumFirst = 0, sumSecond = 0, sumThird = 0;

        for (int i = 0; i < 1000; i++) {

            //container ours 3 elements
            List<Double> tempValues = new ArrayList<>();

            for (int w = 0; w < 3; w++) {
                double sumR = 0.0;
                // counting R
                for (int q = 0; q < 12; q++) {
                    double randomR = Math.random();
                    sumR = sumR + randomR;
                }
                //counting in the () from math formula
                double resultOf = sumR - 6;
                double finalResult = expectedValue.get(w) + (deviation.get(w) * resultOf);
                tempValues.add(finalResult);
            }

            int minIndex = tempValues.indexOf(Collections.min(tempValues));
            //added very small element
            sumFirst = sumFirst + tempValues.get(minIndex);
            tempValues.remove(minIndex);

            int preMinIndex = tempValues.indexOf(Collections.min(tempValues));
            //added pre small element
            sumSecond = sumSecond + tempValues.get(preMinIndex);

            //added last element
            sumThird = sumThird + tempValues.get(0);
        }

        //last operation
        System.out.println("Expected value First: " + sumFirst / 1000);
        System.out.println("Expected value Second: " + sumSecond / 1000);
        System.out.println("Expected value Third: " + sumThird / 1000);
    }
}
