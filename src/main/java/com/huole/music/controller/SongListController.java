package com.huole.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.huole.music.model.SongList;
import com.huole.music.service.SongListService;
import com.huole.music.utils.Consts;
import com.huole.music.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 歌单控制类
 */
@RestController
@RequestMapping("/songList")
public class SongListController {
    @Autowired
    private SongListService songListService;

    @Resource
    private RedisUtil redisUtil;
    /**
     * 添加歌单
     */
    @PostMapping("/add")
    public Object addSongList(String title, String pic, String introduction, String style){
        JSONObject jsonObject = new JSONObject();
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
    public Object addByConsumer(String title, String pic, String introduction, String style, Integer userId){
        JSONObject jsonObject = new JSONObject();
        //保存到歌单对象中
        SongList songList = new SongList();
        songList.setTitle(title);
        songList.setPic(pic);
        songList.setIntroduction(introduction);
        songList.setStyle(style);
        songList.setUserId(userId);
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
     */
    @PostMapping("/update")
    public Object updateSongList(Integer id, String title, String pic, String introduction, String style){
        JSONObject jsonObject = new JSONObject();
        //保存到歌手对象中
        SongList songList = new SongList();
        songList.setId(id);
        songList.setTitle(title);
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

    @PostMapping("/addSong")
    public Object addSong(Integer songId, Integer songListId) {
        String songs = songListService.selectById(songListId).getSongs();
        JSONObject result = new JSONObject();
        if (songs.matches(".*," + songId + ",.*")){
            result.put("success", false);
            result.put("message", "歌曲已存在！");
            return result;
        }else {
            boolean flag = songListService.addSong(songListId, songId);
            result.put("success", flag);
            result.put("message", "添加成功！");
        }
        return result;
    }

    @GetMapping("/deleteSong")
    public Object deleteSong(Integer songId, Integer songListId, Integer creatorId, Integer userId) {
        JSONObject result = new JSONObject();
        if (creatorId.equals(userId)){
            String songs = songListService.selectById(songListId).getSongs();
            if (!songs.matches(".*," + songId + ",.*")){
                result.put("success", false);
                result.put("message", "歌曲不存在！");
                return result;
            }else {
                boolean flag = songListService.deleteSong(songListId, songId);
                result.put("success", flag);
                result.put("message", "删除成功！");
            }
        }else {
            result.put("success", false);
            result.put("message", "你没有权限！");
            return result;
        }
        return result;
    }
    /**
     * 根据id删除歌单
     */
    @GetMapping("/delete")
    public Object delSongList(Integer id){
        boolean flag = songListService.delete(id);
        return flag;
    }

    /**
     * 根据主键查询歌dan
     */
    @GetMapping("/selectById")
    public Object selectById(Integer id){
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", songListService.selectById(id));
        return result;
    }

    /**
     * 查询所有歌单
     */
    @GetMapping("/selectAll")
    public Object selectAll(){
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", songListService.selectAll());
        return result;
    }

    /**
     * 查询所有歌单--分页
     * @return
     */
    @GetMapping("/selectByPager")
    public Object selectByPager(Integer page, Integer size){
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", songListService.selectByPager(page, size));
        return result;
    }

    /**
     * 查询随机20收歌曲
     * @return
     */
    @GetMapping("/selectRandom")
    public Object selectRandom(Integer nums, Integer userId){
        String userSongList = userId == 9527 ? "youkeSongList" : userId.toString() + "songList";
        Calendar calendar = Calendar.getInstance();
        long ExpireTime = (24 - calendar.get(Calendar.HOUR)) * 3600L;
        if (redisUtil.get(userSongList) == null){
            Integer n = nums == null ? 10 : nums;
            Set<SongList> songLists = new HashSet<>();
            List<Integer> songIds = songListService.selectAllId();
            System.out.println(songLists);
            Integer totalNum = songIds.size();
            for (int i = 0; i < n; i++){
                Integer id = songIds.get((int) (Math.random() * totalNum));
                songLists.add(songListService.selectById(id));
            }
            redisUtil.set(userSongList, songLists, ExpireTime);
            JSONObject result = new JSONObject();
            result.put("success", true);
            result.put("data", songLists);
            return result;
        }else {
            JSONObject result = new JSONObject();
            result.put("success", true);
            result.put("data", redisUtil.get(userSongList));
            return result;
        }
    }

    @GetMapping("/selectSongs")
    public Object selectSongs(Integer songListId) {
        String songs = songListService.selectById(songListId).getSongs();
        System.out.println(songs);
        String[] songIds = songs.split(",");
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", songListService.selectSongs(songIds));
        return result;
    }

    /**
     * 查询用户所有歌单
     */
    @GetMapping("/selectAllConsumer")
    public Object selectAllConsumer(Integer userId){
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", songListService.selectAllConsumer(userId));
        return result;
    }

    /**
     * 查询前十个歌单
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
     * @return
     */
    @GetMapping("/selectByTitle")
    public Object selectByTitle(String title){
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", songListService.selectByTitle(title));
        return result;
    }

    /**
     * 根据标题模糊查找
     */
    @GetMapping("/selectLikeTitle")
    public Object selectLikeTitle(String title){
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", songListService.selectLikeTitle(title));
        return result;
    }

    /**
     * 根据风格模糊查找
     */
    @GetMapping("/selectLikeStyle")
    public Object selectLikeStyle(String style, Integer page, Integer size){
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", songListService.selectLikeStyle(style, page, size));
        return result;
    }


    /**
     * 更新歌曲图片
     */
    @PostMapping("/updateSongListPic")
    public Object updateSongListPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") int id){
        SongList songList = songListService.selectById(id);
        String pic = "." + songList.getPic();
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
