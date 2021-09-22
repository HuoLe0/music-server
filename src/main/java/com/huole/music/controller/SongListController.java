package com.huole.music.controller;

import com.huole.music.model.ResultModel;
import com.huole.music.model.SongList;
import com.huole.music.service.ReturnService;
import com.huole.music.service.SongListService;
import com.huole.music.utils.RedisUtil;
import com.huole.music.utils.ResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ReturnService returnService;


    /**
     * 添加歌单
     */
    @PostMapping("/add")
    public Object addSongList(String title, String pic, String introduction, String style){
        //保存到歌单对象中
        SongList songList = new SongList();
        songList.setTitle(title);
        songList.setPic(pic);
        songList.setIntroduction(introduction);
        songList.setStyle(style);
        boolean flag = songListService.insert(songList);
        if (flag){//保存成功
            returnService.setSuccess(ResponseEnum.ADD_SUCCESS.isSuccess());
            returnService.setCode(ResponseEnum.ADD_SUCCESS.getCode());
            returnService.setMsg(ResponseEnum.ADD_SUCCESS.getMsg());
            returnService.setTimestamp(System.currentTimeMillis()/1000);
        }
        returnService.setSuccess(ResponseEnum.ADD_FAILED.isSuccess());
        returnService.setCode(ResponseEnum.ADD_FAILED.getCode());
        returnService.setMsg(ResponseEnum.ADD_FAILED.getMsg());
        returnService.setTimestamp(System.currentTimeMillis()/1000);
        return returnService.getReturnValue();
    }

    /**
     * 前端用户添加歌单
     */
    @PostMapping("/addByConsumer")
    public Object addByConsumer(String title, String pic, String introduction, String style, Integer userId){
        //保存到歌单对象中
        SongList songList = new SongList();
        songList.setTitle(title);
        songList.setPic(pic);
        songList.setIntroduction(introduction);
        songList.setStyle(style);
        songList.setUserId(userId);
        boolean flag = songListService.insertByConsumer(songList);
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
     * 更新歌单
     */
    @PostMapping("/update")
    public Object updateSongList(Integer id, String title, String pic, String introduction, String style){
        //保存到歌手对象中
        SongList songList = new SongList();
        songList.setId(id);
        songList.setTitle(title);
        songList.setIntroduction(introduction);
        songList.setStyle(style);
        boolean flag = songListService.update(songList);
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

    @PostMapping("/addSong")
    public Object addSong(Integer songId, Integer songListId) {
        String songs = songListService.selectById(songListId).getSongs();
        if (songs.matches(".*," + songId + ",.*")){
            returnService.setSuccess(ResponseEnum.ADD_FAILED.isSuccess());
            returnService.setCode(ResponseEnum.ADD_FAILED.getCode());
            returnService.setMsg("歌曲已存在！");
            returnService.setTimestamp(System.currentTimeMillis()/1000);
            return returnService.getReturnValue();
        }else {
            boolean flag = songListService.addSong(songListId, songId);
            if (flag) {
                returnService.setSuccess(ResponseEnum.ADD_SUCCESS.isSuccess());
                returnService.setCode(ResponseEnum.ADD_SUCCESS.getCode());
                returnService.setMsg(ResponseEnum.ADD_SUCCESS.getMsg());
                returnService.setTimestamp(System.currentTimeMillis() / 1000);
            }else {
                returnService.setSuccess(ResponseEnum.ADD_FAILED.isSuccess());
                returnService.setCode(ResponseEnum.ADD_FAILED.getCode());
                returnService.setMsg(ResponseEnum.ADD_FAILED.getMsg());
                returnService.setTimestamp(System.currentTimeMillis()/1000);
            }
        }
        return returnService.getReturnValue();
    }

    @GetMapping("/deleteSong")
    public Object deleteSong(Integer songId, Integer songListId, Integer creatorId, Integer userId) {
        if (creatorId.equals(userId)){
            String songs = songListService.selectById(songListId).getSongs();
            if (!songs.matches(".*," + songId + ",.*")){
                returnService.setSuccess(ResponseEnum.ADD_FAILED.isSuccess());
                returnService.setCode(ResponseEnum.ADD_FAILED.getCode());
                returnService.setMsg("歌曲不存在！");
                returnService.setTimestamp(System.currentTimeMillis()/1000);
                return returnService.getReturnValue();
            }else {
                boolean flag = songListService.deleteSong(songListId, songId);
                if (flag) {
                    returnService.setSuccess(ResponseEnum.ADD_SUCCESS.isSuccess());
                    returnService.setCode(ResponseEnum.ADD_SUCCESS.getCode());
                    returnService.setMsg(ResponseEnum.ADD_SUCCESS.getMsg());
                    returnService.setTimestamp(System.currentTimeMillis() / 1000);
                }else {
                    returnService.setSuccess(ResponseEnum.ADD_FAILED.isSuccess());
                    returnService.setCode(ResponseEnum.ADD_FAILED.getCode());
                    returnService.setMsg(ResponseEnum.ADD_FAILED.getMsg());
                    returnService.setTimestamp(System.currentTimeMillis()/1000);
                }
            }
        }else {
            returnService.setSuccess(ResponseEnum.ADD_FAILED.isSuccess());
            returnService.setCode(ResponseEnum.ADD_FAILED.getCode());
            returnService.setMsg(ResponseEnum.NO_PERMISSION.getMsg());
            returnService.setTimestamp(System.currentTimeMillis()/1000);
            return returnService.getReturnValue();
        }
        return returnService.getReturnValue();
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
        returnService.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        returnService.setCode(ResponseEnum.SUCCESS.getCode());
        returnService.setMsg(ResponseEnum.SUCCESS.getMsg());
        returnService.setData(songListService.selectById(id));
        returnService.setTimestamp(System.currentTimeMillis()/1000);
        return returnService.getReturnValue();
    }

    /**
     * 查询所有歌单
     */
    @GetMapping("/selectAll")
    public Object selectAll(){
        returnService.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        returnService.setCode(ResponseEnum.SUCCESS.getCode());
        returnService.setMsg(ResponseEnum.SUCCESS.getMsg());
        returnService.setData(songListService.selectAll());
        returnService.setTimestamp(System.currentTimeMillis()/1000);
        return returnService.getReturnValue();
    }

    /**
     * 查询所有歌单--分页
     * @return
     */
    @GetMapping("/selectByPager")
    public Object selectByPager(Integer page, Integer size){
        returnService.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        returnService.setCode(ResponseEnum.SUCCESS.getCode());
        returnService.setMsg(ResponseEnum.SUCCESS.getMsg());
        returnService.setData(songListService.selectByPager(page, size));
        returnService.setTimestamp(System.currentTimeMillis()/1000);
        return returnService.getReturnValue();
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
//            System.out.println(songLists);
            Integer totalNum = songIds.size();
            for (int i = 0; i < n; i++){
                Integer id = songIds.get((int) (Math.random() * totalNum));
                songLists.add(songListService.selectById(id));
            }
            redisUtil.set(userSongList, songLists, ExpireTime);
            returnService.setSuccess(ResponseEnum.SUCCESS.isSuccess());
            returnService.setCode(ResponseEnum.SUCCESS.getCode());
            returnService.setMsg(ResponseEnum.SUCCESS.getMsg());
            returnService.setData(songLists);
            returnService.setTimestamp(System.currentTimeMillis()/1000);
            return returnService.getReturnValue();
        }else {
            returnService.setSuccess(ResponseEnum.SUCCESS.isSuccess());
            returnService.setCode(ResponseEnum.SUCCESS.getCode());
            returnService.setMsg(ResponseEnum.SUCCESS.getMsg());
            returnService.setData(redisUtil.get(userSongList));
            returnService.setTimestamp(System.currentTimeMillis()/1000);
            return returnService.getReturnValue();
        }
    }

    @GetMapping("/selectSongs")
    public Object selectSongs(Integer songListId) {
        String songs = songListService.selectById(songListId).getSongs();
//        System.out.println(songs);
        String[] songIds = songs.split(",");
        returnService.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        returnService.setCode(ResponseEnum.SUCCESS.getCode());
        returnService.setMsg(ResponseEnum.SUCCESS.getMsg());
        returnService.setData(songListService.selectSongs(songIds));
        returnService.setTimestamp(System.currentTimeMillis()/1000);
        return returnService.getReturnValue();
    }

    /**
     * 查询用户所有歌单
     */
    @GetMapping("/selectAllConsumer")
    public Object selectAllConsumer(Integer userId){
        returnService.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        returnService.setCode(ResponseEnum.SUCCESS.getCode());
        returnService.setMsg(ResponseEnum.SUCCESS.getMsg());
        returnService.setData(songListService.selectAllConsumer(userId));
        returnService.setTimestamp(System.currentTimeMillis()/1000);
        return returnService.getReturnValue();
    }

    /**
     * 查询前十个歌单
     */
    @GetMapping("/selectTen")
    public Object selectTen(){
        returnService.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        returnService.setCode(ResponseEnum.SUCCESS.getCode());
        returnService.setMsg(ResponseEnum.SUCCESS.getMsg());
        returnService.setData(songListService.selectTen());
        returnService.setTimestamp(System.currentTimeMillis()/1000);
        return returnService.getReturnValue();
    }

    /**
     * 根据标题精确查找
     * @return
     */
    @GetMapping("/selectByTitle")
    public Object selectByTitle(String title){
        returnService.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        returnService.setCode(ResponseEnum.SUCCESS.getCode());
        returnService.setMsg(ResponseEnum.SUCCESS.getMsg());
        returnService.setData(songListService.selectByTitle(title));
        returnService.setTimestamp(System.currentTimeMillis()/1000);
        return returnService.getReturnValue();
    }

    /**
     * 根据标题模糊查找
     */
    @GetMapping("/selectLikeTitle")
    public Object selectLikeTitle(String title){
        returnService.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        returnService.setCode(ResponseEnum.SUCCESS.getCode());
        returnService.setMsg(ResponseEnum.SUCCESS.getMsg());
        returnService.setData(songListService.selectLikeTitle(title));
        returnService.setTimestamp(System.currentTimeMillis()/1000);
        return returnService.getReturnValue();
    }

    /**
     * 根据风格模糊查找
     */
    @GetMapping("/selectLikeStyle")
    public Object selectLikeStyle(String style, Integer page, Integer size){
        returnService.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        returnService.setCode(ResponseEnum.SUCCESS.getCode());
        returnService.setMsg(ResponseEnum.SUCCESS.getMsg());
        returnService.setData(songListService.selectLikeStyle(style, page, size));
        returnService.setTimestamp(System.currentTimeMillis()/1000);
        return returnService.getReturnValue();
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
        if (avatorFile.isEmpty()){
            returnService.setSuccess(ResponseEnum.UPLOAD_FILE_FAILED.isSuccess());
            returnService.setCode(ResponseEnum.UPLOAD_FILE_FAILED.getCode());
            returnService.setMsg(ResponseEnum.UPLOAD_FILE_FAILED.getMsg());
            returnService.setTimestamp(System.currentTimeMillis()/1000);
            return returnService.getReturnValue();
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
                returnService.setCode(ResponseEnum.UPLOAD_SUCCESS.getCode());
                returnService.setSuccess(ResponseEnum.UPLOAD_SUCCESS.isSuccess());
                returnService.setMsg(ResponseEnum.UPLOAD_SUCCESS.getMsg());
                returnService.setExt("avator" + storeAvatorPath);
                returnService.setTimestamp(System.currentTimeMillis()/1000);
                return returnService.getReturnValue();
            }
            returnService.setSuccess(ResponseEnum.ERROR.isSuccess());
            returnService.setCode(ResponseEnum.ERROR.getCode());
            returnService.setMsg(ResponseEnum.ERROR.getMsg());
            returnService.setTimestamp(System.currentTimeMillis()/1000);
        } catch (IOException e) {
            e.printStackTrace();
            returnService.setSuccess(ResponseEnum.ERROR.isSuccess());
            returnService.setCode(ResponseEnum.ERROR.getCode());
            returnService.setMsg(ResponseEnum.ERROR.getMsg());
            returnService.setTimestamp(System.currentTimeMillis()/1000);
        }finally {
            return returnService.getReturnValue();
        }
    }
}
