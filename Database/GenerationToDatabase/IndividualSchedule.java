package databaseSQL;

public class IndividualSchedule {
    private int id;
    private String timeStart;
    private String timeEnd;
    private int idClient;
    private int idTrainer;

    public IndividualSchedule(int id, String timeStart, String timeEnd, int idClient, int idTrainer) {
        this.id = id;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.idClient = idClient;
        this.idTrainer = idTrainer;
    }

    @Override
    public String toString() {
        return "insert into [Individual Schedule] values" +
                " (" + id + ", '" + timeStart + "', '" + timeEnd + "', " + idClient + ", " + idTrainer + ");";
    }

    public int getId() {
        return id;
    }

    public int getIdClient() {
        return idClient;
    }

    public int getIdTrainer() {
        return idTrainer;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setIdTrainer(int idTrainer) {
        this.idTrainer = idTrainer;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }
}
