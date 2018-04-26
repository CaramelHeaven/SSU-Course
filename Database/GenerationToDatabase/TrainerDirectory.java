package databaseSQL;

public class TrainerDirectory {
    private int id_trainer;
    private String first_name;
    private String last_name;

    public TrainerDirectory(int id_trainer, String first_name, String last_name) {
        this.id_trainer = id_trainer;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    @Override
    public String toString() {
        return "insert into [Trainer Directory] values" +
                " (" + id_trainer + ", '" + first_name + "', '" + last_name + "');";
    }

    public String getLast_name() {
        return last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public int getId_trainer() {
        return id_trainer;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setId_trainer(int id_trainer) {
        this.id_trainer = id_trainer;
    }
}
