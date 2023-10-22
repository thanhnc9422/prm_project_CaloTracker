package vn.edu.fpt.calotracker;

public class Food {
    private int id;
    private String name;
    private float calo;

    public Food(int id, String name, float calo) {
        this.id = id;
        this.name = name;
        this.calo = calo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCalo() {
        return calo;
    }

    public void setCalo(String img) {
        this.calo = calo;
    }
}
