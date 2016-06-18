package zjut.com.laowuguanli.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;


public class AlphaInAnimation  {
    private float mFrom;
    private float mTo;

    public AlphaInAnimation(float from, float to) {
        mFrom = from;
        mTo = to;
    }

    public Animator getHideAnimators(View view) {
        return
            ObjectAnimator.ofFloat(view, "alpha", mFrom, mTo);
    }
}
