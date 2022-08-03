package be.bf.android.listedecourses.dal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import be.bf.android.listedecourses.models.entities.Categories;
import be.bf.android.listedecourses.models.entities.ListeListes;

public class CategoriesDAO {
    public static final String CREATE_QUERY = "CREATE TABLE categories(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, categorieProd VARCHAR(10) NOT NULL)";
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
    public Categories getCategories(Cursor cursor) {
        Categories categories = new Categories();
        categories.setCategoriesId(cursor.getInt(cursor.getColumnIndex("id")));
        categories.setCategorieProd(cursor.getString(cursor.getColumnIndex("categorieProd")));

        return categories;
    }
}
