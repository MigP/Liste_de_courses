package be.bf.android.listedecourses.dal;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import be.bf.android.listedecourses.models.entities.ListeListes;

public class ListeListesDAO {
    public static final String CREATE_QUERY = "CREATE TABLE liste_listes(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, listName VARCHAR(15) NOT NULL, listTag VARCHAR(20))";
    public static final String UPGRADE_QUERY = "DROP TABLE liste_listes;";

    private final DbHelper helper;
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
    public ListeListes getListeListesfromCursor(Cursor cursor) {
        ListeListes listeListes = new ListeListes();
        listeListes.setListName(cursor.getString(cursor.getColumnIndex("listName")));
        listeListes.setListTag(cursor.getString(cursor.getColumnIndex("listTag")));

        return listeListes;
    }

    public List<ListeListes> findAll() {
        List<ListeListes> listesListes = new ArrayList<>();
        Cursor cursor = this.database.query("liste_listes", null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                ListeListes listeListes = getListeListesfromCursor(cursor);
                listesListes.add(listeListes);
            } while (cursor.moveToNext());
        }

        return listesListes;
    }

    public List<ListeListes> findById(int id) {
        List<ListeListes> listesListes = new ArrayList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM liste_listes WHERE id = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                listesListes.add(getListeListesfromCursor(cursor));
            } while (cursor.moveToNext());
        }

        return listesListes;
    }

    @SuppressLint("Range")
    public List<Integer> findId() {
        List<Integer> listIds = new ArrayList<>();
        Cursor cursor = this.database.rawQuery("SELECT id FROM liste_listes", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                listIds.add(cursor.getInt(cursor.getColumnIndex("id")));
            } while (cursor.moveToNext());
        }

        return listIds;
    }

   @SuppressLint("Range")
   public int findLastId() {
        if (this.findAll().size() == 0) {
            return 0;
        } else {
            @SuppressLint("Recycle") Cursor cursor = this.database.rawQuery("SELECT * FROM liste_listes ORDER BY id DESC LIMIT 1", null);
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
    }

    public long insert(ListeListes listeListes) {
        ContentValues cv = new ContentValues();
        cv.put("listName", listeListes.getListName());
        cv.put("listTag", listeListes.getListTag());

        return this.database.insert("liste_listes", null, cv);
    }

    public int update(int id, ListeListes listeListes) {
        ContentValues cv = new ContentValues();
        cv.put("listName", listeListes.getListName());
        cv.put("listTag", listeListes.getListTag());

        return this.database.update("liste_listes", cv, "id = ?", new String[]{String.valueOf(id)});
    }

    public int delete(int id) {
        return this.database.delete("liste_listes", "id = ?", new String[]{id + ""});
    }

    public void close() {
        database.close();
    }
}
