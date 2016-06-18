package zjut.com.laowuguanli.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SaveUserInfoHelperW extends SQLiteOpenHelper {
    private static final String CREATE_DB = "create table userinfow(" +
            "id integer primary key autoincrement," +
            "name text," +
            "pic text," +
            "date text)";

    public SaveUserInfoHelperW(Context context) {
        super(context, "Userw.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
