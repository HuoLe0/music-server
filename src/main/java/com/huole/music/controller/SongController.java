package com.huole.music.controller;


import com.huole.music.model.ResultModel;
import com.huole.music.model.Song;
import com.huole.music.service.ReturnService;
import com.huole.music.service.SongService;
import com.huole.music.utils.ResponseEnum;
import com.huole.music.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 歌曲管理controller
 */
@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SongService songService;

    @Autowired
    private ReturnService returnService;


    /**
     * 添加歌曲
     */
    @PostMapping("/add")
    public Object addSong(Integer singerId, String name, String introduction, String lyric, String mv, @RequestParam("pic") MultipartFile pic, @RequestParam("file") MultipartFile file) {
        if (pic.isEmpty()) {
            returnService.setSuccess(ResponseEnum.UPLOAD_IMAGE_FAILED.isSuccess());
            returnService.setCode(ResponseEnum.UPLOAD_IMAGE_FAILED.getCode());
            returnService.setMsg(ResponseEnum.UPLOAD_IMAGE_FAILED.getMsg());
            returnService.setTimestamp(System.currentTimeMillis()/1000);
            return returnService.getReturnValue();
        }
        if (file.isEmpty()) {
            returnService.setSuccess(ResponseEnum.UPLOAD_IMAGE_FAILED.isSuccess());
            returnService.setCode(ResponseEnum.UPLOAD_FILE_FAILED.getCode());
            returnService.setMsg(ResponseEnum.UPLOAD_FILE_FAILED.getMsg());
            returnService.setTimestamp(System.currentTimeMillis()/1000);
            return returnService.getReturnValue();
        }
        //文件名=当前时间（ms）+原来的文件名
        String filename = System.currentTimeMillis() + file.getOriginalFilename();
        String picname = System.currentTimeMillis() + pic.getOriginalFilename();
        //文件路径
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "song";
        String picPath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "songPic";
        //如果文件路劲不存在，新增路径
        File file1 = new File(filePath);
        File pic1 = new File(picPath);
        if (!file1.exists()) {
            file1.mkdir();
        }
        if (!pic1.exists()) {
            pic1.mkdir();
        }
        //实际的文件路径
        File dest = new File(filePath + System.getProperty("file.separator") + filename);
        File picDest = new File(picPath + System.getProperty("file.separator") + picname);
        //存储到数据库里的相对地址文件
        String storeSongPath = "/song/" + filename;
        String storeSongPicPath = "/img/songPic/" + picname;
        try {
            file.transferTo(dest);
            pic.transferTo(picDest);
            Song song = new Song();
            song.setSingerId(singerId);
            song.setName(name);
            song.setIntroduction(introduction);
            song.setPic(storeSongPicPath);
            song.setLyric(lyric);
            song.setUrl(storeSongPath);
            song.setMv(mv);
            boolean flag = songService.insert(song);
            if (flag) {//保存成功
                returnService.setCode(ResponseEnum.UPLOAD_SUCCESS.getCode());
                returnService.setSuccess(ResponseEnum.UPLOAD_SUCCESS.isSuccess());
                returnService.setMsg(ResponseEnum.UPLOAD_SUCCESS.getMsg());
                returnService.setExt("avator" + storeSongPath);
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
            returnService.setExt(e.getMessage());
            returnService.setTimestamp(System.currentTimeMillis()/1000);
        } finally {
            return returnService.getReturnValue();
        }
    }

    /**
     * 更新歌曲
     */
    @PostMapping("/update")
    public Object updateSong(Integer id, String name, String introduction, String lyric, String mv) {
        //获取前端传来的参数
        Song song = new Song();
        song.setId(id);
        song.setName(name);
        song.setIntroduction(introduction);
        song.setLyric(lyric);
        song.setMv(mv);
        songService.update(song);
        boolean flag = songService.update(song);
        if (flag) {//保存成功
            returnService.setCode(ResponseEnum.UPLOAD_SUCCESS.getCode());
            returnService.setSuccess(ResponseEnum.UPLOAD_SUCCESS.isSuccess());
            returnService.setMsg(ResponseEnum.UPLOAD_SUCCESS.getMsg());
            returnService.setTimestamp(System.currentTimeMillis()/1000);
            return returnService.getReturnValue();
        }
        returnService.setCode(ResponseEnum.UPLOAD_FAILED.getCode());
        returnService.setSuccess(ResponseEnum.UPLOAD_FAILED.isSuccess());
        returnService.setMsg(ResponseEnum.UPLOAD_FAILED.getMsg());
        returnService.setTimestamp(System.currentTimeMillis()/1000);
        return returnService.getReturnValue();
    }

    /**
     * 查询所有歌曲
     */
    @GetMapping("/selectAll")
    public Object selectAll(){
        returnService.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        returnService.setCode(ResponseEnum.SUCCESS.getCode());
        returnService.setMsg(ResponseEnum.SUCCESS.getMsg());
        returnService.setData(songService.selectAll());
        returnService.setTimestamp(System.currentTimeMillis()/1000);
        return returnService.getReturnValue();
    }

    /**
     * 查询所有歌曲--分页
     */
    @GetMapping("/selectByPager")
    public Object selectByPager(Integer page, Integer size){
        ResultModel returnService = new ResultModel();
        SongController.this.returnService.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        SongController.this.returnService.setCode(ResponseEnum.SUCCESS.getCode());
        SongController.this.returnService.setMsg(ResponseEnum.SUCCESS.getMsg());
        SongController.this.returnService.setData(songService.selectByPager(page, size));
        SongController.this.returnService.setTimestamp(System.currentTimeMillis()/1000);
        return SongController.this.returnService;
    }

    /**
     * 根据id删除歌曲
     */
    @GetMapping("/delete")
    public Object deleteSong(Integer id){
        Song song = songService.selectById(id);
        String url = "." + song.getUrl();
        String pic = "." + song.getPic();
        FileSystemUtils.deleteRecursively(new File(url));//移除本地文件
        if (!pic.equals("./img/songPic/Jay.jpg")){
            FileSystemUtils.deleteRecursively(new File(pic));
        }
        return songService.delete(id);
    }

    /**
     * 根据主键查询歌曲
     */
    @GetMapping("/selectById")
    public Object selectById(Integer id){
        returnService.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        returnService.setCode(ResponseEnum.SUCCESS.getCode());
        returnService.setMsg(ResponseEnum.SUCCESS.getMsg());
        returnService.setData(songService.selectById(id));
        returnService.setTimestamp(System.currentTimeMillis()/1000);
        return returnService.getReturnValue();
    }

    /**
     * 查询随机20收歌曲
     * @return
     */
    @GetMapping("/selectRandom")
    public Object selectRandom(Integer nums, Integer userId){
        String userSong = userId == 9527 ? "youkeSong" : userId.toString() + "song";
        Calendar calendar = Calendar.getInstance();
        long ExpireTime = (24 - calendar.get(Calendar.HOUR)) * 3600L;
        if (redisUtil.get(userSong) == null){
            int num = nums == null ? 20 : nums;
            Set<Song> songs = new HashSet<>();
            List<Integer> songIds = songService.selectAllId();
            Set<Integer> songId = new HashSet<>();
            int totalNum = songIds.size();
            for (int i = 0; i < num; i++){
                Integer id = songIds.get((int) (Math.random() * totalNum));
                if (!songId.contains(id)){
                    songId.add(id);
                }
            }
            for (Integer ID : songId){
                songs.add(songService.selectById(ID));
            }
            redisUtil.set(userSong, songs, ExpireTime);
            returnService.setSuccess(ResponseEnum.SUCCESS.isSuccess());
            returnService.setCode(ResponseEnum.SUCCESS.getCode());
            returnService.setMsg(ResponseEnum.SUCCESS.getMsg());
            returnService.setData(songs);
            returnService.setTimestamp(System.currentTimeMillis()/1000);
            return returnService.getReturnValue();
        }else {
            returnService.setSuccess(ResponseEnum.SUCCESS.isSuccess());
            returnService.setCode(ResponseEnum.SUCCESS.getCode());
            returnService.setMsg(ResponseEnum.SUCCESS.getMsg());
            returnService.setData(redisUtil.get(userSong));
            returnService.setTimestamp(System.currentTimeMillis()/1000);
            return returnService.getReturnValue();
        }
    }

    /**
     * 根据歌名查询
     */
    @GetMapping("/selectByName")
    public Object selectByName(String name){
        returnService.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        returnService.setCode(ResponseEnum.SUCCESS.getCode());
        returnService.setMsg(ResponseEnum.SUCCESS.getMsg());
        Object data = songService.selectByName(name);
        returnService.setData(data);
        returnService.setTimestamp(System.currentTimeMillis()/1000);
        return returnService.getReturnValue();
    }

    @GetMapping("/selectLikeName")
    public Object selectLikeName(String name){
        returnService.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        returnService.setCode(ResponseEnum.SUCCESS.getCode());
        returnService.setMsg(ResponseEnum.SUCCESS.getMsg());
        returnService.setData(songService.selectLikeName(name));
        returnService.setTimestamp(System.currentTimeMillis()/1000);
        return returnService.getReturnValue();
    }
    /**
     * 根据歌手id查询
     */
    @GetMapping("/selectBySingerId")
    public Object selectBySingerId(Integer singerId, Integer page, Integer size){
        returnService.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        returnService.setCode(ResponseEnum.SUCCESS.getCode());
        returnService.setMsg(ResponseEnum.SUCCESS.getMsg());
        returnService.setData(songService.selectBySingerId(singerId, page, size));
        returnService.setTimestamp(System.currentTimeMillis()/1000);
        return returnService.getReturnValue();
    }

    /**
     * 更新歌曲图片
     */
    @PostMapping("/updateSongPic")
    public Object updateSongPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") int id){
        Song song = songService.selectById(id);
        String pic = "." + song.getPic();
        if (!pic.equals("./img/songPic/Jay.jpg")){
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
            return returnService.getReturnValue();
        } catch (IOException e) {
            e.printStackTrace();
            returnService.setSuccess(ResponseEnum.ERROR.isSuccess());
            returnService.setCode(ResponseEnum.ERROR.getCode());
            returnService.setMsg(e.getMessage());
            returnService.setTimestamp(System.currentTimeMillis()/1000);
        }finally {
            return returnService.getReturnValue();
        }
    }


    /**
     * 更新歌曲
     */
    @PostMapping("/updateSongUrl")
    public Object updateSongUrl(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") int id){
        Song song = songService.selectById(id);
        String url = "." + song.getUrl();
        System.out.println(url);
        FileSystemUtils.deleteRecursively(new File(url));
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
                returnService.setCode(ResponseEnum.UPLOAD_SUCCESS.getCode());
                returnService.setSuccess(ResponseEnum.UPLOAD_SUCCESS.isSuccess());
                returnService.setMsg(ResponseEnum.UPLOAD_SUCCESS.getMsg());
                returnService.setExt("avator" + storeAvatorPath);
                returnService.setTimestamp(System.currentTimeMillis()/1000);
                return returnService.getReturnValue();
            }
            returnService.setSuccess(ResponseEnum.UPLOAD_FILE_FAILED.isSuccess());
            returnService.setCode(ResponseEnum.UPLOAD_FILE_FAILED.getCode());
            returnService.setMsg(ResponseEnum.UPLOAD_FILE_FAILED.getMsg());
            returnService.setTimestamp(System.currentTimeMillis()/1000);
            return returnService.getReturnValue();
        } catch (IOException e) {
            e.printStackTrace();
            returnService.setSuccess(ResponseEnum.UPLOAD_FILE_FAILED.isSuccess());
            returnService.setCode(ResponseEnum.UPLOAD_FILE_FAILED.getCode());
            returnService.setMsg(e.getMessage());
            returnService.setTimestamp(System.currentTimeMillis()/1000);
        }finally {
            return returnService.getReturnValue();
        }
    }
}
