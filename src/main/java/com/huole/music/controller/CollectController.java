package com.huole.music.controller;

import com.huole.music.model.Collect;
import com.huole.music.model.ResultModel;
import com.huole.music.service.CollectService;
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

    /**
     * 添加收藏
     * @param userId
     * @param type
     * @param songId
     * @return
     */
    @PostMapping("/add")
    public Object addCollect(Integer userId, Byte type, Integer songId){
        ResultModel resultModel = new ResultModel();
        if(songId==null){
            resultModel.setSuccess(ResponseEnum.COLLECT_FAILED.isSuccess());
            resultModel.setCode(ResponseEnum.COLLECT_FAILED.getCode());
            resultModel.setMsg(ResponseEnum.COLLECT_FAILED.getMsg());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        }
        if(collectService.existSongId(userId ,songId)){
            resultModel.setSuccess(ResponseEnum.COLLECT_REPEATED.isSuccess());
            resultModel.setCode(ResponseEnum.COLLECT_REPEATED.getCode());
            resultModel.setMsg(ResponseEnum.COLLECT_REPEATED.getMsg());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        }
        //保存到收藏对象中
        Collect collect = new Collect();
        collect.setUserId(userId);
        collect.setType(type);
        collect.setSongId(songId);
        boolean flag = collectService.insert(collect);
        if (flag){//保存成功
            resultModel.setSuccess(ResponseEnum.COLLECT_SUCCESS.isSuccess());
            resultModel.setCode(ResponseEnum.COLLECT_SUCCESS.getCode());
            resultModel.setMsg(ResponseEnum.COLLECT_SUCCESS.getMsg());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        }
        resultModel.setSuccess(ResponseEnum.COLLECT_FAILED.isSuccess());
        resultModel.setCode(ResponseEnum.COLLECT_FAILED.getCode());
        resultModel.setMsg(ResponseEnum.COLLECT_FAILED.getMsg());
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
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
        ResultModel resultModel = new ResultModel();
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
            resultModel.setCode(ResponseEnum.MODIFY_SUCCESS.getCode());
            resultModel.setSuccess(ResponseEnum.MODIFY_SUCCESS.isSuccess());
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
