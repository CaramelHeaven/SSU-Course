package databaseSQL;

public class GymMembership {
    private int id_gym_membership;
    private String day_of_start;
    private String day_of_end;
    private int price_of_membership;
    private int sale;

    public GymMembership(int id_gym_membership, String day_of_start, String day_of_end, int price_of_membership, int sale) {
        this.id_gym_membership = id_gym_membership;
        this.day_of_start = day_of_start;
        this.day_of_end = day_of_end;
        this.price_of_membership = price_of_membership;
        this.sale = sale;
    }

    @Override
    public String toString() {
        return "insert [Gym Membership](id_gym_membership, day_of_start, day_of_end, price_of_membership, sale)" +
                " values (" + id_gym_membership + ", '" + day_of_start + "', '" + day_of_end + "', " + price_of_membership + ", " + sale + ");";
    }

    public int getId_gym_membership() {
        return id_gym_membership;
    }

    public int getSale() {
        return sale;
    }

    public String getDay_of_end() {
        return day_of_end;
    }

    public String getDay_of_start() {
        return day_of_start;
    }

    public int getPrice_of_membership() {
        return price_of_membership;
    }

    public void setId_gym_membership(int id_gym_membership) {
        this.id_gym_membership = id_gym_membership;
    }

    public void setDay_of_end(String day_of_end) {
        this.day_of_end = day_of_end;
    }

    public void setDay_of_start(String day_of_start) {
        this.day_of_start = day_of_start;
    }

    public void setPrice_of_membership(int price_of_membership) {
        this.price_of_membership = price_of_membership;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }
}
