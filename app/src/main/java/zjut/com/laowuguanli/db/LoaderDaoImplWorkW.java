package zjut.com.laowuguanli.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import zjut.com.laowuguanli.bean.UserWorkInfo;

/**
 * Created by ScienceHistory on 16/5/4.
 * 数据访问接口的实现类
 */
public class LoaderDaoImplWorkW implements LoaderDaoWork {
    private SaveUserWorkInfoHelperW helper;

    public LoaderDaoImplWorkW(Context mContext) {
        helper = new SaveUserWorkInfoHelperW(mContext);
    }

    @Override
    public void insertUser(UserWorkInfo user) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",user.getName());
        values.put("inInfo",user.getInInfo());
        values.put("outInfo",user.getOutInfo());
        values.put("weiguiInfo",user.getWeiguiInfo());
        db.insert("userworkinfo",null,values);
        db.close();//数据库关闭
    }


    @Override
    public List<UserWorkInfo> getUsers() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<UserWorkInfo> list = new ArrayList<>();
        Cursor cursor = db.query("userworkinfo",null,null,null,null,null,null);
        while (cursor.moveToNext()) {
            UserWorkInfo user = new UserWorkInfo();
            user.setName(cursor.getString(cursor.getColumnIndex("name")));
            user.setInInfo(cursor.getString(cursor.getColumnIndex("inInfo")));
            user.setOutInfo(cursor.getString(cursor.getColumnIndex("outInfo")));
            user.setWeiguiInfo(cursor.getString(cursor.getColumnIndex("weiguiInfo")));
            list.add(user);
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public UserWorkInfo getUser(String name) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from userworkinfo where name like ?", new String[]{name});
        UserWorkInfo user = new UserWorkInfo();
        if (cursor.moveToNext()) {
            user.setName(cursor.getString(cursor.getColumnIndex("name")));
            user.setInInfo(cursor.getString(cursor.getColumnIndex("inInfo")));
            user.setOutInfo(cursor.getString(cursor.getColumnIndex("outInfo")));
            user.setWeiguiInfo(cursor.getString(cursor.getColumnIndex("weiguiInfo")));
        }
        cursor.close();
        db.close();
        return user;
    }

    @Override
    public void deleteUser(String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from userworkinfo where name = ?",
                new Object[]{name});
        Log.d("sh","---------");
        db.close();//数据库关闭
    }

    public boolean isExists(String name) {
        SQLiteDatabase db = helper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("select * from thread_info where url = ? and thread_id = ?",
//                new String[]{url,thread_id+""});

        Cursor cursor = db.query("userworkinfo",null,
                "name like ?",new String[]{name},
                null,null,null);
        boolean result = cursor.moveToNext();
        cursor.close();
        db.close();
        return result;
    }

    @Override
    public void updateUser(String outInfo,String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("outInfo",outInfo);
        db.update("userworkinfo",values,"name=?",new String[]{name});
    }
}
