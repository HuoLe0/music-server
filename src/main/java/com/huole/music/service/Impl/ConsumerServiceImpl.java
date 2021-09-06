package com.huole.music.service.Impl;

import com.huole.music.dao.ConsumerMapper;
import com.huole.music.model.Consumer;
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
     * 根据id查询用户
     *
     * @param id
     */
    @Override
    public Consumer selectById(Integer id) {
        return consumerMapper.selectById(id);
    }

    /**
     * 查询所有用户
     */
    @Override
    public List<Consumer> selectAll() {
        return consumerMapper.selectAll();
    }

    /**
     * 验证密码
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public boolean verifyPassword(String username, String password) {
        return consumerMapper.verifyPassword(username, password) > 0;
    }

    /**
     * 根据名字查询用户
     *
     * @param username
     */
    @Override
    public List<Consumer> selectByName(String username) {
        return consumerMapper.selectByName(username);
    }

    /**
     * 根据名字模糊查询用户
     *
     * @param username
     */
    @Override
    public List<Consumer> selectLikeName(String username) {
        return consumerMapper.selectLikeName(username);
    }

    /**
     * 根据用户手机号查询用户
     *
     * @param phoneNum
     */
    @Override
    public List<Consumer> selectByPhoneNum(String phoneNum) {
        return selectByPhoneNum(phoneNum);
    }
}
