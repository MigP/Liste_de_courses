package be.bf.android.listedecourses.dal;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import be.bf.android.listedecourses.models.entities.Unites;

public class UnitesDAO {
    public static final String CREATE_QUERY = "CREATE TABLE unites(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, unitFr VARCHAR(6) NOT NULL DEFAULT 'Unités', unitEn VARCHAR(6) NOT NULL DEFAULT 'Units')";
    public static final String UPGRADE_QUERY = "DROP TABLE unites;";

    private final DbHelper helper;
    private SQLiteDatabase database;

    public UnitesDAO(Context context) {
        helper = new DbHelper(context);
    }

    public SQLiteDatabase openWritable() {
        return this.database = helper.getWritableDatabase();
    }

    public SQLiteDatabase openReadable() {
        return this.database = helper.getReadableDatabase();
    }

    @SuppressLint("Range")
    public Unites getUnitesfromCursor(Cursor cursor) {
        Unites unites = new Unites();
        unites.setUnitFr(cursor.getString(cursor.getColumnIndex("unitFr")));
        unites.setUnitEn(cursor.getString(cursor.getColumnIndex("unitEn")));
        return unites;
    }

    public List<Unites> findAll() {
        List<Unites> listeUnites = new ArrayList<>();
        Cursor cursor = this.database.query("unites", null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                Unites unites = getUnitesfromCursor(cursor);
                listeUnites.add(unites);
            } while (cursor.moveToNext());
        }

        return listeUnites;
    }

    public List<Unites> findById(int id) {
        List<Unites> listeUnites = new ArrayList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM unites WHERE id = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                listeUnites.add(getUnitesfromCursor(cursor));
            } while (cursor.moveToNext());
        }

        return listeUnites;
    }

    public long insert(Unites unites) {
        ContentValues cv = new ContentValues();
        cv.put("unitFr", unites.getUnitFr());
        cv.put("unitEn", unites.getUnitEn());

        return this.database.insert("unites", null, cv);
    }

    public int update(int id, Unites unites) {
        ContentValues cv = new ContentValues();
        cv.put("unitFr", unites.getUnitFr());
        cv.put("unitEn", unites.getUnitEn());

        return this.database.update("unites", cv, "id = ?", new String[]{String.valueOf(id)});
    }

    public int delete(int id) {
        return this.database.delete("unites", "id = ?", new String[]{id + ""});
    }

    public void close() {
        database.close();
    }
}
