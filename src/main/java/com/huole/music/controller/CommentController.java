package com.huole.music.controller;

import com.huole.music.model.Comment;
import com.huole.music.model.ResultModel;
import com.huole.music.service.CommentService;
import com.huole.music.utils.ResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
        ResultModel resultModel = new ResultModel();
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
            resultModel.setSuccess(ResponseEnum.COMMENT_NULL.isSuccess());
            resultModel.setCode(ResponseEnum.COMMENT_NULL.getCode());
            resultModel.setMsg(ResponseEnum.COMMENT_NULL.getMsg());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        }
        comment.setContent(content);
        boolean flag = commentService.insert(comment);
        if (flag){//保存成功
            resultModel.setSuccess(ResponseEnum.COMMENT_SUCCESS.isSuccess());
            resultModel.setCode(ResponseEnum.COMMENT_SUCCESS.getCode());
            resultModel.setMsg(ResponseEnum.COMMENT_SUCCESS.getMsg());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        }
        resultModel.setSuccess(ResponseEnum.COMMENT_FAILED.isSuccess());
        resultModel.setCode(ResponseEnum.COMMENT_FAILED.getCode());
        resultModel.setMsg(ResponseEnum.COMMENT_FAILED.getMsg());
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
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
        ResultModel resultModel = new ResultModel();
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
        ResultModel resultModel = new ResultModel();
        Comment comment = new Comment();
        comment.setId(id);
        comment.setUp(up);
        boolean flag = commentService.update(comment);
        if (flag){//保存成功
            resultModel.setCode(ResponseEnum.LIKE_SUCCESS.getCode());
            resultModel.setSuccess(ResponseEnum.LIKE_SUCCESS.isSuccess());
            resultModel.setMsg(ResponseEnum.LIKE_SUCCESS.getMsg());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        }
        resultModel.setCode(ResponseEnum.LIKE_FAILED.getCode());
        resultModel.setSuccess(ResponseEnum.LIKE_FAILED.isSuccess());
        resultModel.setMsg(ResponseEnum.LIKE_FAILED.getMsg());
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }
}
