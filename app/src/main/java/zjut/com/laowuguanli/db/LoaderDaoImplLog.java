package zjut.com.laowuguanli.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import zjut.com.laowuguanli.bean.Account;

/**
 * Created by ScienceHistory on 16/5/4.
 * 数据访问接口的实现类
 */
public class LoaderDaoImplLog implements LoaderDaoLog {
    private SaveUserInfoHelperLog helper;

    public LoaderDaoImplLog(Context mContext) {
        helper = new SaveUserInfoHelperLog(mContext);
    }

    @Override
    public void insertUser(Account user) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("account",user.getAccount());
        values.put("password",user.getPassword());
        db.insert("userinfoLog",null,values);
        db.close();//数据库关闭
    }


    @Override
    public List<Account> getUsers() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Account> list = new ArrayList<>();
        Cursor cursor = db.query("userinfoLog",null,null,null,null,null,null);
        while (cursor.moveToNext()) {
            Account user = new Account();
            user.setAccount(cursor.getString(cursor.getColumnIndex("account")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            list.add(user);
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public void deleteUser(String account) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from userinfoLog where account = ?",
                new Object[]{account});
        Log.d("sh","---------");
        db.close();//数据库关闭
    }

    public boolean isExists(String account, String password) {
        SQLiteDatabase db = helper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("select * from thread_info where url = ? and thread_id = ?",
//                new String[]{url,thread_id+""});

        Cursor cursor = db.query("userinfoLog",null,
                "account like ? and password like ?",new String[]{account,password},
                null,null,null);
        boolean result = cursor.moveToNext();
        cursor.close();
        db.close();
        return result;
    }
}
