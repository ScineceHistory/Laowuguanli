package zjut.com.laowuguanli.bean;

/**
 * 作者 @ScienceHistory
 * 时间 @2016年07月06日 14:52
 */
public class UserWorkInfo {
    String name;
    String inInfo;
    String outInfo;
    String weiguiInfo;

    public String getWeiguiInfo() {
        return weiguiInfo;
    }

    public void setWeiguiInfo(String weiguiInfo) {
        this.weiguiInfo = weiguiInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInInfo() {
        return inInfo;
    }

    public void setInInfo(String inInfo) {
        this.inInfo = inInfo;
    }

    public String getOutInfo() {
        return outInfo;
    }

    public void setOutInfo(String outInfo) {
        this.outInfo = outInfo;
    }

    @Override
    public String toString() {
        return name + "\r\n" + inInfo + "\r\n" + outInfo + "\r\n" + weiguiInfo + "\r\n";
    }
}
