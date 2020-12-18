package com.huole.music.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 歌单评论
 */
public class Comment implements Serializable {
    //主键
    private Integer id;
    //用户id
    private Integer userId;
    //评论类型
    private String type;
    //歌id
    private Integer songId;
    //歌单id
    private Integer songListId;
    //内容
    private String content;
    //创建时间
    private Date createTime;
    //评论点赞数
    private Integer up;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSongId() {
        return songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    public Integer getSongListId() {
        return songListId;
    }

    public void setSongListId(Integer songListId) {
        this.songListId = songListId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUp() {
        return up;
    }

    public void setUp(Integer up) {
        this.up = up;
    }
}
