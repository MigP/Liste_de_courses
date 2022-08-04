package be.bf.android.listedecourses.models.entities;

public class ListeCourses {
    private String listName;
    private String listCat;
    private String produit;
    private int quantite;
    private int uniteId;
    private int categorieProdId1;
    private int categorieProdId2;
    private int categorieProdId3;
    private int achete;

    public ListeCourses() {
    }

    public ListeCourses(String listName, String listCat, String produit, int quantite, int uniteId, int categorieProdId1, int categorieProdId2, int categorieProdId3, int achete) {
        this.listName = listName;
        this.listCat = listCat;
        this.produit = produit;
        this.quantite = quantite;
        this.uniteId = uniteId;
        this.categorieProdId1 = categorieProdId1;
        this.categorieProdId2 = categorieProdId2;
        this.categorieProdId3 = categorieProdId3;
        this.achete = achete;
    }

    @Override
    public String toString() {
        return "ListeCourses{" +
                "listName='" + listName + '\'' +
                ", listCat='" + listCat + '\'' +
                ", produit='" + produit + '\'' +
                ", quantite='" + quantite + '\'' +
                ", uniteId='" + uniteId + '\'' +
                ", categorieProdId1='" + categorieProdId1 + '\'' +
                ", categorieProdId2='" + categorieProdId2 + '\'' +
                ", categorieProdId3='" + categorieProdId3 + '\'' +
                ", achete='" + achete + '\'' +
                '}';
    }

    public String getListName() {
        return listName;
    }

    public ListeCourses setListName(String listName) {
        this.listName = listName;
        return this;
    }

    public String getListCat() {
        return listCat;
    }

    public ListeCourses setListCat(String listCat) {
        this.listCat = listCat;
        return this;
    }

    public String getProduit() {
        return produit;
    }

    public ListeCourses setProduit(String produit) {
        this.produit = produit;
        return this;
    }

    public int getQuantite() {
        return quantite;
    }

    public ListeCourses setQuantite(int quantite) {
        this.quantite = quantite;
        return this;
    }

    public int getUniteId() {
        return uniteId;
    }

    public ListeCourses setUniteId(int uniteId) {
        this.uniteId = uniteId;
        return this;
    }

    public int getCategorieProdId1() {
        return categorieProdId1;
    }

    public ListeCourses setCategorieProdId1(int categorieProdId1) {
        this.categorieProdId1 = categorieProdId1;
        return this;
    }

    public int getCategorieProdId2() {
        return categorieProdId2;
    }

    public ListeCourses setCategorieProdId2(int categorieProdId2) {
        this.categorieProdId2 = categorieProdId2;
        return this;
    }

    public int getCategorieProdId3() {
        return categorieProdId3;
    }

    public ListeCourses setCategorieProdId3(int categorieProdId3) {
        this.categorieProdId3 = categorieProdId3;
        return this;
    }

    public int getAchete() {
        return achete;
    }

    public ListeCourses setAchete(int achete) {
        this.achete = achete;
        return this;
    }
}
