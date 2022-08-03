package be.bf.android.listedecourses.models.entities;

public class Categories {
    private String categorieProd;

    public Categories() {
    }

    public Categories(String categorieProd) {
        this.categorieProd = categorieProd;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "categorieProd='" + categorieProd + '\'' +
                '}';
    }

    public String getCategorieProd() {
        return categorieProd;
    }

    public Categories setCategorieProd(String categorieProd) {
        this.categorieProd = categorieProd;
        return this;
    }
}
