package zjut.com.laowuguanli.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import zjut.com.laowuguanli.bean.User;

/**
 * Created by ScienceHistory on 16/5/4.
 * 数据访问接口的实现类
 */
public class LoaderDaoImplq implements LoaderDao {
    private SaveUserInfoHelperQ helper;

    public LoaderDaoImplq(Context mContext) {
        helper = new SaveUserInfoHelperQ(mContext);
    }

    @Override
    public void insertUser(User user) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",user.getName());
        values.put("date",user.getDate());
        values.put("pic",user.getPic());
        values.put("isOut",user.getIsOut());
        db.insert("userinfoq",null,values);
        db.close();//数据库关闭
    }


    @Override
    public List<User> getUsers() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<User> list = new ArrayList<>();
        Cursor cursor = db.query("userinfoq",null,null,null,null,null,null);
        while (cursor.moveToNext()) {
            User user = new User();
            user.setName(cursor.getString(cursor.getColumnIndex("name")));
            user.setDate(cursor.getString(cursor.getColumnIndex("date")));
            user.setPic(cursor.getString(cursor.getColumnIndex("pic")));
            user.setIsOut(cursor.getInt(cursor.getColumnIndex("isOut")));
            list.add(user);
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public void deleteUser(String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from userinfoq where name = ?",
                new Object[]{name});
        Log.d("sh","---------");
        db.close();//数据库关闭
    }

    public boolean isExists(String name, String date) {
        SQLiteDatabase db = helper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("select * from thread_info where url = ? and thread_id = ?",
//                new String[]{url,thread_id+""});

        Cursor cursor = db.query("userinfoq",null,
                "name like ? and date like ?",new String[]{name,date},
                null,null,null);
        boolean result = cursor.moveToNext();
        cursor.close();
        db.close();
        return result;
    }
}
