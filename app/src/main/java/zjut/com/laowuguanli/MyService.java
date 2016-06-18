package zjut.com.laowuguanli;

import retrofit.http.GET;
import rx.Observable;
import zjut.com.laowuguanli.bean.SplashImage;

public interface MyService {
    //传入id查看详细信息
    @GET("/api/4/start-image/1080*1776")
    Observable<SplashImage> getSplashImage();

}
