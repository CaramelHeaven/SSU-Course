import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Task9 {

    private static List<Person> firstQueue = new ArrayList<>();
    private static List<Person> secondQueue = new ArrayList<>();

    public static void main(String[] args) {

        double currentTime = 0;

        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                double timeWork = Math.random();
                firstQueue.add(new Person(currentTime, timeWork, currentTime + timeWork));
                currentTime = currentTime + timeWork;
            }

            double choose = Math.random();

            //choose where we put our person in queue,,, secondQueue - > 0.5
            //где меньше, туда и пойдет
            if (choose > 0.5) {
                if ((secondQueue.size() < 3) && i > 0) {
                    double timeWorking = Math.random();
                    secondQueue.add(new Person(currentTime, timeWorking, currentTime + timeWorking));
                    currentTime = currentTime + timeWorking;
                }
            } else {
                if ((firstQueue.size() < 3) && i > 0) {
                    double timeWorking = Math.random();
                    firstQueue.add(new Person(currentTime, timeWorking, currentTime + timeWorking));
                    currentTime = currentTime + timeWorking;
                }
            }
        }

        System.out.println("first: " + firstQueue);
        System.out.println("second: " + secondQueue);

    }

    static class Person {
        private double current;
        private double timeWorking;
        private double endTime;

        @Override
        public String toString() {
            return "current: " + getCurrent() + " startTime: " + getTimeWorking() + " endTime: " + getEndTime();
        }

        public Person(double current, double timeWorking, double endTime) {
            this.current = current;
            this.timeWorking = timeWorking;
            this.endTime = endTime;
        }

        public double getCurrent() {
            return current;
        }

        public double getEndTime() {
            return endTime;
        }

        public double getTimeWorking() {
            return timeWorking;
        }
    }
}
