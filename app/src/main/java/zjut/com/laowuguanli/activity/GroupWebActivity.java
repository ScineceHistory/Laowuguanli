package zjut.com.laowuguanli.activity;

/**
 * 作者 @ScienceHistory
 * 时间 @2016年06月28日 21:29
 */
public class GroupWebActivity extends WebViewBaseActivity {

    @Override
    void initActionBar() {
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("公司主页");
    }

    @Override
    String getLoadUrl() {
        return "http://www.cscec83.com/";
    }

}
