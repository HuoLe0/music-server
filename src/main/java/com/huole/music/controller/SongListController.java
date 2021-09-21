package com.huole.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.huole.music.model.ResultModel;
import com.huole.music.model.SongList;
import com.huole.music.service.SongListService;
import com.huole.music.utils.Consts;
import com.huole.music.utils.RedisUtil;
import com.huole.music.utils.ResponseEnum;
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

    @Autowired
    private RedisUtil redisUtil;
    /**
     * 添加歌单
     */
    @PostMapping("/add")
    public Object addSongList(String title, String pic, String introduction, String style){
        ResultModel resultModel = new ResultModel();
        //保存到歌单对象中
        SongList songList = new SongList();
        songList.setTitle(title);
        songList.setPic(pic);
        songList.setIntroduction(introduction);
        songList.setStyle(style);
        boolean flag = songListService.insert(songList);
        if (flag){//保存成功
            resultModel.setSuccess(ResponseEnum.ADD_SUCCESS.isSuccess());
            resultModel.setCode(ResponseEnum.ADD_SUCCESS.getCode());
            resultModel.setMsg(ResponseEnum.ADD_SUCCESS.getMsg());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
        }
        resultModel.setSuccess(ResponseEnum.ADD_FAILED.isSuccess());
        resultModel.setCode(ResponseEnum.ADD_FAILED.getCode());
        resultModel.setMsg(ResponseEnum.ADD_FAILED.getMsg());
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    /**
     * 前端用户添加歌单
     */
    @PostMapping("/addByConsumer")
    public Object addByConsumer(String title, String pic, String introduction, String style, Integer userId){
        ResultModel resultModel = new ResultModel();
        //保存到歌单对象中
        SongList songList = new SongList();
        songList.setTitle(title);
        songList.setPic(pic);
        songList.setIntroduction(introduction);
        songList.setStyle(style);
        songList.setUserId(userId);
        boolean flag = songListService.insertByConsumer(songList);
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
     * 更新歌单
     */
    @PostMapping("/update")
    public Object updateSongList(Integer id, String title, String pic, String introduction, String style){
        ResultModel resultModel = new ResultModel();
        //保存到歌手对象中
        SongList songList = new SongList();
        songList.setId(id);
        songList.setTitle(title);
        songList.setIntroduction(introduction);
        songList.setStyle(style);
        boolean flag = songListService.update(songList);
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

    @PostMapping("/addSong")
    public Object addSong(Integer songId, Integer songListId) {
        String songs = songListService.selectById(songListId).getSongs();
        ResultModel resultModel = new ResultModel();
        if (songs.matches(".*," + songId + ",.*")){
            resultModel.setSuccess(ResponseEnum.ADD_FAILED.isSuccess());
            resultModel.setCode(ResponseEnum.ADD_FAILED.getCode());
            resultModel.setMsg("歌曲已存在！");
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        }else {
            boolean flag = songListService.addSong(songListId, songId);
            if (flag) {
                resultModel.setSuccess(ResponseEnum.ADD_SUCCESS.isSuccess());
                resultModel.setCode(ResponseEnum.ADD_SUCCESS.getCode());
                resultModel.setMsg(ResponseEnum.ADD_SUCCESS.getMsg());
                resultModel.setTimestamp(System.currentTimeMillis() / 1000);
            }else {
                resultModel.setSuccess(ResponseEnum.ADD_FAILED.isSuccess());
                resultModel.setCode(ResponseEnum.ADD_FAILED.getCode());
                resultModel.setMsg(ResponseEnum.ADD_FAILED.getMsg());
                resultModel.setTimestamp(System.currentTimeMillis()/1000);
            }
        }
        return resultModel;
    }

    @GetMapping("/deleteSong")
    public Object deleteSong(Integer songId, Integer songListId, Integer creatorId, Integer userId) {
        ResultModel resultModel = new ResultModel();
        if (creatorId.equals(userId)){
            String songs = songListService.selectById(songListId).getSongs();
            if (!songs.matches(".*," + songId + ",.*")){
                resultModel.setSuccess(ResponseEnum.ADD_FAILED.isSuccess());
                resultModel.setCode(ResponseEnum.ADD_FAILED.getCode());
                resultModel.setMsg("歌曲不存在！");
                resultModel.setTimestamp(System.currentTimeMillis()/1000);
                return resultModel;
            }else {
                boolean flag = songListService.deleteSong(songListId, songId);
                if (flag) {
                    resultModel.setSuccess(ResponseEnum.ADD_SUCCESS.isSuccess());
                    resultModel.setCode(ResponseEnum.ADD_SUCCESS.getCode());
                    resultModel.setMsg(ResponseEnum.ADD_SUCCESS.getMsg());
                    resultModel.setTimestamp(System.currentTimeMillis() / 1000);
                }else {
                    resultModel.setSuccess(ResponseEnum.ADD_FAILED.isSuccess());
                    resultModel.setCode(ResponseEnum.ADD_FAILED.getCode());
                    resultModel.setMsg(ResponseEnum.ADD_FAILED.getMsg());
                    resultModel.setTimestamp(System.currentTimeMillis()/1000);
                }
            }
        }else {
            resultModel.setSuccess(ResponseEnum.ADD_FAILED.isSuccess());
            resultModel.setCode(ResponseEnum.ADD_FAILED.getCode());
            resultModel.setMsg(ResponseEnum.NO_PERMISSION.getMsg());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        }
        return resultModel;
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
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setData(songListService.selectById(id));
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    /**
     * 查询所有歌单
     */
    @GetMapping("/selectAll")
    public Object selectAll(){
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setData(songListService.selectAll());
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    /**
     * 查询所有歌单--分页
     * @return
     */
    @GetMapping("/selectByPager")
    public Object selectByPager(Integer page, Integer size){
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setData(songListService.selectByPager(page, size));
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
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
        ResultModel resultModel = new ResultModel();
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
            resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
            resultModel.setCode(ResponseEnum.SUCCESS.getCode());
            resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
            resultModel.setData(songLists);
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        }else {
            resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
            resultModel.setCode(ResponseEnum.SUCCESS.getCode());
            resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
            resultModel.setData(redisUtil.get(userSongList));
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        }
    }

    @GetMapping("/selectSongs")
    public Object selectSongs(Integer songListId) {
        String songs = songListService.selectById(songListId).getSongs();
        System.out.println(songs);
        String[] songIds = songs.split(",");
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setData(songListService.selectSongs(songIds));
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    /**
     * 查询用户所有歌单
     */
    @GetMapping("/selectAllConsumer")
    public Object selectAllConsumer(Integer userId){
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setData(songListService.selectAllConsumer(userId));
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    /**
     * 查询前十个歌单
     */
    @GetMapping("/selectTen")
    public Object selectTen(){
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setData(songListService.selectTen());
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    /**
     * 根据标题精确查找
     * @return
     */
    @GetMapping("/selectByTitle")
    public Object selectByTitle(String title){
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setData(songListService.selectByTitle(title));
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    /**
     * 根据标题模糊查找
     */
    @GetMapping("/selectLikeTitle")
    public Object selectLikeTitle(String title){
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setData(songListService.selectLikeTitle(title));
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    /**
     * 根据风格模糊查找
     */
    @GetMapping("/selectLikeStyle")
    public Object selectLikeStyle(String style, Integer page, Integer size){
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setData(songListService.selectLikeStyle(style, page, size));
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
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
        ResultModel resultModel = new ResultModel();
        if (avatorFile.isEmpty()){
            resultModel.setSuccess(ResponseEnum.UPLOAD_FILE_FAILED.isSuccess());
            resultModel.setCode(ResponseEnum.UPLOAD_FILE_FAILED.getCode());
            resultModel.setMsg(ResponseEnum.UPLOAD_FILE_FAILED.getMsg());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
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
                resultModel.setCode(ResponseEnum.UPLOAD_SUCCESS.getCode());
                resultModel.setSuccess(ResponseEnum.UPLOAD_SUCCESS.isSuccess());
                resultModel.setMsg(ResponseEnum.UPLOAD_SUCCESS.getMsg());
                resultModel.setExt("avator" + storeAvatorPath);
                resultModel.setTimestamp(System.currentTimeMillis()/1000);
                return resultModel;
            }
            resultModel.setSuccess(ResponseEnum.ERROR.isSuccess());
            resultModel.setCode(ResponseEnum.ERROR.getCode());
            resultModel.setMsg(ResponseEnum.ERROR.getMsg());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
        } catch (IOException e) {
            e.printStackTrace();
            resultModel.setSuccess(ResponseEnum.ERROR.isSuccess());
            resultModel.setCode(ResponseEnum.ERROR.getCode());
            resultModel.setMsg(ResponseEnum.ERROR.getMsg());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
        }finally {
            return resultModel;
        }
    }
}
