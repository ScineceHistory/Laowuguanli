package zjut.com.laowuguanli.bean;

/**
 * Created by ScienceHistory on 16/6/16.
 */
public class Option {
    private String name;
    private int colorId;

    public Option(String name, int colorId) {
        this.name = name;
        this.colorId = colorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }
}
