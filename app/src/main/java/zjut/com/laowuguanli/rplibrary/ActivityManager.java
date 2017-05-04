package zjut.com.laowuguanli.rplibrary;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 @sh2zqp
 * 时间 @2016年12月28日 12:22
 */

public class ActivityManager {

    private static List<Activity> mActivities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        mActivities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        if (mActivities.contains(activity))
            mActivities.remove(activity);
    }

    public static void removeAllActivities() {
        if (!mActivities.isEmpty())
            for (Activity activity : mActivities) {
                if (mActivities.contains(activity))
                    mActivities.remove(activity);
            }
    }

    public static Activity getTopActivity() {
        if (!mActivities.isEmpty()) {
            return mActivities.get(mActivities.size()-1);
        }
        return null;
    }

}
