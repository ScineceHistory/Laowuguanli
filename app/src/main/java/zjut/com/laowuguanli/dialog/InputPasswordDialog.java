package zjut.com.laowuguanli.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import zjut.com.laowuguanli.R;


public class InputPasswordDialog extends Dialog implements View.OnClickListener {
    //标题栏
    private TextView mTitleTv;
    //输入密码文本框
    public EditText mInputPassWordEt;
    //回调接口
    private MyCallBack myCallBack;
    public InputPasswordDialog(Context context) {
        super(context, R.style.dialog_custom);//引入自定义的对话框样式
    }

    public void setCallBack(MyCallBack myCallBack) {
        this.myCallBack = myCallBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_password_dialog);
        initView();
    }

    //初始化控件
    private void initView() {
        mTitleTv = (TextView) findViewById(R.id.tv_input_password_title);
        mInputPassWordEt = (EditText) findViewById(R.id.et_input_password);
        findViewById(R.id.btn_confirm).setOnClickListener(this);
        findViewById(R.id.btn_dismiss).setOnClickListener(this);
    }

    //设置对话框标题
    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTitleTv.setText(title);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                myCallBack.confirm();
                break;
            case R.id.btn_dismiss:
                myCallBack.cancel();
                break;
            default:
                break;
        }
    }

    public interface MyCallBack {
        void confirm();
        void cancel();
    }
}
