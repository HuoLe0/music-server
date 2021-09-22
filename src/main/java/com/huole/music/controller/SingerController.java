package com.huole.music.controller;

import com.huole.music.model.Singer;
import com.huole.music.service.ReturnService;
import com.huole.music.service.SingerService;
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

/**
 * 歌手控制类
 */
@RestController
@RequestMapping("/singer")
public class SingerController {

    @Autowired
    private SingerService singerService;

    @Autowired
    private ReturnService returnService;


    /**
     * 添加歌手
     */
    @PostMapping("/add")
    public Object addSinger(String name, Byte sex, String pic, String birth, String location, String introduction){
        //把生日转换成Date格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        try {
            birthDate = dateFormat.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //保存到歌手对象中
        Singer singer = new Singer();
        singer.setName(name);
        singer.setSex(sex);
        singer.setPic(pic);
        singer.setBirth(birthDate);
        singer.setLocation(location);
        singer.setIntroduction(introduction);
        boolean flag = singerService.insert(singer);
        if (flag){//保存成功
            returnService.setSuccess(ResponseEnum.ADD_SUCCESS.isSuccess());
            returnService.setCode(ResponseEnum.ADD_SUCCESS.getCode());
            returnService.setMsg(ResponseEnum.ADD_SUCCESS.getMsg());
            returnService.setTimestamp(System.currentTimeMillis()/1000);
            return returnService.getReturnValue();
        }
        returnService.setSuccess(ResponseEnum.ADD_FAILED.isSuccess());
        returnService.setCode(ResponseEnum.ADD_FAILED.getCode());
        returnService.setMsg(ResponseEnum.ADD_FAILED.getMsg());
        returnService.setTimestamp(System.currentTimeMillis()/1000);
        return returnService.getReturnValue();
    }

    /**
     * 更新歌手
     */
    @PostMapping("/update")
    public Object updateSinger(Integer id, String name, Byte sex, String pic, String birth, String location, String introduction){
        //把生日转换成Date格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        try {
            birthDate = dateFormat.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //保存到歌手对象中
        Singer singer = new Singer();
        singer.setId(id);
        singer.setName(name);
        singer.setSex(sex);
        singer.setBirth(birthDate);
        singer.setLocation(location);
        singer.setIntroduction(introduction);
        boolean flag = singerService.update(singer);
        if (flag){//保存成功
            returnService.setSuccess(ResponseEnum.MODIFY_SUCCESS.isSuccess());
            returnService.setCode(ResponseEnum.MODIFY_SUCCESS.getCode());
            returnService.setMsg(ResponseEnum.MODIFY_SUCCESS.getMsg());
            returnService.setTimestamp(System.currentTimeMillis()/1000);
            return returnService.getReturnValue();
        }
        returnService.setSuccess(ResponseEnum.MODIFY_FAILED.isSuccess());
        returnService.setCode(ResponseEnum.MODIFY_FAILED.getCode());
        returnService.setMsg(ResponseEnum.MODIFY_FAILED.getMsg());
        returnService.setTimestamp(System.currentTimeMillis()/1000);
        return returnService.getReturnValue();
    }

    /**
     * 删除歌手
     */
    @GetMapping("/delete")
    public Object deleteSinger(Integer id){
        Singer singer = singerService.selectById(id);
        String url = "." + singer.getPic();
        FileSystemUtils.deleteRecursively(new File(url));
        return singerService.delete(id);
    }

    /**
     * 根据主键查询整个对象
     */
    @GetMapping("/selectById")
    public Object selectById(Integer id){
        returnService.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        returnService.setCode(ResponseEnum.SUCCESS.getCode());
        returnService.setMsg(ResponseEnum.SUCCESS.getMsg());
        returnService.setData(singerService.selectById(id));
        returnService.setTimestamp(System.currentTimeMillis()/1000);
        return returnService.getReturnValue();
    }

    /**
     * 查询所有歌手
     */
    @GetMapping("/selectAll")
    public Object selectAll(){
        returnService.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        returnService.setCode(ResponseEnum.SUCCESS.getCode());
        returnService.setMsg(ResponseEnum.SUCCESS.getMsg());
        returnService.setData(singerService.selectAll());
        returnService.setTimestamp(System.currentTimeMillis()/1000);
        return returnService.getReturnValue();
    }

