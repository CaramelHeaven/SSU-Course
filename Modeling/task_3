import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Coins {

    public static void main(String[] args) {
        int mainN = 0;
        int periodCounter = 1;
        List<Integer> periodArray = new ArrayList<>();
        for (int q = 0; q < 10; q++) {
            System.out.println("Test № " + q);
            int firstPlayer = 0, secondPlayer = 0;
            for (int i = 0; i < 1000; i++) {
                double temp = Math.random();
                //tail of coin
                if (temp > 0.5) {
                    firstPlayer++;
                } else {
                    secondPlayer++;
                }
            }
            if (firstPlayer > secondPlayer) {
                mainN++;
            } else if (firstPlayer == secondPlayer) {
                System.out.println("Nothing:");
            } else {
                mainN--;
            }
            System.out.println("mainP: " + mainN);

            if (mainN > 0) {
                periodCounter++;
            } else if (mainN == 0) {
                System.out.println("else if (mainN == 0) : " + periodCounter);
                periodArray.add(periodCounter);
                periodCounter = 1;
            } else if (mainN < 0) {
                System.out.println("first is loosing");
            }

            System.out.println(periodArray);

            System.out.println(firstPlayer);
            System.out.println(secondPlayer);

            if (firstPlayer > secondPlayer) {
                System.out.println("First is win: " + firstPlayer + " > " + secondPlayer);
            } else {
                System.out.println("Second is win: " + secondPlayer + " > " + firstPlayer);
            }
            System.out.println("-----------");
        }
        double mainResult = 0;
        if (periodArray.size() == 0) {
            System.out.println("The first player has always won");
        } else {
            int temp = 0;
            for (Integer number : periodArray) {
                temp = temp + number;
            }
            System.out.println("temp: " + temp + " periodArray size: " + periodArray.size());
            mainResult = (double) temp / (double) periodArray.size();
        }
        System.out.println("Expected value: " + mainResult);
    }
}
