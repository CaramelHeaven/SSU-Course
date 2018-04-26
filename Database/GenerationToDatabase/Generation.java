package databaseSQL;

import java.util.*;

public class Generation {

    private static final int BASE_SIZE_CLIENTS = 1500;
    private static final int BASE_SIZE_TRAINERS = 200;
    //Сколько тренеров мы выделяем для индивидуального
    private static final int BASE_SIZE_TRAINER_IND = 100;
    //контроллируем сколько клиентов пихаем в индивидуалку
    //для контролля групповых мы просто будем от 150 до 300
    private static final int BASE_SIZE_INDIVIDUAL = 1000;
    //групп будет 4.
    private static final int QUANTITY_GROUP = 4;
    //quantity people in the group
    private static int GROUP_1 = 100;
    private static int GROUP_2 = 50;
    private static int GROUP_3 = 30;
    private static int GROUP_4 = 70;

    private static final int TEMP_1 = GROUP_1;
    private static final int TEMP_2 = GROUP_2;
    private static final int TEMP_3 = GROUP_3;
    private static final int TEMP_4 = GROUP_4;

    private static final int GROUP_WORK_SIZE_ALL = GROUP_1 + GROUP_2 + GROUP_3 + GROUP_4;

    //generation list
    private static List<String> dbLastNames = new ArrayList<>();
    private static List<String> dbFirstNames = new ArrayList<>();

    //list for our tables
    private static List<GymMembership> gymList = new ArrayList<>();
    private static List<ClientDirectory> clientList = new ArrayList<>();
    private static List<TrainerDirectory> trainerList = new ArrayList<>();
    private static List<IndividualSchedule> individualList = new ArrayList<>();
    private static List<ClientTrace> clientTraceList = new ArrayList<>();
    private static List<GroupWork> groupWorkList = new ArrayList<>();

    public static void main(String[] args) {

        initMyDatabase();

        /*for (GymMembership gym : gymList){
            System.out.println(gym);
        }*/

        /*for (ClientDirectory client : clientList) {
            System.out.println(client);
        }*/

        /*for (TrainerDirectory trainer : trainerList){
            System.out.println(trainer);
        }*/

        /*for (IndividualSchedule indiv : individualList){
            System.out.println(indiv);
        }
*/
        /*for (ClientTrace trace : clientTraceList){
            System.out.println(trace);
        }*/

        for (GroupWork groupWork : groupWorkList) {
            System.out.println(groupWork);
        }
    }

