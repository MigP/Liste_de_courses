package be.bf.android.listedecourses.models.entities;

public class ListeCourses {
    private String produit;
    private int quantite;
    private String unite;
    private String cat1;
    private String cat2;
    private String cat3;
    private int achete;

    public ListeCourses() {
    }

    public ListeCourses(String produit, int quantite, String unite, String cat1, String cat2, String cat3, int achete) {
        this.produit = produit;
        this.quantite = quantite;
        this.unite = unite;
        this.cat1 = cat1;
        this.cat2 = cat2;
        this.cat3 = cat3;
        this.achete = achete;
    }

    @Override
    public String toString() {
        return "ListeCourses{" +
                "produit='" + produit + '\'' +
                ", quantite='" + quantite + '\'' +
                ", unite='" + unite + '\'' +
                ", cat1='" + cat1 + '\'' +
                ", cat2='" + cat2 + '\'' +
                ", cat3='" + cat3 + '\'' +
                ", achete='" + achete + '\'' +
                '}';
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

    public String getUnite() {
        return unite;
    }

    public ListeCourses setUnite(String unite) {
        this.unite = unite;
        return this;
    }

    public String getCat1() {
        return cat1;
    }

    public ListeCourses setCat1(String cat1) {
        this.cat1 = cat1;
        return this;
    }

    public String getCat2() {
        return cat2;
    }

    public ListeCourses setCat2(String cat2) {
        this.cat2 = cat2;
        return this;
    }

    public String getCat3() {
        return cat3;
    }

    public ListeCourses setCat3(String cat3) {
        this.cat3 = cat3;
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
