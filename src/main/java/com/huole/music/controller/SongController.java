package com.huole.music.controller;


import com.huole.music.model.ResultModel;
import com.huole.music.model.Song;
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


    /**
     * 添加歌曲
     */
    @PostMapping("/add")
    public Object addSong(Integer singerId, String name, String introduction, String lyric, String mv, @RequestParam("pic") MultipartFile pic, @RequestParam("file") MultipartFile file) {
        ResultModel resultModel = new ResultModel();
        if (pic.isEmpty()) {
            resultModel.setSuccess(ResponseEnum.UPLOAD_IMAGE_FAILED.isSuccess());
            resultModel.setCode(ResponseEnum.UPLOAD_IMAGE_FAILED.getCode());
            resultModel.setMsg(ResponseEnum.UPLOAD_IMAGE_FAILED.getMsg());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        }
        if (file.isEmpty()) {
            resultModel.setSuccess(ResponseEnum.UPLOAD_IMAGE_FAILED.isSuccess());
            resultModel.setCode(ResponseEnum.UPLOAD_FILE_FAILED.getCode());
            resultModel.setMsg(ResponseEnum.UPLOAD_FILE_FAILED.getMsg());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
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
                resultModel.setCode(ResponseEnum.UPLOAD_SUCCESS.getCode());
                resultModel.setSuccess(ResponseEnum.UPLOAD_SUCCESS.isSuccess());
                resultModel.setMsg(ResponseEnum.UPLOAD_SUCCESS.getMsg());
                resultModel.setExt("avator" + storeSongPath);
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
            resultModel.setMsg(ResponseEnum.UPLOAD_FAILED.getMsg());
            resultModel.setExt(e.getMessage());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
        } finally {
            return resultModel;
        }
    }

    /**
     * 更新歌曲
     */
    @PostMapping("/update")
    public Object updateSong(Integer id, String name, String introduction, String lyric, String mv) {
        ResultModel resultModel = new ResultModel();
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
            resultModel.setCode(ResponseEnum.UPLOAD_SUCCESS.getCode());
            resultModel.setSuccess(ResponseEnum.UPLOAD_SUCCESS.isSuccess());
            resultModel.setMsg(ResponseEnum.UPLOAD_SUCCESS.getMsg());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        }
        resultModel.setCode(ResponseEnum.UPLOAD_FAILED.getCode());
        resultModel.setSuccess(ResponseEnum.UPLOAD_FAILED.isSuccess());
        resultModel.setMsg(ResponseEnum.UPLOAD_FAILED.getMsg());
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    /**
     * 查询所有歌曲
     */
    @GetMapping("/selectAll")
    public Object selectAll(){
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setData(songService.selectAll());
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    /**
     * 查询所有歌曲--分页
     */
    @GetMapping("/selectByPager")
    public Object selectByPager(Integer page, Integer size){
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setData(songService.selectByPager(page, size));
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
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
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setData(songService.selectById(id));
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    /**
     * 查询随机20收歌曲
     * @return
     */
    @GetMapping("/selectRandom")
    public Object selectRandom(Integer nums, Integer userId){
        ResultModel resultModel = new ResultModel();
        String userSong = userId == null ? "youkeSong" : userId.toString() + "song";
        Calendar calendar = Calendar.getInstance();
        long ExpireTime = (24 - calendar.get(Calendar.HOUR)) * 3600L;
        if (redisUtil.get(userSong) == null){
            int num = nums == null ? 20 : nums;
            Set<Song> songs = new HashSet<>();
            List<Integer> songIds = new ArrayList<>();
            if (redisUtil.hasKey("songIds")){
                songIds = (List<Integer>) redisUtil.get("songIds");
            }else {
                songIds = songService.selectAllId();
                redisUtil.set("songIds", songIds);
            }
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
            resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
            resultModel.setCode(ResponseEnum.SUCCESS.getCode());
            resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
            resultModel.setData(songs);
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        }else {
            resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
            resultModel.setCode(ResponseEnum.SUCCESS.getCode());
            resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
            resultModel.setData(redisUtil.get(userSong));
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        }
    }

    /**
     * 根据歌名查询
     */
    @GetMapping("/selectByName")
    public Object selectByName(String name){
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        Object data = songService.selectByName(name);
        resultModel.setData(data);
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    @GetMapping("/selectLikeName")
    public Object selectLikeName(String name){
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setData(songService.selectLikeName(name));
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }
    /**
     * 根据歌手id查询
     */
    @GetMapping("/selectBySingerId")
    public Object selectBySingerId(Integer singerId, Integer page, Integer size){
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setData(songService.selectBySingerId(singerId, page, size));
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    /**
     * 更新歌曲图片
     */
    @PostMapping("/updateSongPic")
    public Object updateSongPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") int id){
        ResultModel resultModel = new ResultModel();
        Song song = songService.selectById(id);
        String pic = "." + song.getPic();
        if (!pic.equals("./img/songPic/Jay.jpg")){
            FileSystemUtils.deleteRecursively(new File(pic));
        }
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
            return resultModel;
        } catch (IOException e) {
            e.printStackTrace();
            resultModel.setSuccess(ResponseEnum.ERROR.isSuccess());
            resultModel.setCode(ResponseEnum.ERROR.getCode());
            resultModel.setMsg(e.getMessage());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
        }finally {
            return resultModel;
        }
    }


    /**
     * 更新歌曲
     */
    @PostMapping("/updateSongUrl")
    public Object updateSongUrl(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") int id){
        ResultModel resultModel = new ResultModel();
        Song song = songService.selectById(id);
        String url = "." + song.getUrl();
        System.out.println(url);
        FileSystemUtils.deleteRecursively(new File(url));
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
                resultModel.setCode(ResponseEnum.UPLOAD_SUCCESS.getCode());
                resultModel.setSuccess(ResponseEnum.UPLOAD_SUCCESS.isSuccess());
                resultModel.setMsg(ResponseEnum.UPLOAD_SUCCESS.getMsg());
                resultModel.setExt("avator" + storeAvatorPath);
                resultModel.setTimestamp(System.currentTimeMillis()/1000);
                return resultModel;
            }
            resultModel.setSuccess(ResponseEnum.UPLOAD_FILE_FAILED.isSuccess());
            resultModel.setCode(ResponseEnum.UPLOAD_FILE_FAILED.getCode());
            resultModel.setMsg(ResponseEnum.UPLOAD_FILE_FAILED.getMsg());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        } catch (IOException e) {
            e.printStackTrace();
            resultModel.setSuccess(ResponseEnum.UPLOAD_FILE_FAILED.isSuccess());
            resultModel.setCode(ResponseEnum.UPLOAD_FILE_FAILED.getCode());
            resultModel.setMsg(e.getMessage());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
        }finally {
            return resultModel;
        }
    }
}
