package com.huole.music.dao;

import com.huole.music.model.Song;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 歌曲Dao
 */
@Repository
public interface SongMapper {

    /**
     * 增加
     */
    public int insert(Song song);

    /**
     * 修改
     */
    public int update(Song song);

    /**
     * 删除
     */
    public int delete(Integer id);

    /**
     * 根据歌曲id查询歌曲
     */
    public Song selectById(Integer id);

    /**
     * 查询所有歌曲
     */
    public List<Song> selectAll();

    public List<Integer> selectAllId();

    public List<Song> selectByPager(Map<String, Object> params);

    public long count();

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
    public List<Song> selectBySingerId(Map<String, Object> params);

    public long countSingerSong(Integer singerId);

}