    private static void initMyDatabase() {
        //Gym membership generation
        for (int i = 0; i < BASE_SIZE_CLIENTS; i++) {
            String day_of_start = getGymDayOfStart();
            String day_of_end = getGymDayOfEnd();
            int price_of = getGymPriceMembership();
            int sale_id = generate(1, 5);
            GymMembership gym = new GymMembership(i, day_of_start, day_of_end, price_of, sale_id);
            gymList.add(gym);
        }

        //Create Client Directory
        List<Integer> gymIds = new ArrayList<>();
        for (int i = 0; i < BASE_SIZE_CLIENTS; i++) {
            gymIds.add(i);
        }

        Collections.shuffle(gymIds);
        setDbFirstNames();
        setDbLastNames();

        for (int i = 0; i < BASE_SIZE_CLIENTS; i++) {
            int firstRandom = generate(1, dbFirstNames.size() - 1);
            int secondRandom = generate(1, dbLastNames.size() - 1);
            String first_name = dbFirstNames.get(firstRandom);
            String second_name = dbLastNames.get(secondRandom);

            int gym_membership_id = gymIds.get(i);
            ClientDirectory client = new ClientDirectory(i, first_name, second_name, gym_membership_id);
            clientList.add(client);
        }

        //Create Trainer Directory
        for (int i = 0; i < BASE_SIZE_TRAINERS; i++) {
            int firstRandom = generate(1, dbFirstNames.size() - 1);
            int secondRandom = generate(1, dbLastNames.size() - 1);
            String first_name = dbFirstNames.get(firstRandom);
            String second_name = dbLastNames.get(secondRandom);

            TrainerDirectory trainer = new TrainerDirectory(i, first_name, second_name);
            trainerList.add(trainer);
        }

        //Create Individual Schedule
        for (int q = 0; q < BASE_SIZE_INDIVIDUAL; q++) {
            List<String> getTimeList = getTimeIndividual();
            String timeStart = getTimeList.get(0);
            String timeEnd = getTimeList.get(1);

            int idTrainer = trainerList.get(generate(1, BASE_SIZE_TRAINER_IND)).getId_trainer();
            //unique
            int idClient = clientList.get(q).getId();

            IndividualSchedule individual = new IndividualSchedule(q, timeStart, timeEnd, idClient, idTrainer);
            individualList.add(individual);
        }
        Collections.shuffle(individualList);

        //Create Client Trace
        int counterInGroup = GROUP_1;
        int traceId = 0;
        for (int w = BASE_SIZE_INDIVIDUAL; w < BASE_SIZE_CLIENTS; w++) {
            if (GROUP_1 > 0) {
                while (GROUP_1 > 0) {
                    int randClient = generate(w, BASE_SIZE_CLIENTS);

                    List<String> arrayTime = getTimeIndividual();
                    String timeStart = "2017-12-12 " + arrayTime.get(0);
                    String timeEnd = "2019-12-12 " + arrayTime.get(1);

                    ClientTrace clientTrace = new ClientTrace(traceId, randClient, timeStart, timeEnd);
                    clientTraceList.add(clientTrace);
                    traceId++;
                    GROUP_1--;
                }
            } else if (GROUP_2 > 0) {
                while (GROUP_2 > 0) {
                    int randClient = generate(w, BASE_SIZE_CLIENTS);

                    List<String> arrayTime = getTimeIndividual();
                    String timeStart = "2018-12-12 " + arrayTime.get(0);
                    String timeEnd = "2019-11-10 " + arrayTime.get(1);

                    ClientTrace clientTrace = new ClientTrace(traceId, randClient, timeStart, timeEnd);
                    clientTraceList.add(clientTrace);
                    traceId++;
                    GROUP_2--;
                }
            } else if (GROUP_3 > 0) {
                while (GROUP_3 > 0) {
                    int randClient = generate(w, BASE_SIZE_CLIENTS);

                    List<String> arrayTime = getTimeIndividual();
                    String timeStart = "2017-03-05 " + arrayTime.get(0);
                    String timeEnd = "2018-02-07 " + arrayTime.get(1);

                    ClientTrace clientTrace = new ClientTrace(traceId, randClient, timeStart, timeEnd);
                    clientTraceList.add(clientTrace);
                    traceId++;
                    GROUP_3--;
                }
            } else if (GROUP_4 > 0) {
                while (GROUP_4 > 0) {
                    int randClient = generate(w, BASE_SIZE_CLIENTS);

                    List<String> arrayTime = getTimeIndividual();
                    String timeStart = "2018-07-22 " + arrayTime.get(0);
                    String timeEnd = "2019-09-12 " + arrayTime.get(1);

                    ClientTrace clientTrace = new ClientTrace(traceId, randClient, timeStart, timeEnd);
                    clientTraceList.add(clientTrace);
                    traceId++;
                    GROUP_4--;
                }
            }
        }
        Collections.shuffle(clientTraceList);

        for (int e = 0; e < GROUP_WORK_SIZE_ALL; e++) {
            if (e < TEMP_1) {
                GroupWork groupWork = new GroupWork(e, 1, "Group 1", clientTraceList.get(e).getId());
                groupWorkList.add(groupWork);
            } else if (e < TEMP_2 + TEMP_1) {
                GroupWork groupWork = new GroupWork(e, 2, "Group 2", clientTraceList.get(e).getId());
                groupWorkList.add(groupWork);
            } else if (e < TEMP_3 + TEMP_1 + TEMP_2) {
                GroupWork groupWork = new GroupWork(e, 3, "Group 3", clientTraceList.get(e).getId());
                groupWorkList.add(groupWork);
            } else if (e < GROUP_WORK_SIZE_ALL) {
                GroupWork groupWork = new GroupWork(e, 4, "Group 4", clientTraceList.get(e).getId());
                groupWorkList.add(groupWork);
            }
        }
        Collections.shuffle(groupWorkList);
    }

    private static List<String> getTimeIndividual() {
        List<String> passList = new ArrayList<>();

        String day = String.valueOf(generate(1, 31));
        String month = String.valueOf(generate(1, 12));
        String year = String.valueOf(generate(2015, 2017));

        int w = 0;
        while (w < 1) {
            int hour = generate(7, 22);
            int minutes = generate(10, 59);

            int hourq = generate(7, 22);
            int minutesq = generate(10, 59);

            if ((hour < hourq) && (minutes < minutesq)) {
                int temp = hourq - hour;
                if (temp < 2) {
                    String timeStart = String.valueOf(hour + ":" + String.valueOf(minutes) + ":00");
                    String timeEnd = String.valueOf(hourq + ":" + String.valueOf(minutesq) + ":00");
                    w++;
                    passList.add(timeStart);
                    passList.add(timeEnd);
                }
            }
        }
        return passList;
    }

