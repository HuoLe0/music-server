package com.huole.music.service;

import com.huole.music.domain.ListSong;

import java.util.List;

/**
 * 歌单歌曲service接口
 */

public interface ListSongService {

    /**
     * 增加
     */
    public boolean insert(ListSong listSong);

    /**
     * 修改
     */
    public boolean update(ListSong listSong);

    /**
     * 删除
     */
    public boolean delete(Integer id);

    public boolean deleteBySongIdAndSongListId(Integer songId, Integer songListId);

    /**
     * 根据主键查询整个对象
     */
    public ListSong selectByPrimaryKey(Integer id);

    /**
     * 查询所有歌单歌曲
     */
    public List<ListSong> allListSong();

    /**
     * 根据歌曲id查询列表
     */
    public List<ListSong> listSongOfSongId(Integer songId);

    /**
     * 根据歌单id查询
     */
    public List<ListSong> listSongOfSongListId(Integer songListId);

}
