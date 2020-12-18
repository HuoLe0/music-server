package com.huole.music.controller;


import com.alibaba.fastjson.JSONObject;
import com.huole.music.domain.Singer;
import com.huole.music.domain.Song;
import com.huole.music.domain.Song;
import com.huole.music.service.SongService;
import com.huole.music.utils.Consts;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * 歌曲管理controller
 */
@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongService songService;

    /**
     * 添加歌曲
     */
    @PostMapping("/add")
    public Object addSong(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        JSONObject jsonObject = new JSONObject();
        //获取前端传来的参数
        String singerId = request.getParameter("singerId").trim();//歌手id
        String name = request.getParameter("name").trim();//歌名
        String introduction = request.getParameter("introduction").trim();//简介
        String pic = "/img/songPic/十.jpg";//默认歌曲封面
        String lyric = request.getParameter("lyric").trim();//歌词
//        System.out.println(singerId+"++"+name+"++"+introduction+"++"+lyric);
        if (file.isEmpty()) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "文件上传失败");
            return jsonObject;
        }
        //文件名=当前时间（ms）+原来的文件名
        String filename = System.currentTimeMillis() + file.getOriginalFilename();
        //文件路径
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "song";
        //如果文件路劲不存在，新增路径
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }
        //实际的文件路径
        File dest = new File(filePath + System.getProperty("file.separator") + filename);
        //存储到数据库里的相对地址文件
        String storeSongPath = "/song/" + filename;
        try {
            file.transferTo(dest);
            Song song = new Song();
            song.setSingerId(Integer.parseInt(singerId));
            song.setName(name);
            song.setIntroduction(introduction);
            song.setPic(pic);
            song.setLyric(lyric);
            song.setUrl(storeSongPath);
//            songService.insert(song);
            boolean flag = songService.insert(song);
            if (flag) {//保存成功
                jsonObject.put(Consts.CODE, 1);
                jsonObject.put(Consts.MSG, "上传成功");
                jsonObject.put("avator", storeSongPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "上传失败");

            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "上传失败" + e.getMessage());

        } finally {
            return jsonObject;
        }
    }

    /**
     * 更新歌曲
     * @param request
     * @return
     */
    @PostMapping("/update")
    public Object updateSong(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        //获取前端传来的参数
        String id = request.getParameter("id").trim();//歌手id
        String name = request.getParameter("name").trim();//歌名
        String introduction = request.getParameter("introduction").trim();//简介
        String lyric = request.getParameter("lyric").trim();//歌词
//        System.out.println(id+"++"+name+"++"+introduction+"++"+lyric);
            Song song = new Song();
            song.setId(Integer.parseInt(id));
            song.setName(name);
            song.setIntroduction(introduction);
            song.setLyric(lyric);
            songService.update(song);
            boolean flag = songService.update(song);
            if (flag) {//保存成功
                jsonObject.put(Consts.CODE, 1);
                jsonObject.put(Consts.MSG, "上传成功");
                return jsonObject;
            }
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "上传失败");
            return jsonObject;
    }

    /**
     * 查询所有歌曲
     * @return
     */
    @GetMapping("/allSong")
    public Object allSong(){
        return songService.allSong();
    }

    /**\
     * 根据id删除歌曲
     * @param request
     * @return
     */
    @GetMapping("/delete")
    public Object deleteSong(HttpServletRequest request){
        String id = request.getParameter("id").trim();
        Song song = songService.selectByPrimaryKey(Integer.parseInt(id));
        String url = "." + song.getUrl();
        String pic = "." + song.getPic();
//        System.out.println(url);
        FileSystemUtils.deleteRecursively(new File(url));//移除本地文件
        if (!pic.equals("./img/songPic/Jay.jpg")){
            FileSystemUtils.deleteRecursively(new File(pic));
        }

        boolean flag = songService.delete(Integer.parseInt(id));
        return flag;
    }

    /**
     * 根据主键查询歌曲
     * @param request
     * @return
     */
    @GetMapping("/selectByPrimaryKey")
    public Object selectByPrimaryKey(HttpServletRequest request){
        String id = request.getParameter("id").trim();
        return songService.selectByPrimaryKey(Integer.parseInt(id));
    }

    /**
     * 根据歌名查询
     * @param request
     * @return
     */
    @GetMapping("/songOfName")
    public Object songOfName(HttpServletRequest request){
        String name = request.getParameter("name").trim();
        return songService.songOfName(name);
    }

    @GetMapping("/songLikeName")
    public Object songLikeName(HttpServletRequest request){
        String name = request.getParameter("name").trim();
        return songService.songLikeName(name);
    }
    /**
     * 根据歌手id查询
     * @param request
     * @return
     */
    @GetMapping("/songOfSingerId")
    public Object songOfSingerId(HttpServletRequest request){
        String singerId = request.getParameter("singerId");
        return songService.songOfSingerId(Integer.parseInt(singerId));
    }

    /**
     * 更新歌曲图片
     */
    @PostMapping("/updateSongPic")
    public Object updateSongPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") int id){
        Song song = songService.selectByPrimaryKey(id);
        String pic = "." + song.getPic();
//        System.out.println(url);
        if (!pic.equals("./img/songPic/Jay.jpg")){
            FileSystemUtils.deleteRecursively(new File(pic));
        }
        JSONObject jsonObject = new JSONObject();
        if (avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
        //文件名=当前时间（ms）+原来的文件名
        String filename = System.currentTimeMillis() + avatorFile.getOriginalFilename();
        //文件路径
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                +System.getProperty("file.separator")+"songPic";
        //如果文件路劲不存在，新增路径
        File file1 = new File(filePath);
        if (!file1.exists()){
            file1.mkdir();
        }
        //实际的文件路径
        File dest = new File(filePath + System.getProperty("file.separator") + filename);
        //存储到数据库里的相对地址文件
        String storeAvatorPath = "/img/songPic/" + filename;
        try {
            avatorFile.transferTo(dest);
            song.setId(id);
            song.setPic(storeAvatorPath);
            songService.update(song);
            boolean flag = songService.update(song);
            if (flag){//保存成功
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"修改成功");
                jsonObject.put("pic",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"修改失败");

            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"修改失败"+e.getMessage());

        }finally {
            return jsonObject;
        }
    }


    /**
     * 更新歌曲
     */
    @PostMapping("/updateSongUrl")
    public Object updateSongUrl(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") int id){
        Song song = songService.selectByPrimaryKey(id);
        String url = "." + song.getUrl();
        System.out.println(url);
        FileSystemUtils.deleteRecursively(new File(url));

        JSONObject jsonObject = new JSONObject();
        if (avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
        //文件名=当前时间（ms）+原来的文件名
        String filename = System.currentTimeMillis() + avatorFile.getOriginalFilename();
        //文件路径
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"song";
        //如果文件路劲不存在，新增路径
        File file1 = new File(filePath);
        if (!file1.exists()){
            file1.mkdir();
        }
        //实际的文件路径
        File dest = new File(filePath + System.getProperty("file.separator") + filename);
        //存储到数据库里的相对地址文件
        String storeAvatorPath = "/song/" + filename;
        try {
            avatorFile.transferTo(dest);
            song.setId(id);
            song.setUrl(storeAvatorPath);
            songService.update(song);
            boolean flag = songService.update(song);
            if (flag){//保存成功
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"修改成功");
                jsonObject.put("avator",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"修改失败");

            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"修改失败"+e.getMessage());

        }finally {
            return jsonObject;
        }
    }
}
