package com.huole.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.huole.music.domain.Comment;
import com.huole.music.service.CommentService;
import com.huole.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;


/**
 * 评论控制类
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 添加评论
     * @param userId
     * @param type
     * @param songId
     * @param songListId
     * @param content
     * @return
     */
    @PostMapping("/add")
    public Object addComment(Integer userId, String type, Integer songId, Integer songListId, String content){
        JSONObject jsonObject = new JSONObject();
        //保存到评论对象中
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setType(type);
        if(new Byte(type) ==0){
            comment.setSongId(songId);
        }else{
            comment.setSongListId(songListId);
        }
        if(content == null || content.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"评论内容不能为空");
            return jsonObject;
        }
        comment.setContent(content);
        boolean flag = commentService.insert(comment);
        if (flag){//保存成功
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"评论提交成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"评论失败");
        return jsonObject;
    }

    /**
     * 更新评论
     * @param id
     * @param userId
     * @param type
     * @param songId
     * @param songListId
     * @param content
     * @param up
     * @return
     */
    @PostMapping("/update")
    public Object updateComment(Integer id, Integer userId, String type, Integer songId, Integer songListId, String content, Integer up){
        JSONObject jsonObject = new JSONObject();
        //保存到评论对象中
        Comment comment = new Comment();
        comment.setId(id);
        comment.setUserId(userId);
        comment.setType(type);
        if(songId!=null){
            comment.setSongId(songId);
        }
        if(songListId != null){
            comment.setSongListId(songListId);
        }
        comment.setContent(content);
        comment.setUp(up);//点赞数
        boolean flag = commentService.update(comment);
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
     * 删除评论
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public Object deleteComment(Integer id){
        return commentService.delete(id);
    }

    @GetMapping("/deleteBySongId")
    public Object deleteBySongId(Integer songId){
        return commentService.delete(songId);
    }

    @GetMapping("/deleteBySongListId")
    public Object deleteBySongListId(Integer songListId){
        return commentService.delete(songListId);
    }

    /**
     * 根据主键查询整个对象
     */
    @GetMapping("/selectById")
    public Object selectByPrimaryKey(Integer id){
        return commentService.selectById(id);
    }

    /**
     * 查询所有评论
     */
    @GetMapping("/allComment")
    public Object allComment(){
        return commentService.allComment();
    }

    /**
     * 查询歌曲评论
     */
    @GetMapping("/commentOfSongId")
    public Object commentOfSongId(Integer songId){
        return commentService.commentOfSongId(songId);
    }

    /**
     * 查询歌单评论
     */
    @GetMapping("/commentOfSongListId")
    public Object commentOfSex(Integer songListId){
        return commentService.commentOfSongListId(songListId);
    }


    /**
     * 给某个评论点赞
     */
    @PostMapping("/like")
    public Object like(Integer id, Integer up){
        JSONObject jsonObject = new JSONObject();
        Comment comment = new Comment();
        comment.setId(id);
        comment.setUp(up);
        boolean flag = commentService.update(comment);
        if (flag){//保存成功
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"点赞成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"点赞失败");
        return jsonObject;
    }
}
