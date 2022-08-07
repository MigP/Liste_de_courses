package be.bf.android.listedecourses.models.entities;

public class Categories {
    private String categorieProdFr;
    private String categorieProdEn;
    private String imageSrc;

    public Categories() {
    }

    public Categories(String categorieProdFr, String categorieProdEn, String imageSrc) {
        this.categorieProdEn = categorieProdEn;
        this.categorieProdFr = categorieProdFr;
        this.imageSrc = imageSrc;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "categorieProdFr='" + categorieProdFr + '\'' +
                ", categorieProdEn='" + categorieProdEn + '\'' +
                ", imageSrc='" + imageSrc + '\'' +
                '}';
    }

    public String getCategorieProdFr() {
        return categorieProdFr;
    }

    public Categories setCategorieProdFr(String categorieProdFr) {
        this.categorieProdFr = categorieProdFr;
        return this;
    }

    public String getCategorieProdEn() {
        return categorieProdEn;
    }

    public Categories setCategorieProdEn(String categorieProdEn) {
        this.categorieProdEn = categorieProdEn;
        return this;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public Categories setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
        return this;
    }
}
