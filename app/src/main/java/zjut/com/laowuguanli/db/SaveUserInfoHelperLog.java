package zjut.com.laowuguanli.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SaveUserInfoHelperLog extends SQLiteOpenHelper {
    private static final String CREATE_DB = "create table userinfoLog(" +
            "id integer primary key autoincrement," +
            "account text," +
            "password text)";

    public SaveUserInfoHelperLog(Context context) {
        super(context, "UserLog.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
