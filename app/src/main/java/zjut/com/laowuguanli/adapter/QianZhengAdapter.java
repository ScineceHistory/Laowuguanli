package zjut.com.laowuguanli.adapter;

import android.animation.Animator;
import android.util.Log;

import java.util.List;

import zjut.com.laowuguanli.activity.AdministerActivity;
import zjut.com.laowuguanli.animation.ScaleInAnimator;
import zjut.com.laowuguanli.bean.User;
import zjut.com.laowuguanli.db.LoaderDaoImplq;

public class QianZhengAdapter extends BaseAdapter {

    LoaderDaoImplq mLoaderDao;

    public QianZhengAdapter(AdministerActivity activity, List<User> datas) {
        super(activity, datas);
        mLoaderDao = new LoaderDaoImplq(activity);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        User user = mDatas.get(position);
        holder.tv_name.setText(user.getDate());
        String[] split = user.getName().split("，");
        Log.d("SH",split[0]);

        holder.tv_num.setText((position+1)+". "+split[0]);


        ScaleInAnimator animation = new ScaleInAnimator();
        for (Animator anim : animation.getAnimators(holder.itemView)) {
            anim.setInterpolator(mInterpolator);
            anim.setDuration(mDuration).start();
        }
    }
}
