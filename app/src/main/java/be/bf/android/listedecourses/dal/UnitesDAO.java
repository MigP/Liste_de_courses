package be.bf.android.listedecourses.dal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import be.bf.android.listedecourses.models.entities.Categories;
import be.bf.android.listedecourses.models.entities.Unites;

public class UnitesDAO {
    public static final String CREATE_QUERY = "CREATE TABLE unites(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, unit VARCHAR(5) NOT NULL DEFAULT 'Unités')";
    public static final String UPGRADE_QUERY = "DROP TABLE unites;";

    private DbHelper helper;
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
    public Unites getCategoriesfromCursor(Cursor cursor) {
        Unites unites = new Unites();
        unites.setUnitesId(cursor.getInt(cursor.getColumnIndex("id")));
        unites.setUnit(cursor.getString(cursor.getColumnIndex("unit")));

        return unites;
    }
}
