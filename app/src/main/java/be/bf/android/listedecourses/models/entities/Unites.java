package be.bf.android.listedecourses.models.entities;

public class Unites {
    private int unitesId;
    private String unit;

    public Unites() {
    }

    public Unites(int unitesId, String unit) {
        this.unitesId = unitesId;
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Unites{" +
                "unitesId='" + unitesId + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }

    public int getUnitesId() {
        return unitesId;
    }

    public Unites setUnitesId(int unitesId) {
        this.unitesId = unitesId;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public Unites setUnit(String unit) {
        this.unit = unit;
        return this;
    }
}
