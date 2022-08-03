package be.bf.android.listedecourses.models.entities;

public class Categories {
    private int categoriesId;
    private String categorieProd;

    public Categories() {
    }

    public Categories(int categoriesId, String categorieProd) {
        this.categoriesId = categoriesId;
        this.categorieProd = categorieProd;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "categoriesId='" + categoriesId + '\'' +
                ", categorieProd='" + categorieProd + '\'' +
                '}';
    }

    public int getCategoriesId() {
        return categoriesId;
    }

    public Categories setCategoriesId(int categoriesId) {
        this.categoriesId = categoriesId;
        return this;
    }

    public String getCategorieProd() {
        return categorieProd;
    }

    public Categories setCategorieProd(String categorieProd) {
        this.categorieProd = categorieProd;
        return this;
    }
}
