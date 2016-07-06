package zjut.com.laowuguanli.db;

import java.util.List;

import zjut.com.laowuguanli.bean.UserWorkInfo;

/**
 * Created by ScienceHistory on 16/5/16.
 */
public interface LoaderDaoWork {
    /**
     * 插入用户信息
     */
    void insertUser(UserWorkInfo user);

    /**
     * 查询用户信息
     */
    List<UserWorkInfo> getUsers();

    /**
     * 查询用户信息
     */
    UserWorkInfo getUser(String name);

    /**
     * 删除用户信息
     */
    void deleteUser(String name);

    /**
     * 判断该用户是否存在
     */
    boolean isExists(String name);

    void updateUser(String outInfo,String name);
}
