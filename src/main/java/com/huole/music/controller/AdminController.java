package com.huole.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.huole.music.service.AdminService;
import com.huole.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 判断是否访问成功
     */
    @PostMapping("/admin/login/status")
    public Object loginStatus(String name, String password, HttpSession session){
        JSONObject jsonObject = new JSONObject();
        boolean flag = adminService.verifyPassword(name,password);
        if (flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"登陆成功");
            session.setAttribute(Consts.NAME,name);
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"用户名或密码错误");
        return jsonObject;
    }
}
