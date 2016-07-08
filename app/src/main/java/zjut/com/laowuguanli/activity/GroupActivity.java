package zjut.com.laowuguanli.activity;

/**
 * 作者 @ScienceHistory
 * 时间 @2016年06月28日 21:29
 */
public class GroupActivity extends WebViewBaseActivity {

    @Override
    void initActionBar() {
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("公司简介");
    }

    @Override
    String getLoadUrl() {
        return "http://8bur.cscec.com/col/col1627/index.html";
    }

}
