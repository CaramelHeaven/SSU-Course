package databaseSQL;

public class ClientTrace {
    private int id;
    private int idClient;
    private String dateStart;
    private String dateEnd;

    public ClientTrace(int id, int idClient, String dateStart, String dateEnd) {
        this.id = id;
        this.idClient = idClient;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    @Override
    public String toString() {
        return "insert into ClientTrace values" +
                " (" + id + ", " + idClient + ", '" + dateStart + "', '" + dateEnd + "');";
    }

    public int getIdClient() {
        return idClient;
    }

    public int getId() {
        return id;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }
}
