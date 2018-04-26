package databaseSQL;

public class ClientDirectory {
    private int id;
    private String first_name;
    private String last_name;
    private int gym_membership_id;

    public ClientDirectory(int id, String first_name, String last_name, int gym_membership_id) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gym_membership_id = gym_membership_id;
    }

    @Override
    public String toString() {
        return "insert into [Client Directory] values" +
                " (" + id + ", '" + first_name + "', '" + last_name + "', " + gym_membership_id + ");";
    }

    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGym_membership_id(int gym_membership_id) {
        this.gym_membership_id = gym_membership_id;
    }

    public int getGym_membership_id() {
        return gym_membership_id;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getLast_name() {
        return last_name;
    }
}
