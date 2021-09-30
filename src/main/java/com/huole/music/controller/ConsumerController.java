package com.huole.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.huole.music.model.Consumer;
import com.huole.music.model.ResultModel;
import com.huole.music.service.ConsumerService;
import com.huole.music.utils.Consts;
import com.huole.music.utils.MD5Utils;
import com.huole.music.utils.ResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 用户控制类
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    /**
     * 添加歌手
     */
    @PostMapping("/add")
    public Object addConsumer(String username, String password, String sex, String phoneNum, String email, String birth, String introduction, String location, String avator){
        ResultModel resultModel = new ResultModel();
        password = MD5Utils.code(password);
        if (username == null || username.equals("")){
            resultModel.setSuccess(ResponseEnum.ERROR.isSuccess());
            resultModel.setCode(ResponseEnum.ERROR.getCode());
            resultModel.setMsg("用户名不能为空");
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        }
        List<Consumer> consumer1 = consumerService.selectByName(username);
        if (!consumer1.isEmpty()){
            resultModel.setSuccess(ResponseEnum.ERROR.isSuccess());
            resultModel.setCode(ResponseEnum.ERROR.getCode());
            resultModel.setMsg("用户名已存在");
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        }

        if (password == null || password.equals("")){
            resultModel.setSuccess(ResponseEnum.ERROR.isSuccess());
            resultModel.setCode(ResponseEnum.ERROR.getCode());
            resultModel.setMsg("密码不能为空");
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        }

        //把生日转换成Date格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        try {
            birthDate = dateFormat.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //保存到歌手对象中
        Consumer consumer = new Consumer();
        consumer.setUsername(username);
        consumer.setPassword(password);
        consumer.setSex(sex);
        consumer.setPhoneNum(phoneNum);
        consumer.setEmail(email);
        consumer.setBirth(birthDate);
        consumer.setLocation(location);
        consumer.setIntroduction(introduction);
        consumer.setAvator(avator);
        boolean flag = consumerService.insert(consumer);
        System.out.println(flag);
        if (flag){//保存成功
            resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
            resultModel.setCode(ResponseEnum.SUCCESS.getCode());
            resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        }
        resultModel.setSuccess(ResponseEnum.ERROR.isSuccess());
        resultModel.setCode(ResponseEnum.ERROR.getCode());
        resultModel.setMsg("注册失败");
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    /**
     * 更新用户
     */
    @PostMapping("/update")
    public Object updateConsumer(Integer id, String username, String password, String sex, String phoneNum, String email, String birth, String introduction, String location, String avator){
        ResultModel resultModel = new ResultModel();
        password = MD5Utils.code(password);
        //把生日转换成Date格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        try {
            birthDate = dateFormat.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //保存到歌手对象中
        Consumer consumer = new Consumer();
        consumer.setId(id);
        consumer.setUsername(username);
        consumer.setPassword(password);
        consumer.setSex(sex);
        consumer.setPhoneNum(phoneNum);
        consumer.setEmail(email);
        consumer.setBirth(birthDate);
        consumer.setLocation(location);
        consumer.setIntroduction(introduction);
        boolean flag = consumerService.update(consumer);
        if (flag){//保存成功
            resultModel.setSuccess(ResponseEnum.MODIFY_SUCCESS.isSuccess());
            resultModel.setCode(ResponseEnum.MODIFY_SUCCESS.getCode());
            resultModel.setData(username);
            resultModel.setMsg(ResponseEnum.MODIFY_SUCCESS.getMsg());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        }
        resultModel.setSuccess(ResponseEnum.MODIFY_FAILED.isSuccess());
        resultModel.setCode(ResponseEnum.MODIFY_FAILED.getCode());
        resultModel.setData(username);
        resultModel.setMsg(ResponseEnum.MODIFY_FAILED.getMsg());
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    /**
     * 删除用户
     * @return
     */
    @GetMapping("/delete")
    public Object deleteConsumer(Integer id){
        Consumer consumer = consumerService.selectById(id);
        String url = "." + consumer.getAvator();
        FileSystemUtils.deleteRecursively(new File(url));
        return consumerService.delete(id);
    }

    /**
     * 根据主键查询整个对象
     */
    @GetMapping("/selectById")
    public Object selectById(Integer id){
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setData(consumerService.selectById(id));
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    /**
     * 查询所有用户
     */
    @GetMapping("/selectAll")
    public Object selectAll(){
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setData(consumerService.selectAll());
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    /**
     * 根据名字模糊查询列表
     */
    @GetMapping("/selectByName")
    public Object selectByName(String username){
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setData(consumerService.selectByName(username));
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }
    /**
     * 根据名字模糊查询列表
     */
    @GetMapping("/selectLikeName")
    public Object selectLikeName(String username){
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setData(consumerService.selectLikeName(username));
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }
    /**
     * 根据电话查询列表
     */
    @GetMapping("/selectByPhoneNum")
    public Object selectByPhoneNum(String phoneNum){
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setData(consumerService.selectByPhoneNum(phoneNum));
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    /**
     * 检验
     */
    @GetMapping("/verifyPassword")
    public Object verifyPassword(String username, String password){
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setData(consumerService.verifyPassword(username,password));
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    /**
     * 更新用户图片
     */
    @PostMapping("/updateConsumerAvator")
    public Object updateConsumerPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") int id){
        ResultModel resultModel = new ResultModel();
        Consumer consumer = consumerService.selectById(id);
        String url = "." + consumer.getAvator();
        if (!url.equals("./img/consumerAvator/user.jpg")){
            FileSystemUtils.deleteRecursively(new File(url));
        }
        if (avatorFile.isEmpty()){
            resultModel.setCode(ResponseEnum.UPLOAD_FAILED.getCode());
            resultModel.setSuccess(ResponseEnum.UPLOAD_FAILED.isSuccess());
            resultModel.setMsg(ResponseEnum.UPLOAD_FAILED.getMsg());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        }
        //文件名=当前时间（ms）+原来的文件名
        String filename = System.currentTimeMillis() + avatorFile.getOriginalFilename();
        //文件路径
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                +System.getProperty("file.separator")+"consumerAvator";
        //如果文件路劲不存在，新增路径
        File file1 = new File(filePath);
        if (!file1.exists()){
            file1.mkdir();
        }
        //实际的文件路径
        File dest = new File(filePath + System.getProperty("file.separator") + filename);
        //存储到数据库里的相对地址文件
        String storeAvatorPath = "/img/consumerAvator/" + filename;
        try {
            avatorFile.transferTo(dest);
            consumer.setId(id);
            consumer.setAvator(storeAvatorPath);
            consumerService.update(consumer);
            boolean flag = consumerService.update(consumer);
            if (flag){//保存成功
                resultModel.setCode(ResponseEnum.UPLOAD_SUCCESS.getCode());
                resultModel.setSuccess(ResponseEnum.UPLOAD_SUCCESS.isSuccess());
                resultModel.setMsg(ResponseEnum.UPLOAD_SUCCESS.getMsg());
                resultModel.setExt("avator" + storeAvatorPath);
                resultModel.setTimestamp(System.currentTimeMillis()/1000);
                return resultModel;
            }
            resultModel.setCode(ResponseEnum.UPLOAD_FAILED.getCode());
            resultModel.setSuccess(ResponseEnum.UPLOAD_FAILED.isSuccess());
            resultModel.setMsg(ResponseEnum.UPLOAD_FAILED.getMsg());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        } catch (IOException e) {
            e.printStackTrace();
            resultModel.setCode(ResponseEnum.UPLOAD_FAILED.getCode());
            resultModel.setSuccess(ResponseEnum.UPLOAD_FAILED.isSuccess());
            resultModel.setMsg(e.getMessage());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
        }finally {
            return resultModel;
        }
    }

    @PostMapping("/login")
    public Object Login(String username, String password){
        password = MD5Utils.code(password);
        ResultModel resultModel = new ResultModel();
        if (username == null || username.equals("")){
            resultModel.setSuccess(ResponseEnum.LOGIN_FAILED.isSuccess());
            resultModel.setCode(ResponseEnum.LOGIN_FAILED.getCode());
            resultModel.setMsg("用户名不能为空");
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        }
        if (password == null || password.equals("")){
            resultModel.setSuccess(ResponseEnum.LOGIN_FAILED.isSuccess());
            resultModel.setCode(ResponseEnum.LOGIN_FAILED.getCode());
            resultModel.setMsg("密码不能为空");
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        }

        Consumer consumer = new Consumer();
        consumer.setUsername(username);
        consumer.setPassword(password);
        boolean flag = consumerService.verifyPassword(username,password);
        if (flag){
            resultModel.setSuccess(ResponseEnum.LOGIN_SUCCESS.isSuccess());
            resultModel.setCode(ResponseEnum.LOGIN_SUCCESS.getCode());
            resultModel.setMsg(ResponseEnum.LOGIN_SUCCESS.getMsg());
            resultModel.setData(consumerService.selectByName(username));
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        }
        resultModel.setSuccess(ResponseEnum.LOGIN_FAILED.isSuccess());
        resultModel.setCode(ResponseEnum.LOGIN_FAILED.getCode());
        resultModel.setMsg(ResponseEnum.LOGIN_FAILED.getMsg());
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }
}
