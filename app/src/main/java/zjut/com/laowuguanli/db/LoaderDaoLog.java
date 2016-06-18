package zjut.com.laowuguanli.db;

import java.util.List;

import zjut.com.laowuguanli.bean.Account;

/**
 * Created by ScienceHistory on 16/5/16.
 */
public interface LoaderDaoLog {
    /**
     * 插入用户信息
     */
    void insertUser(Account user);

    /**
     * 查询用户信息
     */
    List<Account> getUsers();

    /**
     * 删除用户信息
     */
    void deleteUser(String account);

    /**
     * 判断该用户是否存在
     */
    boolean isExists(String account, String password);
}
