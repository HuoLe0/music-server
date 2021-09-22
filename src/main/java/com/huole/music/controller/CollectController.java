package com.huole.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.huole.music.model.Collect;
import com.huole.music.service.CollectService;
import com.huole.music.service.ReturnService;
import com.huole.music.utils.Consts;
import com.huole.music.utils.ResponseEnum;
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

    @Autowired
    private ReturnService returnService;

    /**
     * 添加收藏
     * @param userId
     * @param type
     * @param songId
     * @return
     */
    @PostMapping("/add")
    public Object addCollect(Integer userId, Byte type, Integer songId){
        if(songId==null){
            returnService.setSuccess(ResponseEnum.COLLECT_FAILED.isSuccess());
            returnService.setCode(ResponseEnum.COLLECT_FAILED.getCode());
            returnService.setMsg(ResponseEnum.COLLECT_FAILED.getMsg());
            returnService.setTimestamp(System.currentTimeMillis()/1000);
            return returnService.getReturnValue();
        }
        if(collectService.existSongId(userId ,songId)){
            returnService.setSuccess(ResponseEnum.COLLECT_REPEATED.isSuccess());
            returnService.setCode(ResponseEnum.COLLECT_REPEATED.getCode());
            returnService.setMsg(ResponseEnum.COLLECT_REPEATED.getMsg());
            returnService.setTimestamp(System.currentTimeMillis()/1000);
            return returnService.getReturnValue();
        }
        //保存到收藏对象中
        Collect collect = new Collect();
        collect.setUserId(userId);
        collect.setType(type);
        collect.setSongId(songId);
        boolean flag = collectService.insert(collect);
        if (flag){//保存成功
            returnService.setSuccess(ResponseEnum.COLLECT_SUCCESS.isSuccess());
            returnService.setCode(ResponseEnum.COLLECT_SUCCESS.getCode());
            returnService.setMsg(ResponseEnum.COLLECT_SUCCESS.getMsg());
            returnService.setTimestamp(System.currentTimeMillis()/1000);
            return returnService.getReturnValue();
        }
        returnService.setSuccess(ResponseEnum.COLLECT_FAILED.isSuccess());
        returnService.setCode(ResponseEnum.COLLECT_FAILED.getCode());
        returnService.setMsg(ResponseEnum.COLLECT_FAILED.getMsg());
        returnService.setTimestamp(System.currentTimeMillis()/1000);
        return returnService.getReturnValue();
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
            returnService.setCode(ResponseEnum.MODIFY_SUCCESS.getCode());
            returnService.setSuccess(ResponseEnum.MODIFY_SUCCESS.isSuccess());
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
