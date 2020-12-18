package com.huole.music.service.Impl;

import com.huole.music.dao.ConsumerMapper;
import com.huole.music.domain.Consumer;
import com.huole.music.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户service实现
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private ConsumerMapper consumerMapper;
    /**
     * 增加
     *
     * @param consumer
     */
    @Override
    public boolean insert(Consumer consumer) {
        return consumerMapper.insert(consumer)>0;
    }

    /**
     * 修改
     *
     * @param consumer
     */
    @Override
    public boolean update(Consumer consumer) {
        return consumerMapper.update(consumer)>0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return consumerMapper.delete(id)>0;
    }

    /**
     * 根据主键查询整个对象
     *
     * @param id
     */
    @Override
    public Consumer selectByPrimaryKey(Integer id) {
        return consumerMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有用户
     */
    @Override
    public List<Consumer> allConsumer() {
        return consumerMapper.allConsumer();
    }

    /**
     * 验证用户名密码
     *
     * @param username
     * @param password
     */
    @Override
    public boolean verifyPassword(String username, String password) {
        return consumerMapper.verifyPassword(username,password)>0;
    }

    /**
     * 根据用户名字模糊查询列表
     *
     * @param name
     */
    @Override
    public List<Consumer> consumerOfName(String name) {
        return consumerMapper.consumerOfName(name);
    }

    @Override
    public List<Consumer> consumerLikeName(String name) {
        return consumerMapper.consumerLikeName(name);
    }

    /**
     * 根据歌手
     *
     * @param phoneNum
     */
    @Override
    public List<Consumer> consumerOfPhoneNum(String phoneNum) {
        return consumerMapper.consumerOfPhoneNum(phoneNum);
    }
}
