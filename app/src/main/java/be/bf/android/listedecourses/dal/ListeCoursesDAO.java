package be.bf.android.listedecourses.dal;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

import be.bf.android.listedecourses.models.entities.ListeCourses;

public class ListeCoursesDAO implements Closeable {
    public static final String CREATE_QUERY = "CREATE TABLE liste_courses(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, listeId INTEGER NOT NULL, produit VARCHAR(15) NOT NULL, quantite INTEGER NOT NULL DEFAULT 0, uniteId INTEGER NOT NULL DEFAULT 0, categorieProdId1 INTEGER NOT NULL, categorieProdId2 INTEGER NOT NULL, categorieProdId3 INTEGER NOT NULL, achete INTEGER NOT NULL DEFAULT 0, CONSTRAINT fk_liste_listes FOREIGN KEY (listeId) REFERENCES liste_listes(id), CONSTRAINT fk_unites FOREIGN KEY (uniteId) REFERENCES unites(id), CONSTRAINT fk_categories1 FOREIGN KEY (categorieProdId1) REFERENCES categories(id), CONSTRAINT fk_categories2 FOREIGN KEY (categorieProdId2) REFERENCES categories(id), CONSTRAINT fk_categories3 FOREIGN KEY (categorieProdId3) REFERENCES categories(id))";
    public static final String UPGRADE_QUERY = "DROP TABLE liste_courses;";

    private final DbHelper helper;
    private SQLiteDatabase database;

    public ListeCoursesDAO(Context context) {
        helper = new DbHelper(context);
    }

    public SQLiteDatabase openWritable() {
        return this.database = helper.getWritableDatabase();
    }

    public SQLiteDatabase openReadable() {
        return this.database = helper.getReadableDatabase();
    }

    @SuppressLint("Range")
    public ListeCourses getListeCoursesfromCursor(Cursor cursor) {
        ListeCourses listeCourses = new ListeCourses();
        listeCourses.setId(cursor.getInt(cursor.getColumnIndex("id")));
        listeCourses.setListeId(cursor.getInt(cursor.getColumnIndex("listeId")));
        listeCourses.setProduit(cursor.getString(cursor.getColumnIndex("produit")));
        listeCourses.setQuantite(cursor.getInt(cursor.getColumnIndex("quantite")));
        listeCourses.setUniteId((cursor.getInt(cursor.getColumnIndex("uniteId"))));
        listeCourses.setCategorieProdId1((cursor.getInt(cursor.getColumnIndex("categorieProdId1"))));
        listeCourses.setCategorieProdId2((cursor.getInt(cursor.getColumnIndex("categorieProdId2"))));
        listeCourses.setCategorieProdId3((cursor.getInt(cursor.getColumnIndex("categorieProdId3"))));
        listeCourses.setAchete((cursor.getInt(cursor.getColumnIndex("achete"))));

        return listeCourses;
    }

    public List<ListeCourses> findAll() {
        List<ListeCourses> listesCourses = new ArrayList<>();
        Cursor cursor = this.database.query("liste_courses", null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                ListeCourses listeCourses = getListeCoursesfromCursor(cursor);
                listesCourses.add(listeCourses);
            } while (cursor.moveToNext());
        }

        return listesCourses;
    }

    public List<ListeCourses> findById(int id) {
        List<ListeCourses> listesCourses = new ArrayList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM liste_courses WHERE id = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                listesCourses.add(getListeCoursesfromCursor(cursor));
            } while (cursor.moveToNext());
        }

        return listesCourses;
    }

    public List<ListeCourses> findByListId(int id) {
        List<ListeCourses> listesCourses = new ArrayList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM liste_courses WHERE listeId = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                listesCourses.add(getListeCoursesfromCursor(cursor));
            } while (cursor.moveToNext());
        }

        return listesCourses;
    }

    public long insert(ListeCourses listeCourses) {
        ContentValues cv = new ContentValues();
        cv.put("listeId", listeCourses.getListeId());
        cv.put("produit", listeCourses.getProduit());
        cv.put("quantite", listeCourses.getQuantite());
        cv.put("uniteId", listeCourses.getUniteId());
        cv.put("categorieProdId1", listeCourses.getCategorieProdId1());
        cv.put("categorieProdId2", listeCourses.getCategorieProdId2());
        cv.put("categorieProdId3", listeCourses.getCategorieProdId3());
        cv.put("achete", listeCourses.getAchete());

        return this.database.insert("liste_courses", null, cv);
    }

    public int update(int id, ListeCourses listeCourses) {
        ContentValues cv = new ContentValues();
        cv.put("listeId", listeCourses.getListeId());
        cv.put("produit", listeCourses.getProduit());
        cv.put("quantite", listeCourses.getQuantite());
        cv.put("uniteId", listeCourses.getUniteId());
        cv.put("categorieProdId1", listeCourses.getCategorieProdId1());
        cv.put("categorieProdId2", listeCourses.getCategorieProdId2());
        cv.put("categorieProdId3", listeCourses.getCategorieProdId3());
        cv.put("achete", listeCourses.getAchete());

        return this.database.update("liste_courses", cv, "id = ?", new String[]{String.valueOf(id)});
    }

    public int delete(int id) {
        return this.database.delete("liste_courses", "id = ?", new String[]{id + ""});
    }

    public int deleteProductsOfListId(int id) {
        return this.database.delete("liste_courses", "listeId = ?", new String[]{id + ""});
    }

    public void close() {
        database.close();
    }
}
