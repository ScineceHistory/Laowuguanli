package zjut.com.laowuguanli;

import retrofit.http.GET;
import rx.Observable;
import zjut.com.laowuguanli.bean.SplashImage;

public interface MyService {
    //传入id查看详细信息
    @GET("/api/7/prefetch-launch-images/1080*1920")
    Observable<SplashImage> getSplashImage();

}
