package be.bf.android.listedecourses.dal;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import be.bf.android.listedecourses.models.entities.Categories;

public class CategoriesDAO {
    public static final String CREATE_QUERY = "CREATE TABLE categories(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, categorieProdFr VARCHAR(15) NOT NULL, categorieProdEn VARCHAR(15) NOT NULL, imageSrc VARCHAR(10) UNIQUE NOT NULL)";
    public static final String UPGRADE_QUERY = "DROP TABLE categories;";

    private DbHelper helper;
    private SQLiteDatabase database;

    public CategoriesDAO(Context context) {
        helper = new DbHelper(context);
    }

    public SQLiteDatabase openWritable() {
        return this.database = helper.getWritableDatabase();
    }

    public SQLiteDatabase openReadable() {
        return this.database = helper.getReadableDatabase();
    }

    @SuppressLint("Range")
    public Categories getCategoriesfromCursor(Cursor cursor) {
        Categories categories = new Categories();
        categories.setCategorieProdFr(cursor.getString(cursor.getColumnIndex("categorieProdFr")));
        categories.setCategorieProdEn(cursor.getString(cursor.getColumnIndex("categorieProdEn")));
        categories.setImageSrc(cursor.getString(cursor.getColumnIndex("imageSrc")));

        return categories;
    }

    public List<Categories> findAll() {
        List<Categories> listCategories = new ArrayList<>();
        Cursor cursor = this.database.query("categories", null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                Categories categories = getCategoriesfromCursor(cursor);
                listCategories.add(categories);
            } while (cursor.moveToNext());
        }

        return listCategories;
    }

    public List<Categories> findById(int id) {
        List<Categories> listCategories = new ArrayList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM categories WHERE id = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                listCategories.add(getCategoriesfromCursor(cursor));
            } while (cursor.moveToNext());
        }

        return listCategories;
    }

    public long insert(Categories categories) {
        ContentValues cv = new ContentValues();
        cv.put("categorieProdFr", categories.getCategorieProdFr());
        cv.put("categorieProdEn", categories.getCategorieProdEn());
        cv.put("imageSrc", categories.getImageSrc());

        return this.database.insert("categories", null, cv);
    }

    public int update(int id, Categories categories) {
        ContentValues cv = new ContentValues();
        cv.put("categorieProdFr", categories.getCategorieProdFr());
        cv.put("categorieProdEn", categories.getCategorieProdEn());
        cv.put("imageSrc", categories.getImageSrc());

        return this.database.update("categories", cv, "id = ?", new String[]{String.valueOf(id)});
    }

    public int delete(int id) {
        return this.database.delete("categories", "id = ?", new String[]{id + ""});
    }

    public void close() {
        database.close();
    }
}
