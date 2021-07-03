package com.huole.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.huole.music.domain.Consumer;
import com.huole.music.service.ConsumerService;
import com.huole.music.utils.Consts;
import com.huole.music.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
        JSONObject jsonObject = new JSONObject();
        password = MD5Utils.code(password);
        if (username == null || username.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"用户名不能为空");
            return jsonObject;
        }

        List<Consumer> consumer1 = consumerService.selectByName(username);
        if (!consumer1.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"用户名已存在");
            return jsonObject;
        }

        if (password == null || password.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"密码不能为空");
            return jsonObject;
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
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"添加成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"添加失败");
        return jsonObject;
    }

    /**
     * 更新用户
     */
    @PostMapping("/update")
    public Object updateConsumer(Integer id, String username, String password, String sex, String phoneNum, String email, String birth, String introduction, String location, String avator){
        JSONObject jsonObject = new JSONObject();
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
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"修改失败");
        return jsonObject;
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
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", consumerService.selectById(id));
        return result;
    }

    /**
     * 查询所有用户
     */
    @GetMapping("/selectAll")
    public Object selectAll(){
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", consumerService.selectAll());
        return result;
    }

    /**
     * 根据名字模糊查询列表
     */
    @GetMapping("/selectByName")
    public Object selectByName(String username){
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", consumerService.selectByName(username));
        return result;
    }
    /**
     * 根据名字模糊查询列表
     */
    @GetMapping("/selectLikeName")
    public Object selectLikeName(String username){
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", consumerService.selectLikeName(username));
        return result;
    }
    /**
     * 根据电话查询列表
     */
    @GetMapping("/selectByPhoneNum")
    public Object selectByPhoneNum(String phoneNum){
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", consumerService.selectByPhoneNum(phoneNum));
        return result;
    }

    /**
     * 检验
     */
    @GetMapping("/verifyPassword")
    public Object verifyPassword(String username, String password){
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", consumerService.verifyPassword(username,password));
        return result;
    }

    /**
     * 更新歌手图片
     */
    @PostMapping("/updateConsumerAvator")
    public Object updateConsumerPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") int id){
        Consumer consumer = consumerService.selectById(id);
        String url = "." + consumer.getAvator();
        if (!url.equals("./img/consumerAvator/user.jpg")){
            FileSystemUtils.deleteRecursively(new File(url));
        }
        JSONObject jsonObject = new JSONObject();
        if (avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put("success", true);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
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
                jsonObject.put(Consts.CODE,1);
                jsonObject.put("success", true);
                jsonObject.put(Consts.MSG,"修改成功");
                jsonObject.put("pic",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put("success", false);
            jsonObject.put(Consts.MSG,"修改失败");

            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
            jsonObject.put(Consts.CODE,0);
            jsonObject.put("success", false);
            jsonObject.put(Consts.MSG,"修改失败"+e.getMessage());
        }finally {
            return jsonObject;
        }
    }

    @PostMapping("/login")
    public Object Login(String username, String password){
        password = MD5Utils.code(password);
        JSONObject jsonObject = new JSONObject();
        if (username == null || username.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put("success", false);
            jsonObject.put(Consts.MSG,"用户名不能为空");
            return jsonObject;
        }
        if (password == null || password.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put("success", false);
            jsonObject.put(Consts.MSG,"密码不能为空");
            return jsonObject;
        }

        Consumer consumer = new Consumer();
        consumer.setUsername(username);
        consumer.setPassword(password);
        boolean flag = consumerService.verifyPassword(username,password);
        if (flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put("success", true);
            jsonObject.put(Consts.MSG,"登陆成功");
            jsonObject.put("userMsg",consumerService.selectByName(username));
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put("success",false);
        jsonObject.put(Consts.MSG,"登陆失败");
        return jsonObject;
    }
}
