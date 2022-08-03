package be.bf.android.listedecourses.models.entities;

public class Unites {
    private String unit;

    public Unites() {
    }

    public Unites(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Unites{" +
                "unit='" + unit + '\'' +
                '}';
    }

    public String getUnit() {
        return unit;
    }

    public Unites setUnit(String unit) {
        this.unit = unit;
        return this;
    }
}
