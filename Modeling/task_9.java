import java.util.ArrayList;
import java.util.List;

public class Task_9 {

    private static List<Request> requestList = new ArrayList<>();
    private static final double LYAMBDA = 2.0;

    public static void main(String[] args) {


        double currentTime = 0, startServiceTime = 0;
        double endServiceTime = (-1 / LYAMBDA) * Math.log(Math.random() + 0.0000001);

        System.out.println(endServiceTime);

        requestList.add(new Request(currentTime, startServiceTime, endServiceTime));

        for (int q = 0; q < 1000; q++) {
            double tempNextCurrent = currentTime + (-1 / LYAMBDA) * Math.log(Math.random() + 0.0000001);
            if (endServiceTime > tempNextCurrent) {
                startServiceTime = endServiceTime - currentTime;
            } else {
                
            }
        }

        for (int i = 0; i < 1000; i++) {
            double temp_current_next = currentTime + (-1 / LYAMBDA) * Math.log(Math.random() + 0.0000001);
            if (endServiceTime > temp_current_next) {
                //end service - 2, curent - 1
                startServiceTime = endServiceTime - currentTime;
            } else {
                //а потом время обслуживания второго генерим
                startServiceTime = temp_current_next;
            }
            requestList.add(new Request(temp_current_next, startServiceTime, endServiceTime));
        }
    }


    static class Request {
        private double currentTime;
        private double startService;
        private double endService;


        public Request(double currentTime, double startService, double endService) {
            this.currentTime = currentTime;
            this.startService = startService;
            this.endService = endService;
        }

        public double getEndService() {
            return endService;
        }

        public double getStartService() {
            return startService;
        }

        public double getCurrentTime() {
            return currentTime;
        }
    }
}
