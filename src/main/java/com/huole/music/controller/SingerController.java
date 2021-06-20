package com.huole.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.huole.music.domain.Singer;
import com.huole.music.domain.Song;
import com.huole.music.service.SingerService;
import com.huole.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public Object addSinger(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String name = request.getParameter("name").trim();//姓名
        String sex = request.getParameter("sex").trim();//性别
        String pic = request.getParameter("pic").trim();//头像
        String birth = request.getParameter("birth").trim();//生日
        String location = request.getParameter("location").trim();//地区
        String introduction = request.getParameter("introduction").trim();//简介
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
        Singer singer = new Singer();
        singer.setName(name);
        singer.setSex(new Byte(sex));
        singer.setPic(pic);
        singer.setBirth(birthDate);
        singer.setLocation(location);
        singer.setIntroduction(introduction);
        boolean flag = singerService.insert(singer);
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
     * 更新歌手
     * @param request
     * @return
     */
    @PostMapping("/update")
    public Object updateSinger(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();//主键
        String name = request.getParameter("name").trim();//姓名
        String sex = request.getParameter("sex").trim();//性别
//        String pic = request.getParameter("pic").trim();//头像
        String birth = request.getParameter("birth").trim();//生日
        String location = request.getParameter("location").trim();//地区
        String introduction = request.getParameter("introduction").trim();//简介
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
        Singer singer = new Singer();
        singer.setId(Integer.parseInt(id));
        singer.setName(name);
        singer.setSex(new Byte(sex));
//        singer.setPic(pic);
        singer.setBirth(birthDate);
        singer.setLocation(location);
        singer.setIntroduction(introduction);
        boolean flag = singerService.update(singer);
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
     * 删除歌手
     * @param request
     * @return
     */
    @GetMapping("/delete")
    public Object deleteSinger(HttpServletRequest request){
        String id = request.getParameter("id").trim();//主键
        Singer singer = singerService.selectById(Integer.parseInt(id));
        String url = "." + singer.getPic();
        FileSystemUtils.deleteRecursively(new File(url));
        boolean flag = singerService.delete(Integer.parseInt(id));
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
        result.put("data", singerService.selectById(Integer.parseInt(id)));
        return result;
    }

    /**
     * 查询所有歌手
     */
    @GetMapping("/selectAll")
    public Object selectAll(){
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", singerService.selectAll());
        return result;
    }

    /**
     * 查询所有歌手--分页
     * @return
     */
    @GetMapping("/selectByPager")
    public Object selectByPager(HttpServletRequest request){
        String page = request.getParameter("page").trim();
        String size = request.getParameter("size").trim();
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", singerService.selectByPager(Integer.parseInt(page), Integer.parseInt(size)));
        return result;
    }

    /**
     * 查询前十个歌手
     * @return
     */
    @GetMapping("/selectTen")
    public Object selectTen(){
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", singerService.selectTen());
        return result;
    }

    /**
     * 根据歌手名字模糊查询列表
     */
    @GetMapping("/selectLikeName")
    public Object selectLikeName(HttpServletRequest request){
        String name = request.getParameter("name").trim();
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", singerService.selectLikeName(name));
        return result;
    }

    /**
     * 根据歌手性别查询列表
     */
    @GetMapping("/selectBySex")
    public Object selectBySex(HttpServletRequest request){
        String sex = request.getParameter("sex").trim();
        String page = request.getParameter("page").trim();
        String size = request.getParameter("size").trim();
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", singerService.selectBySex(Integer.parseInt(sex), Integer.parseInt(page), Integer.parseInt(size)));
        return result;
    }

    /**
     * 更新歌手图片
     */
    @PostMapping("/updateSingerPic")
    public Object updateSingerPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") int id){
        Singer singer = singerService.selectById(id);
        String url = "." + singer.getPic();
//        System.out.println(url);
        if (!url.equals("./img/singerPic/user.jpg")){
            FileSystemUtils.deleteRecursively(new File(url));
        }
        JSONObject jsonObject = new JSONObject();
        if (avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put("success", false);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
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
//            Singer singer = new Singer();
            singer.setId(id);
            singer.setPic(storeAvatorPath);
            singerService.update(singer);
            boolean flag = singerService.update(singer);
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
}
