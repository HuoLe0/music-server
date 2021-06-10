package com.huole.music.service;

import com.huole.music.domain.Song;

import java.util.List;

/**
 * 歌曲service接口
 */

public interface SongService {

    /**
     * 增加
     */
    public boolean insert(Song song);

    /**
     * 修改
     */
    public boolean update(Song song);

    /**
     * 删除
     */
    public boolean delete(Integer id);

    /**
     * 根据歌曲id查询歌曲
     */
    public Song selectById(Integer id);

    /**
     * 查询所有歌曲
     */
    public List<Song> selectAll();

    /**
     * 根据歌曲名字查询列表
     */
    public List<Song> selectByName(String name);

    /**
     * 根据歌曲名字模糊查询列表
     */
    public List<Song> selectLikeName(String name);

    /**
     * 根据歌手id查询列表Singer
     */
    public List<Song> selectBySingerId(Integer singerId);

}
