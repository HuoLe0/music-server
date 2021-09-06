package com.huole.music.model;

import java.io.Serializable;

/**
 * 歌单
 */
public class SongList implements Serializable {
    /*主键*/
    private Integer id;
    /*标题*/
    private String title;
    /*简介*/
    private String introduction;
    //歌单图片
    private String pic;
    //风格
    private String style;
    //用户id
    private Integer userId;
    //歌曲
    private String songs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getSongs() {
        return songs;
    }

    public void setSongs(String songs) {
        this.songs = songs;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

