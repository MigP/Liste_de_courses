package be.bf.android.listedecourses.dal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import be.bf.android.listedecourses.models.entities.ListeCourses;
import be.bf.android.listedecourses.models.entities.ListeListes;

public class ListeListesDAO {
    public static final String CREATE_QUERY = "CREATE TABLE liste_listes(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, categorieList VARCHAR(15) NOT NULL)";
    public static final String UPGRADE_QUERY = "DROP TABLE liste_listes;";

    private DbHelper helper;
    private SQLiteDatabase database;

    public ListeListesDAO(Context context) {
        helper = new DbHelper(context);
    }

    public SQLiteDatabase openWritable() {
        return this.database = helper.getWritableDatabase();
    }

    public SQLiteDatabase openReadable() {
        return this.database = helper.getReadableDatabase();
    }

    @SuppressLint("Range")
    public ListeListes getListeListesf(Cursor cursor) {
        ListeListes listeListes = new ListeListes();
        listeListes.setListeListesId(cursor.getInt(cursor.getColumnIndex("id")));
        listeListes.setCategorieList(cursor.getString(cursor.getColumnIndex("categorieList")));

        return listeListes;
    }
}
