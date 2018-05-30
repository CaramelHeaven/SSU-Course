import java.util.ArrayList;

public class Task9 {
    private static final int QTY = 1000;
    private static int probabilityFail = 0;

    private static Man[] applianceM1 = new Man[1];
    private static Man[] applianceM2 = new Man[1];
    private static ArrayList<Man> queue = new ArrayList<>();

    private static ArrayList<Man> totalsAM1 = new ArrayList<>();
    private static ArrayList<Man> totalsAM2 = new ArrayList<>();

    public static void main(String[] args) {
        double lyambda = 0.3;
        double mu = 0.5;
        for (int i = 0; i < QTY; i++) {
            Man man;
            if (i == 0) {
                man = new Man(0, 0, 0);
            } else if (i == 1) {
                man = new Man(0, 0, 0);
            } else {
                man = new Man(-1 / lyambda * Math.log(Math.random()), 0, 0);
            }
            if (queue.size() < 3) {
                queue.add(man);
            } else {
                probabilityFail++;
            }
            if ((applianceM1[0] == null) && (applianceM2[0] == null)) {
                if (Math.random() > 0.5) {
                    man.setLive(-1 / mu * Math.log(Math.random()));
                    man.setEnd(man.getStart() + man.getLive());
                    applianceM1[0] = man;
                    queue.remove(man);
                } else {
                    man.setLive(-1 / mu * Math.log(Math.random()));
                    man.setEnd(man.getStart() + man.getLive());
                    applianceM2[0] = man;
                    queue.remove(man);
                }
            } else if (applianceM1[0] == null) {
                applianceM1[0] = man;
                man.setLive(-1 / mu * Math.log(Math.random()));
                man.setEnd(man.getStart() + man.getLive());
                queue.remove(man);
            } else if (applianceM2[0] == null) {
                man.setLive(-1 / mu * Math.log(Math.random()));
                man.setEnd(man.getStart() + man.getLive());
                applianceM2[0] = man;
                queue.remove(man);
            }
            if (i > 1) {
                if (queue.size() > 1) {
                    queue.get(0).setStart(queue.get(0).getStart() + queue.get(1).getStart());
                    Man firstMan = queue.get(0);
                    if (firstMan.getStart() > applianceM1[0].getEnd()) {
                        totalsAM1.add(applianceM1[0]);
                        firstMan.setLive(Math.random());
                        firstMan.setEnd(firstMan.getStart() + firstMan.getLive());
                        applianceM1[0] = null;
                        applianceM1[0] = firstMan;
                        queue.set(0, queue.get(1));
                        queue.remove(1);
                    } else if (firstMan.getStart() > applianceM2[0].getEnd()) {
                        totalsAM2.add(applianceM2[0]);
                        firstMan.setLive(Math.random());
                        firstMan.setEnd(firstMan.getStart() + firstMan.getLive());
                        applianceM2[0] = null;
                        applianceM2[0] = firstMan;
                        queue.set(0, queue.get(1));
                        queue.remove(1);
                    }
                } else {
                    if (man.getStart() > applianceM1[0].getEnd()) {
                        totalsAM1.add(applianceM1[0]);
                        man.setLive(Math.random());
                        man.setEnd(man.getStart() + man.getLive());
                        applianceM1[0] = null;
                        applianceM1[0] = man;
                        queue.remove(0);
                    } else if (man.getStart() > applianceM2[0].getEnd()) {
                        totalsAM2.add(applianceM2[0]);
                        man.setLive(Math.random());
                        man.setEnd(man.getStart() + man.getLive());
                        applianceM2[0] = null;
                        applianceM2[0] = man;
                        queue.remove(0);
                    } else {
                        Man am1 = applianceM1[0];
                        Man am2 = applianceM2[0];
                        applianceM1[0].setEnd(am1.getEnd() - man.getStart());
                        applianceM2[0].setEnd(am2.getEnd() - man.getStart());
                    }
                }
            }
        }
        //Оценка мат ожидания длительности пребывания требований во времени в системе обслуживания
        double sumU = 0;
        for (int i = 0; i < totalsAM1.size(); i++) {
            sumU = sumU + totalsAM1.get(i).getEnd() - totalsAM1.get(i).getStart();
        }
        double u = (double) 1 / (double) totalsAM1.size() * sumU;

        sumU = 0;
        for (int i = 0; i < totalsAM2.size(); i++) {
            sumU = sumU + totalsAM2.get(i).getEnd() - totalsAM2.get(i).getStart();
        }
        double u1 = (double) 1 / (double) totalsAM2.size() * sumU;

        for (int i = 0; i < totalsAM1.size(); i++) {
            System.out.println(totalsAM1.get(i));
        }


        for (int i = 0; i < totalsAM2.size(); i++) {
            System.out.println(totalsAM2.get(i));
        }

        System.out.println("Мат. ожидание длительности пребывания требований во времени в системе обслуживания 1 прибора: " + u);
        System.out.println("Мат. ожидание длительности пребывания требований во времени в системе обслуживания 2 прибора: " + u1);
        System.out.println("Вероятность отказа требованию в обслуживании: " + (double) probabilityFail / (double) 100 + " %");
    }
}