    private static int generate(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    private static int getGymPriceMembership() {
        return (generate(10000, 50000));
    }

    private static String getGymDayOfEnd() {
        String day = String.valueOf(generate(1, 31));
        String month = String.valueOf(generate(1, 12));
        String year = String.valueOf(generate(2020, 2025));
        return (year + "-" + month + "-" + day + " 22:00:00");
    }

    private static String getGymDayOfStart() {
        String day = String.valueOf(generate(1, 31));
        String month = String.valueOf(generate(1, 12));
        String year = String.valueOf(generate(2009, 2015));
        return (year + "-" + month + "-" + day + " 08:00:00");
    }

    private static void setDbLastNames() {
        dbLastNames.add("Teal");
        dbLastNames.add("Parodi");
        dbLastNames.add("Sander");
        dbLastNames.add("Lazarus");
        dbLastNames.add("Cavalli");
        dbLastNames.add("Zucconi");
        dbLastNames.add("Gravenor");
        dbLastNames.add("Juan");
        dbLastNames.add("Karabell");
        dbLastNames.add("Stotsky");
        dbLastNames.add("Matthaei");
        dbLastNames.add("Crowell");
        dbLastNames.add("Biron");
        dbLastNames.add("Metiu");
        dbLastNames.add("Lamberg-karlovsky");
        dbLastNames.add("Passeggieri");
        dbLastNames.add("Hackman");
        dbLastNames.add("Lamarre");
        dbLastNames.add("Kamael");
        dbLastNames.add("Scalia");
        dbLastNames.add("Kotlin");
        dbLastNames.add("Pratt");
        dbLastNames.add("Lecce");
        dbLastNames.add("Godlin");
        dbLastNames.add("Saef");
        dbLastNames.add("Pellish");
        dbLastNames.add("Burnley");
        dbLastNames.add("Schmitt");
        dbLastNames.add("Li");
        dbLastNames.add("Lieven");
        dbLastNames.add("Szajowski");
        dbLastNames.add("Ahn");
        dbLastNames.add("Winans");
        dbLastNames.add("Funkhouser");
        dbLastNames.add("Mc Dylan");
    }

    private static void setDbFirstNames() {
        dbFirstNames.add("Tianna");
        dbFirstNames.add("Debrah");
        dbFirstNames.add("Carie");
        dbFirstNames.add("Israel");
        dbFirstNames.add("Kina");
        dbFirstNames.add("Maddie");
        dbFirstNames.add("Dixie");
        dbFirstNames.add("Karren");
        dbFirstNames.add("Marissa");
        dbFirstNames.add("Cicely");
        dbFirstNames.add("Genoveva");
        dbFirstNames.add("Frederic");
        dbFirstNames.add("Deana");
        dbFirstNames.add("Francisca");
        dbFirstNames.add("Piper");
        dbFirstNames.add("Jere");
        dbFirstNames.add("Christoper");
        dbFirstNames.add("Teddy");
        dbFirstNames.add("Sonia");
        dbFirstNames.add("Thersa");
        dbFirstNames.add("Shelby");
        dbFirstNames.add("Bennett");
        dbFirstNames.add("Emmitt");
        dbFirstNames.add("Bennett");
        dbFirstNames.add("William");
        dbFirstNames.add("Carletta");
        dbFirstNames.add("Rosalyn");
        dbFirstNames.add("Horacio");
        dbFirstNames.add("Alan");
        dbFirstNames.add("Eloy");
        dbFirstNames.add("Miles");
        dbFirstNames.add("Sonia");
        dbFirstNames.add("Yon");
        dbFirstNames.add("Alda");
        dbFirstNames.add("Jeff");
        dbFirstNames.add("Dorian");
        dbFirstNames.add("Boyd");
        dbFirstNames.add("Ivan");
        dbFirstNames.add("Jody");
        dbFirstNames.add("Richie");
        dbFirstNames.add("Damian");
        dbFirstNames.add("Mickey");
        dbFirstNames.add("Jan");
        dbFirstNames.add("Loyd");
        dbFirstNames.add("Ward");
        dbFirstNames.add("Chet");
        dbFirstNames.add("Rupert");
        dbFirstNames.add("Jerry");
        dbFirstNames.add("Jeff");
        dbFirstNames.add("Jasper");
        dbFirstNames.add("Rofl");
        dbFirstNames.add("Jaime");
        dbFirstNames.add("Nestor");
        dbFirstNames.add("Elon");
        dbFirstNames.add("Nikolay");
        dbFirstNames.add("Victor");
        dbFirstNames.add("Ruslan");
        dbFirstNames.add("Ned");
        dbFirstNames.add("Garfield");
        dbFirstNames.add("Dan");
        dbFirstNames.add("Allan");
        dbFirstNames.add("Clair");
        dbFirstNames.add("Kris");
        dbFirstNames.add("Collin");
        dbFirstNames.add("Hank");
        dbFirstNames.add("Alfredo");
    }
}
