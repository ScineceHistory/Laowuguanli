package zjut.com.laowuguanli.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import zjut.com.laowuguanli.R;
import zjut.com.laowuguanli.activity.UserInfoActivity;
import zjut.com.laowuguanli.bean.User;

/**
 * Created by ScienceHistory on 16/5/16.
 * 111
 */
public class MyAdapterQ extends RecyclerView.Adapter<MyAdapterQ.ViewHolder> {

    Context mContext;

//    LoaderDaoImpl mLoaderDao;

    List<User> mDatas;
//    public interface OnItemClickListener {
//        void onItemLongClickListener(View itemView, int position);
//    }
//    public OnItemClickListener mListener;
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.mListener = listener;
//    }


    public MyAdapterQ(Context context, List<User> list) {
        mContext = context;
        mDatas = list;
 //       mLoaderDao = new LoaderDaoImpl(context);
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

//        if (mListener != null) {
//            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    mListener.onItemLongClickListener(holder.itemView,holder.getLayoutPosition());
//                    return true;
//                }
//            });
//        }
    }

//    public void deleteItem(int pos) {
//        Log.d("SH", ""+pos);
//        mLoaderDao.deleteUser(mDatas.get(pos).getName(),
//                mDatas.get(pos).getDate());
//        mDatas.remove(pos);
//        notifyItemRemoved(pos);
//    }

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
