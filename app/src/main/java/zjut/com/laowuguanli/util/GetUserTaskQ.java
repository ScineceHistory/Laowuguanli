package zjut.com.laowuguanli.util;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import zjut.com.laowuguanli.activity.QianzhengActivity;
import zjut.com.laowuguanli.bean.User;
import zjut.com.laowuguanli.db.LoaderDaoImplq;


public class GetUserTaskQ extends AsyncTask<String, Void, User> {

    QianzhengActivity mContext;
    LoaderDaoImplq mDao;

    public GetUserTaskQ(QianzhengActivity context) {
        super();
        mContext = context;
        mDao = new LoaderDaoImplq(mContext);
    }

    @Override
    protected User doInBackground(String... strings) {
        User user = new User();

        Document doc = null;
        try {
            doc = Jsoup.connect(strings[0]).get();

            //Element element = doc.select(".type_content_show").first().select("img").first();
            //String pic = element.attr("src");

            Element nameEle = doc.select(".active_show_des ").first();
            String name = nameEle.text();

            String[] fields = name.split("，");
            String pic = Constants.baseUrl + fields[0];

            user.setName(name);
            user.setPic(pic);

            Date date = new Date();
            SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            user.setDate(sFormat.format(date));

            //mDao.insertUser(user);//插入数据库
        } catch (Exception e) {

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
