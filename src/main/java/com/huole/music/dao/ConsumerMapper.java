package com.huole.music.dao;

import com.huole.music.domain.Consumer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 前端用户Dao
 */
@Repository
public interface ConsumerMapper {

    /**
     * 增加
     */
    public int insert(Consumer consumer);

    /**
     * 修改
     */
    public int update(Consumer consumer);

    /**
     * 删除
     */
    public int delete(Integer id);

    /**
     * 根据id查询用户
     */
    public Consumer selectById(Integer id);

    /**
     * 查询所有用户
     */
    public List<Consumer> selectAll();

    /**
     * 验证密码
     * @param username
     * @param password
     * @return
     */
    public int verifyPassword(String username, String password);

    /**
     * 根据名字查询用户
     */
    public List<Consumer> selectByName(String username);

    /**
     * 根据名字模糊查询用户
     */
    public List<Consumer> selectLikeName(String username);

    /**
     * 根据用户手机号查询用户
     */
    public List<Consumer> selectByPhoneNum(String phoneNum);

}