    /**
     * 查询所有歌手--分页
     */
    @GetMapping("/selectByPager")
    public Object selectByPager(Integer page, Integer size){
        returnService.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        returnService.setCode(ResponseEnum.SUCCESS.getCode());
        returnService.setMsg(ResponseEnum.SUCCESS.getMsg());
        returnService.setData(singerService.selectByPager(page, size));
        returnService.setTimestamp(System.currentTimeMillis()/1000);
        return returnService.getReturnValue();
    }

    /**
     * 查询前十个歌手
     */
    @GetMapping("/selectTen")
    public Object selectTen(){
        returnService.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        returnService.setCode(ResponseEnum.SUCCESS.getCode());
        returnService.setMsg(ResponseEnum.SUCCESS.getMsg());
        returnService.setData(singerService.selectTen());
        returnService.setTimestamp(System.currentTimeMillis()/1000);
        return returnService.getReturnValue();
    }

    /**
     * 根据歌手名字模糊查询列表
     */
    @GetMapping("/selectLikeName")
    public Object selectLikeName(String name){
        returnService.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        returnService.setCode(ResponseEnum.SUCCESS.getCode());
        returnService.setMsg(ResponseEnum.SUCCESS.getMsg());
        returnService.setData(singerService.selectLikeName(name));
        returnService.setTimestamp(System.currentTimeMillis()/1000);
        return returnService.getReturnValue();
    }

    /**
     * 根据歌手性别查询列表
     */
    @GetMapping("/selectBySex")
    public Object selectBySex(Integer sex, Integer page, Integer size){
        returnService.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        returnService.setCode(ResponseEnum.SUCCESS.getCode());
        returnService.setMsg(ResponseEnum.SUCCESS.getMsg());
        returnService.setData(singerService.selectBySex(sex, page, size));
        returnService.setTimestamp(System.currentTimeMillis()/1000);
        return returnService.getReturnValue();
    }

    /**
     * 更新歌手图片
     */
    @PostMapping("/updateSingerPic")
    public Object updateSingerPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") int id){
        Singer singer = singerService.selectById(id);
        String url = "." + singer.getPic();
        if (!url.equals("./img/singerPic/user.jpg")){
            FileSystemUtils.deleteRecursively(new File(url));
        }
        if (avatorFile.isEmpty()){
            returnService.setCode(ResponseEnum.UPLOAD_FAILED.getCode());
            returnService.setSuccess(ResponseEnum.UPLOAD_FAILED.isSuccess());
            returnService.setMsg(ResponseEnum.UPLOAD_FAILED.getMsg());
            returnService.setTimestamp(System.currentTimeMillis()/1000);
            return returnService.getReturnValue();
        }
        //文件名=当前时间（ms）+原来的文件名
        String filename = System.currentTimeMillis() + avatorFile.getOriginalFilename();
        //文件路径
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                +System.getProperty("file.separator")+"singerPic";
        //如果文件路劲不存在，新增路径
        File file1 = new File(filePath);
        if (!file1.exists()){
            file1.mkdir();
        }
        //实际的文件路径
        File dest = new File(filePath + System.getProperty("file.separator") + filename);
        //存储到数据库里的相对地址文件
        String storeAvatorPath = "/img/singerPic/" + filename;
        try {
            avatorFile.transferTo(dest);
            singer.setId(id);
            singer.setPic(storeAvatorPath);
            singerService.update(singer);
            boolean flag = singerService.update(singer);
            if (flag){//保存成功
                returnService.setCode(ResponseEnum.UPLOAD_SUCCESS.getCode());
                returnService.setSuccess(ResponseEnum.UPLOAD_SUCCESS.isSuccess());
                returnService.setMsg(ResponseEnum.UPLOAD_SUCCESS.getMsg());
                returnService.setExt("avator" + avatorFile);
                returnService.setTimestamp(System.currentTimeMillis()/1000);
                return returnService.getReturnValue();
            }
            returnService.setCode(ResponseEnum.UPLOAD_FAILED.getCode());
            returnService.setSuccess(ResponseEnum.UPLOAD_FAILED.isSuccess());
            returnService.setMsg(ResponseEnum.UPLOAD_FAILED.getMsg());
            returnService.setTimestamp(System.currentTimeMillis()/1000);
            return returnService.getReturnValue();
        } catch (IOException e) {
            e.printStackTrace();
            returnService.setCode(ResponseEnum.UPLOAD_FAILED.getCode());
            returnService.setSuccess(ResponseEnum.UPLOAD_FAILED.isSuccess());
            returnService.setMsg(ResponseEnum.UPLOAD_FAILED.getMsg());
            returnService.setTimestamp(System.currentTimeMillis()/1000);

        }finally {
            return returnService.getReturnValue();
        }
    }
}
