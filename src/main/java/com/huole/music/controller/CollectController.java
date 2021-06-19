package com.huole.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.huole.music.domain.Collect;
import com.huole.music.service.CollectService;
import com.huole.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


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
     */
    @PostMapping("/add")
    public Object addCollect(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String userId = request.getParameter("userId");//用户id
        String type = request.getParameter("type").trim();//类型
        String songId = request.getParameter("songId");//歌曲id
//        String songListId = request.getParameter("songListId");//歌单id
//        System.out.println(songId);
        if(songId==null || songId.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"收藏歌曲为空");
            return jsonObject;
        }
        if(collectService.existSongId(Integer.parseInt(userId),Integer.parseInt(songId))){
            jsonObject.put(Consts.CODE,2);
            jsonObject.put(Consts.MSG,"您已收藏该歌曲");
            return jsonObject;
        }
        //保存到收藏对象中
        Collect collect = new Collect();
        collect.setUserId(Integer.parseInt(userId));
        collect.setType(new Byte(type));
        collect.setSongId(Integer.parseInt(songId));
//        collect.setSongListId(Integer.parseInt(songListId));
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
     * @param request
     * @return
     */
    @PostMapping("/update")
    public Object updateCollect(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();
        String userId = request.getParameter("userId").trim();//用户id
        String type = request.getParameter("type").trim();//类型
        String songId = request.getParameter("songId").trim();//歌曲id
        String songListId = request.getParameter("songListId").trim();//歌单id
        //保存到收藏对象中
        Collect collect = new Collect();
        collect.setId(Integer.parseInt(id));
        collect.setUserId(Integer.parseInt(userId));
        collect.setType(new Byte(type));
        if(songId!=null&&songId.equals("")){
            songId = null;
        }else {
            collect.setSongId(Integer.parseInt(songId));
        }
        if(songListId != null && songListId.equals("")){
            songListId = null;
        }else {
            collect.setSongListId(Integer.parseInt(songListId));
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
     * @param request
     * @return
     */
    @GetMapping("/delete")
    public Object deleteCollect(HttpServletRequest request){
        String id = request.getParameter("id").trim();//主键
        return collectService.delete(Integer.parseInt(id));
    }

    /**
     * 删除收藏
     * @param request
     * @return
     */
    @GetMapping("/deleteByUserIdSongId")
    public Object delete(HttpServletRequest request){
        String userId = request.getParameter("userId").trim();//用户id
        String songId = request.getParameter("songId").trim();//歌曲id
        return collectService.deleteByUserIdSongId(Integer.parseInt(userId),Integer.parseInt(songId));
    }



    /**
     * 查询所有收藏
     */
    @GetMapping("/selectAll")
    public Object selectAll(){
        return collectService.selectAll();
    }

    /**
     * 根据收藏名字模糊查询列表
     */
    @GetMapping("/selectByUserId")
    public Object selectByUserId(HttpServletRequest request){
        String userId = request.getParameter("userId").trim();
        return collectService.selectByUserId(Integer.parseInt(userId));
    }

}
