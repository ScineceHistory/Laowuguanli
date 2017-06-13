package zjut.com.laowuguanli;

import retrofit.http.GET;
import rx.Observable;
import zjut.com.laowuguanli.bean.SplashImage;

public interface MyService {
    //传入id查看详细信息
    @GET("/api/data/福利/1/1")
    Observable<SplashImage> getSplashImage();

}
