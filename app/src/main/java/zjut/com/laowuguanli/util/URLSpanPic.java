package zjut.com.laowuguanli.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;

import zjut.com.laowuguanli.activity.PictureActivity;

/**
 * 作者 @ScienceHistory
 * 时间 @2016年07月08日 16:03
 */
//自定义urlspan 去掉下划线
public class URLSpanPic extends URLSpan {

    private String titleStr;
    private String picUrl;

    public URLSpanPic(String url,String titleStr) {
        super(url);
        picUrl = url;
        this.titleStr = titleStr;
    }


    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(true);
        ds.setColor(Color.rgb(0,103,189));
    }

    @Override
    public void onClick(View widget) {
        Uri uri = Uri.parse(getURL());
        Context context = widget.getContext();
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putExtra("picUrl",picUrl);
        intent.putExtra("titleStr",titleStr);

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.w("URLSpan", "Actvity was not found for intent, " + intent.toString());
        }
    }

}


