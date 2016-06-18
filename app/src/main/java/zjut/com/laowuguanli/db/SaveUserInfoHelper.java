package zjut.com.laowuguanli.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ScienceHistory on 16/5/16.
 */
public class SaveUserInfoHelper extends SQLiteOpenHelper {
    private static final String CREATE_DB = "create table userinfo(" +
            "id integer primary key autoincrement," +
            "name text," +
            "pic text," +
            "date text)";

    public SaveUserInfoHelper(Context context) {
        super(context, "User1.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
