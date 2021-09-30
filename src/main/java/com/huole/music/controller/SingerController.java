package com.huole.music.controller;

import com.huole.music.model.ResultModel;
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


    /**
     * 添加歌手
     */
    @PostMapping("/add")
    public Object addSinger(String name, Byte sex, String pic, String birth, String location, String introduction){
        ResultModel resultModel = new ResultModel();
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
            resultModel.setSuccess(ResponseEnum.ADD_SUCCESS.isSuccess());
            resultModel.setCode(ResponseEnum.ADD_SUCCESS.getCode());
            resultModel.setMsg(ResponseEnum.ADD_SUCCESS.getMsg());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        }
        resultModel.setSuccess(ResponseEnum.ADD_FAILED.isSuccess());
        resultModel.setCode(ResponseEnum.ADD_FAILED.getCode());
        resultModel.setMsg(ResponseEnum.ADD_FAILED.getMsg());
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    /**
     * 更新歌手
     */
    @PostMapping("/update")
    public Object updateSinger(Integer id, String name, Byte sex, String pic, String birth, String location, String introduction){
        ResultModel resultModel = new ResultModel();
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
            resultModel.setSuccess(ResponseEnum.MODIFY_SUCCESS.isSuccess());
            resultModel.setCode(ResponseEnum.MODIFY_SUCCESS.getCode());
            resultModel.setMsg(ResponseEnum.MODIFY_SUCCESS.getMsg());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        }
        resultModel.setSuccess(ResponseEnum.MODIFY_FAILED.isSuccess());
        resultModel.setCode(ResponseEnum.MODIFY_FAILED.getCode());
        resultModel.setMsg(ResponseEnum.MODIFY_FAILED.getMsg());
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
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
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setData(singerService.selectById(id));
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    /**
     * 查询所有歌手
     */
    @GetMapping("/selectAll")
    public Object selectAll(){
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setData(singerService.selectAll());
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    /**
     * 查询所有歌手--分页
     */
    @GetMapping("/selectByPager")
    public Object selectByPager(Integer page, Integer size){
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setData(singerService.selectByPager(page, size));
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    /**
     * 查询前十个歌手
     */
    @GetMapping("/selectTen")
    public Object selectTen(){
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setData(singerService.selectTen());
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    /**
     * 根据歌手名字模糊查询列表
     */
    @GetMapping("/selectLikeName")
    public Object selectLikeName(String name){
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setData(singerService.selectLikeName(name));
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    /**
     * 根据歌手性别查询列表
     */
    @GetMapping("/selectBySex")
    public Object selectBySex(Integer sex, Integer page, Integer size){
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setData(singerService.selectBySex(sex, page, size));
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    /**
     * 更新歌手图片
     */
    @PostMapping("/updateSingerPic")
    public Object updateSingerPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") int id){
        ResultModel resultModel = new ResultModel();
        Singer singer = singerService.selectById(id);
        String url = "." + singer.getPic();
        if (!url.equals("./img/singerPic/user.jpg")){
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
                resultModel.setCode(ResponseEnum.UPLOAD_SUCCESS.getCode());
                resultModel.setSuccess(ResponseEnum.UPLOAD_SUCCESS.isSuccess());
                resultModel.setMsg(ResponseEnum.UPLOAD_SUCCESS.getMsg());
                resultModel.setExt("avator" + avatorFile);
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
}
