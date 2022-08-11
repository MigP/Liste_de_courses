package be.bf.android.listedecourses.models.entities;

import androidx.annotation.NonNull;

public class Unites {
    private String unitFr;
    private String unitEn;

    public Unites() {
    }

    public Unites(String unitFr, String unitEn) {
        this.unitFr = unitFr;
        this.unitEn = unitEn;
    }

    @NonNull
    @Override
    public String toString() {
        return "Unites{" +
                "unitFr='" + unitFr + '\'' +
                ", unitEn='" + unitEn + '\'' +
                '}';
    }

    public String getUnitFr() {
        return unitFr;
    }

    public Unites setUnitFr(String unitFr) {
        this.unitFr = unitFr;
        return this;
    }

    public String getUnitEn() {
        return unitEn;
    }

    public Unites setUnitEn(String unitEn) {
        this.unitEn = unitEn;
        return this;
    }
}
