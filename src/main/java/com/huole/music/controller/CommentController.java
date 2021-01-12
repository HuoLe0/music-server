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
     */
    @PostMapping("/add")
    public Object addComment(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String userId = request.getParameter("userId");//用户id
        String type = request.getParameter("type").trim();//类型
        String songId = request.getParameter("songId");//歌曲id
        String songListId = request.getParameter("songListId");//歌单id
        String content = request.getParameter("content").trim();//内容
        System.out.println(songId);
        //保存到评论对象中
        Comment comment = new Comment();
        comment.setUserId(Integer.parseInt(userId));
        comment.setType(type);
        if(new Byte(type) ==0){
            comment.setSongId(Integer.parseInt(songId));
        }else{
            comment.setSongListId(Integer.parseInt(songListId));
        }
        if(content == "" || content.equals(null)){
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
     * @param request
     * @return
     */
    @PostMapping("/update")
    public Object updateComment(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();
        String userId = request.getParameter("userId").trim();//用户id
        String type = request.getParameter("type").trim();//类型
        String songId = request.getParameter("songId").trim();//歌曲id
        String songListId = request.getParameter("songListId").trim();//歌单id
        String content = request.getParameter("content").trim();//内容
        String up = request.getParameter("up").trim();//评论点赞数
        //保存到评论对象中
        Comment comment = new Comment();
        comment.setId(Integer.parseInt(id));
        comment.setUserId(Integer.parseInt(userId));
        comment.setType(type);
        if(songId!=null&&songId.equals("")){
            songId = null;
        }else {
            comment.setSongId(Integer.parseInt(songId));
        }
        if(songListId != null && songListId.equals("")){
            songListId = null;
        }else {
            comment.setSongListId(Integer.parseInt(songListId));
        }
        comment.setContent(content);
        comment.setUp(Integer.parseInt(up));
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
     * @param request
     * @return
     */
    @GetMapping("/delete")
    public Object deleteComment(HttpServletRequest request){
        String id = request.getParameter("id").trim();//主键
        return commentService.delete(Integer.parseInt(id));
    }

    @GetMapping("/deleteBySongId")
    public Object deleteBySongId(HttpServletRequest request){
        String songId = request.getParameter("songId").trim();//主键
        return commentService.delete(Integer.parseInt(songId));
    }

    @GetMapping("/deleteBySongListId")
    public Object deleteBySongListId(HttpServletRequest request){
        String songListId = request.getParameter("songListId").trim();//主键
        return commentService.delete(Integer.parseInt(songListId));
    }

    /**
     * 根据主键查询整个对象
     */
    @GetMapping("/selectById")
    public Object selectByPrimaryKey(HttpServletRequest request){
        String id = request.getParameter("id").trim();//主键
        return commentService.selectById(Integer.parseInt(id));
    }

    /**
     * 查询所有评论
     */
    @GetMapping("/allComment")
    public Object allComment(){
        return commentService.allComment();
    }

    /**
     * 根据评论名字模糊查询列表
     */
    @GetMapping("/commentOfSongId")
    public Object commentOfSongId(HttpServletRequest request){
        String songId = request.getParameter("songId").trim();
        return commentService.commentOfSongId(Integer.parseInt(songId));
    }

    /**
     * 根据评论性别查询列表
     */
    @GetMapping("/commentOfSongListId")
    public Object commentOfSex(HttpServletRequest request){
        String songListId = request.getParameter("songListId").trim();
        return commentService.commentOfSongListId(Integer.parseInt(songListId));
    }


    /**
     * 给某个评论点赞
     */
    @PostMapping("/like")
    public Object like(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();
        String up = request.getParameter("up").trim();//评论点赞数
//        String userId = request.getParameter("userId").trim();//用户id
        Comment comment = new Comment();
        comment.setId(Integer.parseInt(id));
        comment.setUp(Integer.parseInt(up));
//        comment.setUserId(Integer.parseInt(userId));
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
