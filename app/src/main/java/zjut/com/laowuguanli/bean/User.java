package zjut.com.laowuguanli.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangpeng on 16-4-18.
 */
public class User implements Serializable {
    private static final long serialVersionUID = -1757055071420451206L;

    String name;

    String pic;

    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!name.equals(user.name)) return false;
        //if (!date.equals(user.date)) return false;
        return pic.equals(user.pic);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + pic.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return date + "\n" + name;
    }
}
