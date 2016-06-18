package zjut.com.laowuguanli.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import zjut.com.laowuguanli.R;
import zjut.com.laowuguanli.adapter.MyAdapter;
import zjut.com.laowuguanli.bean.User;
import zjut.com.laowuguanli.db.LoaderDaoImpl;

/**
 * Created by ScienceHistory on 16/6/5.
 */
public class SignFragment extends Fragment {
    RecyclerView recyclerView;
    List<User> datas;
    MyAdapter adapter;
    private FloatingActionButton fab;
    ProgressDialog progressDialog;
    LoaderDaoImpl mDao;
    List<User> users;
    public boolean isOut = false;
    View mView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_sign,container,false);
        return mView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        initViews();
        users = mDao.getUsers();
        if (users != null) {
            datas.addAll(users);
            adapter.notifyDataSetChanged();
        }
    }

    private void initViews() {
        recyclerView = (RecyclerView) mView.findViewById(R.id.sign_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        datas = new ArrayList<User>();
        adapter = new MyAdapter(getActivity(), datas);

        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemLongClickListener(View itemView, int position) {
                adapter.deleteItem(position);
            }
        });

        recyclerView.setAdapter(adapter);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("请稍候...");
        progressDialog.setMessage("正在处理中...");
        progressDialog.setCancelable(true);

        mDao = new LoaderDaoImpl(getActivity());
    }
}
