package zjut.com.laowuguanli.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * 2016.5.28
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class ScaleInAnimator extends BaseAnimation {
  private static final float DEFAULT_SCALE_FROM = .5f;
  private final float mFrom;

  public ScaleInAnimator() {
      this(DEFAULT_SCALE_FROM);
  }

  public ScaleInAnimator(float from) {
      mFrom = from;
  }

  @Override
  public Animator[] getAnimators(View view) {
    ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", mFrom, 1f);
    ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", mFrom, 1f);
    return new ObjectAnimator[] {
            scaleX, scaleY
    };
  }

}
