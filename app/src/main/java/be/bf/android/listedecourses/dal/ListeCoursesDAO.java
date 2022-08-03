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
    public static final String CREATE_QUERY = "CREATE TABLE liste_courses(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, FOREIGN KEY(listeId) REFERENCES liste_listes(id) SMALLINT(1) NOT NULL, produit VARCHAR(15) NOT NULL, quantite SMALLINT(5) NOT NULL DEFAULT 0, FOREIGN KEY(unite) REFERENCES unites(id) SMALLINT(1) NOT NULL DEFAULT 0, FOREIGN KEY (categorieProdId1) REFERENCES categories(id) SMALLINT(1) NOT NULL, FOREIGN KEY (categorieProdId2) REFERENCES categories(id) SMALLINT(1) NOT NULL, FOREIGN KEY (categorieProdId3) REFERENCES categories(id) SMALLINT(1) NOT NULL, achete SMALLINT(1) NOT NULL DEFAULT 0)";
    public static final String UPGRADE_QUERY = "DROP TABLE liste_courses;";

    private DbHelper helper;
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
        listeCourses.setListeId(cursor.getInt(cursor.getColumnIndex("id")));
        listeCourses.setProduit(cursor.getString(cursor.getColumnIndex("produit")));
        listeCourses.setQuantite(cursor.getInt(cursor.getColumnIndex("quantite")));
        listeCourses.setUnite((cursor.getString(cursor.getColumnIndex("unite"))));
        listeCourses.setCat1((cursor.getString(cursor.getColumnIndex("cat1"))));
        listeCourses.setCat2((cursor.getString(cursor.getColumnIndex("cat2"))));
        listeCourses.setCat3((cursor.getString(cursor.getColumnIndex("cat3"))));
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

    public List<ListeCourses> findByListeId(int listeId) {
        List<ListeCourses> listesCourses = new ArrayList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM liste_courses WHERE listeId = ?", new String[]{String.valueOf(listeId)});
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
        cv.put("id", listeCourses.getListeId());
        cv.put("produit", listeCourses.getProduit());
        cv.put("quantite", listeCourses.getQuantite());
        cv.put("unite", listeCourses.getUnite());
        cv.put("cat1", listeCourses.getCat1());
        cv.put("cat2", listeCourses.getCat2());
        cv.put("cat3", listeCourses.getCat3());
        cv.put("achete", listeCourses.getAchete());

        return this.database.insert("liste_courses", null, cv);
    }

    public int update(int id, ListeCourses listeCourses) {
        ContentValues cv = new ContentValues();
        cv.put("id", listeCourses.getListeId());
        cv.put("produit", listeCourses.getProduit());
        cv.put("quantite", listeCourses.getQuantite());
        cv.put("unite", listeCourses.getUnite());
        cv.put("cat1", listeCourses.getCat1());
        cv.put("cat2", listeCourses.getCat2());
        cv.put("cat3", listeCourses.getCat3());
        cv.put("achete", listeCourses.getAchete());

        return this.database.update("liste_courses", cv, "id = ?", new String[]{String.valueOf(id)});
    }

    public int delete(int id) {
        return this.database.delete("liste_courses", "id = ?", new String[]{id + ""});
    }

    public void close() {
        // TODO database.close();
    }
}
