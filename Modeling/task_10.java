import java.util.ArrayList;
import java.util.List;

public class Task10 {
    private static final int QTY = 1000;
    private static int probabilityFail = 0;

    private static List<Human> queue = new ArrayList<>();
    private static List<Human> applianceM1 = new ArrayList<>();
    private static List<Human> resultM1 = new ArrayList<>();

    public static void main(String[] args) {
        //Woman - 1 класс, абсолютный приоритет
        for (int i = 0; i < QTY; i++) {
            if (i == 0) {
                if (Math.random() < 0.5) {
                    Man man = new Man(0, Math.random(), 0);
                    man.setEnd(man.getStart() + man.getLive());
                    queue.add(man);
                } else {
                    Woman woman = new Woman(0, Math.random(), 0);
                    woman.setEnd(woman.getStart() + woman.getLive());
                    queue.add(woman);
                }
                applianceM1.add(queue.get(0));
                queue.remove(0);
            } else {
                Human human;
                if (Math.random() < 0.5) {
                    human = new Man(Math.random(), 0, 0);
                } else {
                    human = new Woman(Math.random(), 0, 0);
                }
                queue.add(human);
                if (queue.size() > 1) {
                    for (int w = 0; w < queue.size() - 1; w++) {
                        if (queue.get(w) instanceof Man) {
                            if (queue.get(queue.size() - 1) instanceof Man) {
                                ((Man) queue.get(w)).setStart(((Man) queue.get(queue.size() - 1)).getStart() 
                                                              + ((Man) queue.get(w)).getStart());
                            } else if (queue.get(queue.size() - 1) instanceof Woman) {
                                ((Man) queue.get(w)).setStart(((Woman) queue.get(queue.size() - 1)).getStart() 
                                                              + ((Man) queue.get(w)).getStart());
                            }
                        } else if (queue.get(w) instanceof Woman) {
                            if (queue.get(queue.size() - 1) instanceof Man) {
                                ((Woman) queue.get(w)).setStart(((Man) queue.get(queue.size() - 1)).getStart() 
                                                                + ((Woman) queue.get(w)).getStart());
                            } else if (queue.get(queue.size() - 1) instanceof Woman) {
                                ((Woman) queue.get(w)).setStart(((Woman) queue.get(queue.size() - 1)).getStart() 
                                                                + ((Woman) queue.get(w)).getStart());
                            }
                        }
                    }
                }
                if (i == QTY - 1) {
                    resultM1.add(queue.get(0));
                }
                if (queue.get(0) instanceof Woman) {
                    if (applianceM1.get(0) instanceof Man) {
                        ((Woman) queue.get(0)).setLive(Math.random());
                        ((Woman) queue.get(0)).setEnd(((Woman) queue.get(0)).getStart() + ((Woman) queue.get(0)).getLive());
                        applianceM1.set(0, queue.get(0));
                        queue.remove(0);
                        probabilityFail++;
                    } else {
                        if (applianceM1.get(0) instanceof Woman) {
                            if (((Woman) queue.get(0)).getStart() > ((Woman) applianceM1.get(0)).getEnd()) {
                                resultM1.add(applianceM1.get(0));
                                ((Woman) queue.get(0)).setLive(Math.random());
                                ((Woman) queue.get(0)).setEnd(((Woman) queue.get(0)).getStart() + ((Woman) queue.get(0)).getLive());
                                applianceM1.set(0, queue.get(0));
                                queue.remove(0);
                            }
                        }
                    }
                } else if (queue.get(0) instanceof Man) {
                    if (applianceM1.get(0) instanceof Man) {
                        if (((Man) queue.get(0)).getStart() > ((Man) applianceM1.get(0)).getEnd()) {
                            resultM1.add(applianceM1.get(0));
                            ((Man) queue.get(0)).setLive(Math.random());
                            ((Man) queue.get(0)).setEnd(((Man) queue.get(0)).getStart() + ((Man) queue.get(0)).getLive());
                            applianceM1.set(0, queue.get(0));
                            queue.remove(0);
                        }
                    } else if (applianceM1.get(0) instanceof Woman) {
                        if (((Man) queue.get(0)).getStart() > ((Woman) applianceM1.get(0)).getEnd()) {
                            resultM1.add(applianceM1.get(0));
                            ((Man) queue.get(0)).setLive(Math.random());
                            ((Man) queue.get(0)).setEnd(((Man) queue.get(0)).getStart() + ((Man) queue.get(0)).getLive());
                            applianceM1.set(0, queue.get(0));
                            queue.remove(0);
                        }
                    }
                }
            }
        }
        //find u
        double sumMan = 0;
        double sumWoman = 0;
        for (int i = 0; i < resultM1.size(); i++) {
            if (resultM1.get(i) instanceof Man) {
                double temp = ((Man) resultM1.get(i)).getEnd() - ((Man) resultM1.get(i)).getStart();
                sumMan = sumMan + temp;
            } else if (resultM1.get(i) instanceof Woman) {
                double temp = ((Woman) resultM1.get(i)).getEnd() - ((Woman) resultM1.get(i)).getStart();
                sumWoman = sumWoman + temp;
            }
        }
        double uMan = Math.abs((double) 1 / (double) resultM1.size() * sumMan);
        double uWoman = Math.abs((double) 1 / (double) resultM1.size() * sumWoman);

        System.out.println("Мат. ожидание длительности пребывания класса Man в СМО: " + uMan);
        System.out.println("Мат. ожидание длительности пребывания класса Woman в СМО: " + uWoman);
        System.out.println("Вероятность отказа в обслуживании класса Man: " + (double) probabilityFail / (double) QTY 
                           * (double) 100 + " %");
    }

    static class Man extends Human {
        private double start;
        private double live;
        private double end;

        public Man(double start, double live, double end) {
            this.start = start;
            this.live = live;
            this.end = end;
        }

        public double getStart() {
            return start;
        }

        public double getLive() {
            return live;
        }

        public double getEnd() {
            return end;
        }

        public void setStart(double start) {
            this.start = start;
        }

        public void setLive(double live) {
            this.live = live;
        }

        public void setEnd(double end) {
            this.end = end;
        }

        @Override
        public String toString() {
            return "Man = " + "start: " + start + ", live: " + live + ", end: " + end;
        }
    }

    static class Woman extends Human {
        private double start;
        private double live;
        private double end;

        public Woman(double start, double live, double end) {
            this.start = start;
            this.live = live;
            this.end = end;
        }

        public double getStart() {
            return start;
        }

        public double getLive() {
            return live;
        }

        public double getEnd() {
            return end;
        }

        public void setStart(double start) {
            this.start = start;
        }

        public void setLive(double live) {
            this.live = live;
        }

        public void setEnd(double end) {
            this.end = end;
        }

        @Override
        public String toString() {
            return "Woman = " + "start: " + start + ", live: " + live + ", end: " + end;
        }
    }
}
