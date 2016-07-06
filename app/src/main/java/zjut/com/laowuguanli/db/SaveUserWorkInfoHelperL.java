package zjut.com.laowuguanli.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SaveUserWorkInfoHelperL extends SQLiteOpenHelper {
    private static final String CREATE_DB = "create table userworkinfo(" +
            "id integer primary key autoincrement," +
            "name text," +
            "inInfo text," +
            "outInfo text," +
            "weiguiInfo text)";

    public SaveUserWorkInfoHelperL(Context context) {
        super(context, "UserWorkInfoL.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}