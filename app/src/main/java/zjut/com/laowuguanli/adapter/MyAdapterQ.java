package zjut.com.laowuguanli.adapter;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.Interpolator;
import android.widget.TextView;

import java.util.List;

import zjut.com.laowuguanli.R;
import zjut.com.laowuguanli.activity.UserInfoActivity;
import zjut.com.laowuguanli.animation.ScaleInAnimator;
import zjut.com.laowuguanli.bean.User;

/**
 * Created by ScienceHistory on 16/5/16.
 * 111
 */
public class MyAdapterQ extends RecyclerView.Adapter<MyAdapterQ.ViewHolder> {

    Context mContext;
    private int mDuration = 700;
    private Interpolator mInterpolator = new AnticipateOvershootInterpolator();
    List<User> mDatas;

    public MyAdapterQ(Context context, List<User> list) {
        mContext = context;
        mDatas = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
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

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    final class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_num;
        TextView tv_name;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_num = (TextView) itemView.findViewById(R.id.tv_num);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getLayoutPosition();
                    Intent intent = new Intent(mContext, UserInfoActivity.class);
                    intent.putExtra("user", mDatas.get(position));
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
