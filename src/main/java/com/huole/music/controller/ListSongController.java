package com.huole.music.controller;


import com.alibaba.fastjson.JSONObject;
import com.huole.music.model.ListSong;
import com.huole.music.service.ListSongService;
import com.huole.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 歌单歌曲管理controller
 */
@RestController
@RequestMapping("/listSong")
public class ListSongController {

    @Autowired
    private ListSongService listSongService;

    /**
     * 添加歌曲歌单
     */
    @PostMapping("/add")
    public Object addListSong(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        //获取前端传来的参数
        String songId = request.getParameter("songId").trim();//歌曲id
        String songListId = request.getParameter("songListId").trim();//歌单id
        ListSong listSong = new ListSong();
        listSong.setSongId(Integer.parseInt(songId));
        listSong.setSongListId(Integer.parseInt(songListId));
        boolean flag = listSongService.insert(listSong);
        if (flag) {//保存成功
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "添加成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "添加失败");
        return jsonObject;
    }

    /**
     * 更新歌曲歌单
     */
    @PostMapping("/update")
    public Object updateListSong(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        //获取前端传来的参数
        String id = request.getParameter("id").trim();//id
        String songId = request.getParameter("songId").trim();//歌曲id
        String songListId = request.getParameter("songListId").trim();//歌单id
        ListSong listSong = new ListSong();
        listSong.setId(Integer.parseInt(id));
        listSong.setSongId(Integer.parseInt(songId));
        listSong.setSongListId(Integer.parseInt(songListId));
        boolean flag = listSongService.update(listSong);
        if (flag) {//保存成功
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "修改失败");
        return jsonObject;
    }

    /**
     * 删除
     * @param request
     * @return
     */
    @GetMapping("/delete")
    public Object deleteListSong(HttpServletRequest request){
        String songId = request.getParameter("songId").trim();
        String songListId = request.getParameter("songListId").trim();
        return listSongService.deleteBySongIdAndSongListId(Integer.parseInt(songId),Integer.parseInt(songListId));
    }

    /**
     * 根据主键查询歌单歌曲
     * @param request
     * @return
     */
    @GetMapping("/selectByPrimaryKey")
    public Object selectByPrimaryKey(HttpServletRequest request){
        String id = request.getParameter("id").trim();
        return listSongService.selectByPrimaryKey(Integer.parseInt(id));
    }

    /**
     * 查询歌曲
     * @param
     * @return
     */
    @GetMapping("/allListSong")
    public Object allListSong(){
        return listSongService.allListSong();
    }

    /**
     * 根据歌曲id主键查询歌单歌曲
     * @param request
     * @return
     */
    @GetMapping("/listSongOfSongId")
    public Object listSongOfSongId(HttpServletRequest request){
        String songId = request.getParameter("songId").trim();
        return listSongService.listSongOfSongId(Integer.parseInt(songId));
    }

    /**
     * 根据歌单id主键查询歌单歌曲
     * @param request
     * @return
     */
    @GetMapping("/listSongOfSongListId")
    public Object listSongOfSongListId(HttpServletRequest request){
        String songListId = request.getParameter("songListId").trim();
        return listSongService.listSongOfSongListId(Integer.parseInt(songListId));
    }
}
