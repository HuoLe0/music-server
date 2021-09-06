package com.huole.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.huole.music.model.Collect;
import com.huole.music.service.CollectService;
import com.huole.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 收藏控制类
 */
@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    private CollectService collectService;

    /**
     * 添加收藏
     * @param userId
     * @param type
     * @param songId
     * @return
     */
    @PostMapping("/add")
    public Object addCollect(Integer userId, Byte type, Integer songId){
        JSONObject jsonObject = new JSONObject();
        if(songId==null){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"收藏歌曲为空");
            return jsonObject;
        }
        if(collectService.existSongId(userId ,songId)){
            jsonObject.put(Consts.CODE,2);
            jsonObject.put(Consts.MSG,"您已收藏该歌曲");
            return jsonObject;
        }
        //保存到收藏对象中
        Collect collect = new Collect();
        collect.setUserId(userId);
        collect.setType(type);
        collect.setSongId(songId);
        boolean flag = collectService.insert(collect);
        if (flag){//保存成功
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"收藏成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"收藏失败");
        return jsonObject;
    }

    /**
     * 更新收藏
     * @param id
     * @param userId
     * @param type
     * @param songId
     * @param songListId
     * @return
     */
    @PostMapping("/update")
    public Object updateCollect(Integer id, Integer userId, Byte type, Integer songId, Integer songListId){
        JSONObject jsonObject = new JSONObject();

        //保存到收藏对象中
        Collect collect = new Collect();
        collect.setId(id);
        collect.setUserId(userId);
        collect.setType(type);
        if(songId!=null){
            collect.setSongId(songId);
        }
        if(songListId != null){
            collect.setSongListId(songListId);
        }
        boolean flag = collectService.update(collect);
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
     * 删除收藏
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public Object deleteCollect(Integer id){
        return collectService.delete(id);
    }

    /**
     * 删除用户收藏歌曲
     * @param userId
     * @param songId
     * @return
     */
    @GetMapping("/deleteByUserIdSongId")
    public Object delete(Integer userId, Integer songId){
        return collectService.deleteByUserIdSongId(userId, songId);
    }

    /**
     * 查询所有收藏
     */
    @GetMapping("/selectAll")
    public Object selectAll(){
        return collectService.selectAll();
    }

    /**
     * 查询用户收藏歌曲
     * @param userId
     * @return
     */
    @GetMapping("/selectByUserId")
    public Object selectByUserId(Integer userId){
        return collectService.selectByUserId(userId);
    }

}
