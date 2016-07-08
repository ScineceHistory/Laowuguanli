package zjut.com.laowuguanli.util;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.URLSpan;

/**
 * 作者 @ScienceHistory
 * 时间 @2016年07月08日 16:03
 */
//自定义urlspan 去掉下划线
public class URLSpanNoUnderline extends URLSpan {
    public URLSpanNoUnderline(String url) {
        super(url);
    }


    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
        ds.setColor(Color.rgb(0,103,189));
    }


}


