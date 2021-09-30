package com.huole.music.controller;

import com.huole.music.model.ResultModel;
import com.huole.music.service.AdminService;
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


    /**
     * 判断是否访问成功
     */
    @PostMapping("/admin/login/status")
    public Object loginStatus(String name, String password, HttpSession session){
        ResultModel resultModel = new ResultModel();
        boolean flag = adminService.verifyPassword(name,password);
        if (flag){
            resultModel.setSuccess(ResponseEnum.LOGIN_SUCCESS.isSuccess());
            resultModel.setCode(ResponseEnum.LOGIN_SUCCESS.getCode());
            resultModel.setMsg(ResponseEnum.LOGIN_SUCCESS.getMsg());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            session.setAttribute(Consts.NAME,name);
            return resultModel;
        }
        resultModel.setSuccess(ResponseEnum.LOGIN_FAILED.isSuccess());
        resultModel.setCode(ResponseEnum.LOGIN_FAILED.getCode());
        resultModel.setMsg(ResponseEnum.LOGIN_FAILED.getMsg());
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }
}
