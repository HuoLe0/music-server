package com.huole.music.service;

import org.springframework.stereotype.Service;

/**
 * 管理员service接口
 */

public interface AdminService {
    /**
     * 验证密码是否正确
     */
    public boolean verifyPassword(String name,String password);
}
