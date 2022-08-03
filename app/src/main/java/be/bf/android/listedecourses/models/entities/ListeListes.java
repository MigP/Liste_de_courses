package be.bf.android.listedecourses.models.entities;

public class ListeListes {
    private String categorieList;

    public ListeListes() {
    }

    public ListeListes(String categorieList) {
        this.categorieList = categorieList;
    }

    @Override
    public String toString() {
        return "ListeListes{" +
                "categorieList='" + categorieList + '\'' +
                '}';
    }

    public String getCategorieList() {
        return categorieList;
    }

    public ListeListes setCategorieList(String categorieList) {
        this.categorieList = categorieList;
        return this;
    }
}
