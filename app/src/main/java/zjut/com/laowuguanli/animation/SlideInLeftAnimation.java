package zjut.com.laowuguanli.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;

import zjut.com.laowuguanli.util.ScreenUtil;

/**
 * 2016.5.28
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class SlideInLeftAnimation {
    Context mContext;

    public SlideInLeftAnimation(Context context) {
        mContext = context;
    }

    public Animator getShowAnimator(View view) {
        return ObjectAnimator.ofFloat(view, "translationX", ScreenUtil.getScreenWidth(mContext), 0);
    }

    public Animator getHideAnimator(View view) {
        return ObjectAnimator.ofFloat(view, "translationX", 0,-ScreenUtil.getScreenWidth(mContext));
    }
}
