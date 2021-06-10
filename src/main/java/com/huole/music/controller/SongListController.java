package com.huole.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.huole.music.domain.Song;
import com.huole.music.domain.SongList;
import com.huole.music.domain.SongList;
import com.huole.music.service.SongListService;
import com.huole.music.utils.Consts;
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
 * 歌单控制类
 */
@RestController
@RequestMapping("/songList")
public class SongListController {
    @Autowired
    private SongListService songListService;

    /**
     * 添加歌单
     */
    @PostMapping("/add")
    public Object addSongList(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String title = request.getParameter("title").trim();//标题
        String pic = request.getParameter("pic").trim();//封面
        String introduction = request.getParameter("introduction").trim();//简介
        String style = request.getParameter("style").trim();//风格
//        System.out.println();
        //保存到歌单对象中
        SongList songList = new SongList();
        songList.setTitle(title);
        songList.setPic(pic);
        songList.setIntroduction(introduction);
        songList.setStyle(style);
        boolean flag = songListService.insert(songList);
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
     * 前端用户添加歌单
     */
    @PostMapping("/addByConsumer")
    public Object addByConsumer(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String title = request.getParameter("title").trim();//标题
        String pic = request.getParameter("pic").trim();//封面
        String introduction = request.getParameter("introduction").trim();//简介
        String style = request.getParameter("style").trim();//风格
        String userId = request.getParameter("userId").trim();//用户id

        //保存到歌单对象中
        SongList songList = new SongList();
        songList.setTitle(title);
        songList.setPic(pic);
        songList.setIntroduction(introduction);
        songList.setStyle(style);
        songList.setUserId(Integer.parseInt(userId));
        boolean flag = songListService.insertByConsumer(songList);
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
     * 更新歌单
     * @param request
     * @return
     */
    @PostMapping("/update")
    public Object updateSongList(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();//主键
        String title = request.getParameter("title").trim();//标题
//        String pic = request.getParameter("pic").trim();//封面
        String introduction = request.getParameter("introduction").trim();//简介
        String style = request.getParameter("style").trim();//风格
        //保存到歌手对象中
        SongList songList = new SongList();
        songList.setId(Integer.parseInt(id));
        songList.setTitle(title);
//        songList.setPic(pic);
        songList.setIntroduction(introduction);
        songList.setStyle(style);
        boolean flag = songListService.update(songList);
        if (flag){//保存成功
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"修改失败");
        return jsonObject;
    }

    @GetMapping("/addSong")
    public Object addSong(HttpServletRequest request) {
        String songId = request.getParameter("songId").trim();//歌曲id
        String songListId = request.getParameter("songListId").trim();//歌单id
        boolean flag = songListService.addSong(Integer.parseInt(songId), Integer.parseInt(songListId));
        return flag;
    }
    /**
     * 根据id删除歌单
     * @param request
     * @return
     */
    @GetMapping("/delete")
    public Object delSongList(HttpServletRequest request){
        String id = request.getParameter("id").trim();
        boolean flag = songListService.delete(Integer.parseInt(id));
        return flag;
    }

    /**
     * 根据主键查询歌dan
     * @param request
     * @return
     */
    @GetMapping("/selectById")
    public Object selectById(HttpServletRequest request){
        String id = request.getParameter("id").trim();
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", songListService.selectById(Integer.parseInt(id)));
        return result;
    }

    /**
     * 查询所有歌单
     * @return
     */
    @GetMapping("/selectAll")
    public Object selectAll(){
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", songListService.selectAll());
        return result;
    }

    @GetMapping("/selectSongs")
    public Object selectSongs(HttpServletRequest request) {
        String songListId = request.getParameter("songListId").trim();//歌单id
        String songs = songListService.selectById(Integer.parseInt(songListId)).getSongs();
        System.out.println(songs);
        String[] songIds = songs.split(",");
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", songListService.selectSongs(songIds));
        return result;
    }

    /**
     * 查询用户所有歌单
     * @return
     */
    @GetMapping("/selectAllConsumer")
    public Object selectAllConsumer(HttpServletRequest request){
        String userId = request.getParameter("userId").trim();
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", songListService.selectAllConsumer(Integer.parseInt(userId)));
        return result;
    }

    /**
     * 查询前十个歌单
     * @return
     */
    @GetMapping("/selectTen")
    public Object selectTen(){
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", songListService.selectTen());
        return result;
    }

    /**
     * 根据标题精确查找
     * @param request
     * @return
     */
    @GetMapping("/selectByTitle")
    public Object selectByTitle(HttpServletRequest request){
        String title = request.getParameter("title").trim();
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", songListService.selectByTitle(title));
        return result;
    }

    /**
     * 根据标题模糊查找
     * @param request
     * @return
     */
    @GetMapping("/selectLikeTitle")
    public Object selectLikeTitle(HttpServletRequest request){
        String title = request.getParameter("title").trim();
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", songListService.selectLikeTitle(title));
        return result;
    }

    /**
     * 根据风格模糊查找
     * @param request
     * @return
     */
    @GetMapping("/selectLikeStyle")
    public Object selectLikeStyle(HttpServletRequest request){
        String style = request.getParameter("style").trim();
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", songListService.selectLikeStyle(style));
        return result;
    }


    /**
     * 更新歌曲图片
     */
    @PostMapping("/updateSongListPic")
    public Object updateSongListPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") int id){
        SongList songList = songListService.selectById(id);
        String pic = "." + songList.getPic();
//        System.out.println(url);
        if (!pic.equals("./img/songListPic/list.jpg")){
            FileSystemUtils.deleteRecursively(new File(pic));
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
                +System.getProperty("file.separator")+"songListPic";
        //如果文件路劲不存在，新增路径
        File file1 = new File(filePath);
        if (!file1.exists()){
            file1.mkdir();
        }
        //实际的文件路径
        File dest = new File(filePath + System.getProperty("file.separator") + filename);
        //存储到数据库里的相对地址文件
        String storeAvatorPath = "/img/songListPic/" + filename;
        try {
            avatorFile.transferTo(dest);
            songList.setId(id);
            songList.setPic(storeAvatorPath);
            songListService.update(songList);
            boolean flag = songListService.update(songList);
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
