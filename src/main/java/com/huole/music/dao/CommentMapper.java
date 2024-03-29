package com.huole.music.dao;

import com.huole.music.model.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 歌手Dao
 */
@Repository
public interface CommentMapper {

    /**
     * 增加
     */
    public int insert(Comment comment);

    /**
     * 修改
     */
    public int update(Comment comment);

    /**
     * 删除
     */
    public int delete(Integer id);

    public int deleteBySongId(Integer songId);

    public int deleteBySongListId(Integer songListId);

    /**
     * 根据主键查询整个对象
     */
    public Comment selectById(Integer id);

    /**
     * 查询所有评论
     */
    public List<Comment> allComment();

    /**
     * 查询某个歌曲下的所有评论
     */
    public List<Comment> commentOfSongId(Integer songId);

    /**
     * 查询某个歌单下的所有评论
     */
    public List<Comment> commentOfSongListId(Integer songListId);
}
