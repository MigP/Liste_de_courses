package be.bf.android.listedecourses.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import be.bf.android.listedecourses.models.entities.ListeCoursesDAO;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "hangman_users";
    public static int DB_VERSION = 1;

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // TODO sqLiteDatabase.execSQL(ListeCoursesDAO.CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // TODO sqLiteDatabase.execSQL(ListeCoursesDAO.UPGRADE_QUERY);
        DB_VERSION = i1;
        onCreate(sqLiteDatabase);
    }
}
