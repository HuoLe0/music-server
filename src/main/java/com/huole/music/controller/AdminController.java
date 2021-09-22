package com.huole.music.controller;

import com.huole.music.service.AdminService;
import com.huole.music.service.ReturnService;
import com.huole.music.utils.Consts;
import com.huole.music.utils.ResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ReturnService returnService;

    /**
     * 判断是否访问成功
     */
    @PostMapping("/admin/login/status")
    public Object loginStatus(String name, String password, HttpSession session){
        boolean flag = adminService.verifyPassword(name,password);
        if (flag){
            returnService.setSuccess(ResponseEnum.LOGIN_SUCCESS.isSuccess());
            returnService.setMsg(ResponseEnum.LOGIN_SUCCESS.getMsg());
            returnService.setTimestamp(System.currentTimeMillis()/1000);
            session.setAttribute(Consts.NAME,name);
            return returnService.getReturnValue();
        }
        returnService.setSuccess(ResponseEnum.LOGIN_FAILED.isSuccess());
        returnService.setMsg(ResponseEnum.LOGIN_FAILED.getMsg());
        returnService.setTimestamp(System.currentTimeMillis()/1000);
        return returnService.getReturnValue();
    }
}
