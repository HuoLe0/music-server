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
    public Object addConsumer(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String username = request.getParameter("username").trim();//姓名
        String password = request.getParameter("password").trim();//密码
        password = MD5Utils.code(password);
        String sex = request.getParameter("sex").trim();//性别
        String phoneNum = request.getParameter("phoneNum").trim();//电话
        String email = request.getParameter("email").trim();//邮箱
        String birth = request.getParameter("birth").trim();//生日
        String introduction = request.getParameter("introduction").trim();//简介
        String location = request.getParameter("location").trim();//地区
        String avator = request.getParameter("avator").trim();//头像

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
//        System.out.println(birth);
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
     * @param request
     * @return
     */
    @PostMapping("/update")
    public Object updateConsumer(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();//id
        String username = request.getParameter("username").trim();//姓名
        String password = request.getParameter("password").trim();//密码
        password = MD5Utils.code(password);
        String sex = request.getParameter("sex").trim();//性别
        String phoneNum = request.getParameter("phoneNum").trim();//电话
        String email = request.getParameter("email").trim();//邮箱
        String birth = request.getParameter("birth").trim();//生日
        String introduction = request.getParameter("introduction").trim();//简介
        String location = request.getParameter("location").trim();//地区
//        String avator = request.getParameter("avator").trim();//头像
        //把生日转换成Date格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
//        System.out.println(birth);
        try {
            birthDate = dateFormat.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //保存到歌手对象中
        Consumer consumer = new Consumer();
        consumer.setId(Integer.parseInt(id));
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
     * 删除y用户
     * @param request
     * @return
     */
    @GetMapping("/delete")
    public Object deleteConsumer(HttpServletRequest request){
        String id = request.getParameter("id").trim();//主键
        Consumer consumer = consumerService.selectById(Integer.parseInt(id));
        String url = "." + consumer.getAvator();
        FileSystemUtils.deleteRecursively(new File(url));
        boolean flag = consumerService.delete(Integer.parseInt(id));
        return flag;
    }

    /**
     * 根据主键查询整个对象
     */
    @GetMapping("/selectById")
    public Object selectById(HttpServletRequest request){
        String id = request.getParameter("id").trim();//主键
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", consumerService.selectById(Integer.parseInt(id)));
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
    public Object selectByName(HttpServletRequest request){
        String username = request.getParameter("username").trim();
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", consumerService.selectByName(username));
        return result;
    }
    /**
     * 根据名字模糊查询列表
     */
    @GetMapping("/selectLikeName")
    public Object selectLikeName(HttpServletRequest request){
        String username = request.getParameter("username").trim();
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", consumerService.selectLikeName(username));
        return result;
    }
    /**
     * 根据电话查询列表
     */
    @GetMapping("/selectByPhoneNum")
    public Object selectByPhoneNum(HttpServletRequest request){
        String phoneNum = request.getParameter("phoneNum").trim();
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", consumerService.selectByPhoneNum(phoneNum));
        return result;
    }

    /**
     * 检验
     */
    @GetMapping("/verifyPassword")
    public Object verifyPassword(HttpServletRequest request){
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
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
//        System.out.println(url);
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
//            Consumer consumer = new Consumer();
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
    public Object Login(HttpServletRequest request){
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
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
