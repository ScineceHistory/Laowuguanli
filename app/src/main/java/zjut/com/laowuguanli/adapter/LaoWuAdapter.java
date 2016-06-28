package zjut.com.laowuguanli.adapter;

import android.animation.Animator;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import zjut.com.laowuguanli.activity.AdministerActivity;
import zjut.com.laowuguanli.animation.ScaleInAnimator;
import zjut.com.laowuguanli.bean.User;
import zjut.com.laowuguanli.db.LoaderDaoImpll;

public class LaoWuAdapter extends BaseAdapter {

    LoaderDaoImpll mLoaderDao;

    public LaoWuAdapter(AdministerActivity activity, List<User> datas) {
        super(activity, datas);
        mLoaderDao = new LoaderDaoImpll(activity);
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

        if (mListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mListener.onItemLongClickListener(holder.itemView,holder.getLayoutPosition());
                    return true;
                }
            });
        }
    }

    public void deleteItem(int pos) {
        Log.d("SH", ""+pos);
        mActivity.isOut = true;
        Date date = new Date();
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mDatas.get(pos).setDate(sFormat.format(date));
        mActivity.saveUserInfo(mDatas.get(pos));
        mLoaderDao.deleteUser(mDatas.get(pos).getName());
        mDatas.remove(pos);
        notifyItemRemoved(pos);
    }
}
