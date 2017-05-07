package zjut.com.laowuguanli.util;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import zjut.com.laowuguanli.activity.WeiguiActivity;
import zjut.com.laowuguanli.bean.User;
import zjut.com.laowuguanli.db.LoaderDaoImplw;

public class GetUserTaskW extends AsyncTask<String, Void, User> {
    WeiguiActivity mContext;
    LoaderDaoImplw mDao;

    public GetUserTaskW(WeiguiActivity context) {
        super();
        mContext = context;
        mDao = new LoaderDaoImplw(mContext);
    }

    @Override
    protected User doInBackground(String... strings) {
        User user = new User();
        Document doc = null;
        Document doc1 = null;
        try {
            doc = Jsoup.connect(strings[0]).get();
            String body = doc.body().toString();
            String patternString = "\"https(.*?)\""; //href
            Pattern pt=Pattern.compile(patternString);
            Matcher mt=pt.matcher(body);
            String jump_url = null;
            while(mt.find())
            {
                jump_url = mt.group(0);
            }
            if (jump_url != null) {
                jump_url = jump_url.substring(1,jump_url.length()-1);
            }
            System.out.println(jump_url);

            String name1 = NetworkUtil.requestByGet(jump_url);
            System.out.println("=============="+name1);

            doc1 = Jsoup.parse(name1);
            Element nameEle = doc1.select(".active_show_des").first();
            String name = nameEle.text();

            System.out.println("==========name"+name);
            String[] fields = name.split("，");
            String pic = Constants.baseUrl + fields[0];
            System.out.println("pic======="+pic);

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
