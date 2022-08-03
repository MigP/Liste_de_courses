package be.bf.android.listedecourses.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "courses";
    public static int DB_VERSION = 1;

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CategoriesDAO.CREATE_QUERY);
        sqLiteDatabase.execSQL(UnitesDAO.CREATE_QUERY);
        sqLiteDatabase.execSQL(ListeListesDAO.CREATE_QUERY);
        sqLiteDatabase.execSQL(ListeCoursesDAO.CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(CategoriesDAO.UPGRADE_QUERY);
        sqLiteDatabase.execSQL(UnitesDAO.UPGRADE_QUERY);
        sqLiteDatabase.execSQL(ListeListesDAO.UPGRADE_QUERY);
        sqLiteDatabase.execSQL(ListeCoursesDAO.UPGRADE_QUERY);
        DB_VERSION = i1;
        onCreate(sqLiteDatabase);
    }
}
