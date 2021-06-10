package com.huole.music.service.Impl;

import com.huole.music.dao.AdminMapper; 
import com.huole.music.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 管理员Service实现类
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 验证密码是否正确
     *
     * @param name
     * @param password
     */
    @Override
    public boolean verifyPassword(String name, String password) {

        return adminMapper.verifyPassword(name,password)>0;
    }
}
