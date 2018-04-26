package databaseSQL;

public class GroupWork {
    private int baseId;
    private int id;
    private String nameGroup;
    private int idClient;

    public GroupWork(int baseId, int id, String nameGroup, int idClient) {
        this.baseId = baseId;
        this.id = id;
        this.nameGroup = nameGroup;
        this.idClient = idClient;
    }

    @Override
    public String toString() {
        return "insert into [Group work] values" +
                " (" + baseId + ", " + id + ", '" + nameGroup + "', '" + idClient + "');";
    }

    public int getBaseId() {
        return baseId;
    }

    public void setBaseId(int baseId) {
        this.baseId = baseId;
    }

    public int getId() {
        return id;
    }

    public int getIdClient() {
        return idClient;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }
}
