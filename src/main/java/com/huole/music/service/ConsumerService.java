package com.huole.music.service;

import com.huole.music.domain.Consumer;

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
     * 根据主键查询整个对象
     */
    public Consumer selectByPrimaryKey(Integer id);

    /**
     * 查询所有用户
     */
    public List<Consumer> allConsumer();

    /**
     * 验证用户名密码
     */
    public boolean verifyPassword(String username, String password);

    /**
     * 根据用户名字模糊查询列表
     */
    public List<Consumer> consumerOfName(String name);

    public List<Consumer> consumerLikeName(String name);

    /**
     * 根据歌手
     */
    public List<Consumer> consumerOfPhoneNum(String phoneNum);

}
