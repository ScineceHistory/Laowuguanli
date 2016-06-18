package zjut.com.laowuguanli.db;

import java.util.List;

import zjut.com.laowuguanli.bean.User;

/**
 * Created by ScienceHistory on 16/5/16.
 */
public interface LoaderDao {
    /**
     * 插入用户信息
     */
    void insertUser(User user);

    /**
     * 查询用户信息
     */
    List<User> getUsers();

    /**
     * 删除用户信息
     */
    void deleteUser(String name);

    /**
     * 判断该用户是否存在
     */
    boolean isExists(String name,String date);
}
