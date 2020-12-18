package com.huole.music.dao;

import com.huole.music.domain.ListSong;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 歌单歌曲Dao
 */
@Repository
public interface ListSongMapper {

    /**
     * 增加
     */
    public int insert(ListSong listSong);

    /**
     * 修改
     */
    public int update(ListSong listSong);

    /**
     * 删除
     */
    public int delete(Integer id);

    public int deleteBySongIdAndSongListId(Integer songId, Integer songListId);

    /**
     * 根据主键查询整个对象
     */
    public ListSong selectByPrimaryKey(Integer id);

    /**
     * 查询所有歌单歌曲
     */
    public List<ListSong> allListSong();

    /**
     * 根据歌曲id模糊查询
     */
    public List<ListSong> listSongOfSongId(Integer songId);

    /**
     * 根据歌单id查询列表歌曲
     */
    public List<ListSong> listSongOfSongListId(Integer songListId);

}
