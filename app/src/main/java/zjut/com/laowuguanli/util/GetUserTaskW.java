package zjut.com.laowuguanli.util;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.text.SimpleDateFormat;
import java.util.Date;

import zjut.com.laowuguanli.activity.WeiguiActivity;
import zjut.com.laowuguanli.bean.User;
import zjut.com.laowuguanli.db.LoaderDaoImpl;


/**
 * Created by ScienceHistory on 16/5/16.
 */
public class GetUserTaskW extends AsyncTask<String, Void, User> {
    WeiguiActivity mContext;
    LoaderDaoImpl mDao;

    public GetUserTaskW(WeiguiActivity context) {
        super();
        mContext = context;
        mDao = new LoaderDaoImpl(mContext);
    }

    @Override
    protected User doInBackground(String... strings) {
        User user = new User();

        Document doc = null;
        try {
            doc = Jsoup.connect(strings[0]).get();

            Element element = doc.select(".type_content_show").first().select("img").first();
            String pic = element.attr("src");


            Element nameEle = doc.select(".active_show_des ").first();
            String name = nameEle.text();

            user.setName(name);
            user.setPic(pic);

            Date date = new Date();
            SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            user.setDate(sFormat.format(date));

            //mDao.insertUser(user);//插入数据库
        } catch (Exception e) {
//            mContext.showToast("扫码错误");
            return new User();
        }
        return user;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mContext.showDialog();
    }

    @Override
    protected void onPostExecute(User user) {
        super.onPostExecute(user);
        mContext.handlerUser(user);
    }
}
