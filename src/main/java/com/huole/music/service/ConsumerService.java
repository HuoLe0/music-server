package com.huole.music.service;

import com.huole.music.model.Consumer;

import java.util.List;

/**
 * 用户service接口
 */

public interface ConsumerService {

    /**
     * 增加
     */
    public boolean insert(Consumer consumer);

    /**
     * 修改
     */
    public boolean update(Consumer consumer);

    /**
     * 删除
     */
    public boolean delete(Integer id);

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
    public boolean verifyPassword(String username, String password);

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
