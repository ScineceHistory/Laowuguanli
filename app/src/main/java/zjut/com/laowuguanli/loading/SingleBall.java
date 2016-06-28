package zjut.com.laowuguanli.loading;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import zjut.com.laowuguanli.R;


public class SingleBall extends View {

    private int width;
    private int height;
    private Paint paint;
    private int loadingColor = getResources().getColor(android.R.color.darker_gray);

    public SingleBall(Context context) {
        this(context,null);
    }

    public SingleBall(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public SingleBall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(AttributeSet attrs){
        if (null != attrs){
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SingleBall);
            loadingColor = typedArray.getColor(R.styleable.SingleBall_single_ball_color,
                    getResources().getColor(android.R.color.darker_gray));
            typedArray.recycle();
        }
        paint = new Paint();
        paint.setColor(loadingColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }

    // ???
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("Science", "onSizeChanged: " + w);
        Log.d("Science", "onSizeChanged: " + h);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(width/2, height/2, width/2, paint);
    }

    public void setLoadingColor(int color){
        loadingColor = color;
        paint.setColor(color);
        postInvalidate();// 重绘小球
    }

    public int getLoadingColor(){
        return loadingColor;
    }
}
