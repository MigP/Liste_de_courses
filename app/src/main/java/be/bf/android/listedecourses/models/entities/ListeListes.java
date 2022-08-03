package be.bf.android.listedecourses.models.entities;

public class ListeListes {
    private int listeListesId;
    private String categorieList;

    public ListeListes() {
    }

    public ListeListes(int listeListesId, String categorieList) {
        this.listeListesId = listeListesId;
        this.categorieList = categorieList;
    }

    @Override
    public String toString() {
        return "ListeListes{" +
                "listeListesId='" + listeListesId + '\'' +
                ", categorieList='" + categorieList + '\'' +
                '}';
    }

    public int getListeListesId() {
        return listeListesId;
    }

    public ListeListes setListeListesId(int listeListesId) {
        this.listeListesId = listeListesId;
        return this;
    }

    public String getCategorieList() {
        return categorieList;
    }

    public ListeListes setCategorieList(String categorieList) {
        this.categorieList = categorieList;
        return this;
    }
}
