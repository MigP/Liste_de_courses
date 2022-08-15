package be.bf.android.listedecourses.models.entities;

import androidx.annotation.NonNull;

public class ListeCourses {
    private int id;
    private int listeId;
    private String produit;
    private int quantite;
    private int uniteId;
    private int categorieProdId1;
    private int categorieProdId2;
    private int categorieProdId3;
    private int achete;

    public ListeCourses() {
    }

    public ListeCourses(int id, int listeId, String produit, int quantite, int uniteId, int categorieProdId1, int categorieProdId2, int categorieProdId3, int achete) {
        this.id = id;
        this.listeId = listeId;
        this.produit = produit;
        this.quantite = quantite;
        this.uniteId = uniteId;
        this.categorieProdId1 = categorieProdId1;
        this.categorieProdId2 = categorieProdId2;
        this.categorieProdId3 = categorieProdId3;
        this.achete = achete;
    }

    @NonNull
    @Override
    public String toString() {
        return "ListeCourses{" +
                "id='" + id + '\'' +
                ", listeId='" + listeId + '\'' +
                ", produit='" + produit + '\'' +
                ", quantite='" + quantite + '\'' +
                ", uniteId='" + uniteId + '\'' +
                ", categorieProdId1='" + categorieProdId1 + '\'' +
                ", categorieProdId2='" + categorieProdId2 + '\'' +
                ", categorieProdId3='" + categorieProdId3 + '\'' +
                ", achete='" + achete + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public ListeCourses setId(int id) {
        this.id = id;
        return this;
    }

    public int getListeId() {
        return listeId;
    }

    public ListeCourses setListeId(int listeId) {
        this.listeId = listeId;
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
