package com.huole.music.dao;

import com.huole.music.domain.SongList;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 歌曲Dao
 */
@Repository
public interface SongListMapper {

    /**
     * 增加
     */
    public int insert(SongList songList);

    /**
     * 增加
     */
    public int insertByConsumer(SongList songList);
    /**
     * 修改
     */
    public int update(SongList songList);

    /**
     * 删除
     */
    public int delete(Integer id);

    /**
     * 根据主键查询整个对象
     */
    public SongList selectByPrimaryKey(Integer id);

    /**
     * 查询所有歌单
     */
    public List<SongList> allSongList();
    /**
     * 查询所有歌单
     */
    public List<SongList> allConsumerSongList(Integer userId);

    /**
     * 根据标题模糊查询列表
     */
    public List<SongList> songListOfTitle(String title);

    /**
     * 根据标题模糊查询列表
     */
    public List<SongList> likeTitle(String title);

    /**
     * 根据风格模糊查询列表
     */
    public List<SongList> likeStyle(String style);

}