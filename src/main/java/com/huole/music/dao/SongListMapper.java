package com.huole.music.dao;

import com.huole.music.domain.Song;
import com.huole.music.domain.SongList;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 歌曲Dao
 */
@Repository
public interface SongListMapper {

    /**
     * 增加
     */
    public int insert(SongList songList);

    int addSong(Integer id, String songs);

    /**
     * 用户自建歌单
     */
    public int insertByConsumer(SongList songList);
    /**
     * 修改歌单
     */
    public int update(SongList songList);

    /**
     * 删除
     */
    public int delete(Integer id);

    /**
     * 根据id查询歌单
     */
    public SongList selectById(Integer id);

    /**
     * 查询所有歌单
     */
    public List<SongList> selectAll();

    public List<SongList> selectByPager(Map<String, Object> params);

    public long count();

    public List<Integer> selectAllId();
    /**
     * 查询用户所有歌单
     */
    public List<SongList> selectAllConsumer(Integer userId);

    /**
     * 查询前十个歌单
     */
    public List<SongList> selectTen();

    /**
     * 根据标题模糊查询列表
     */
    public List<SongList> selectByTitle(String title);

    /**
     * 根据标题模糊查询列表
     */
    public List<SongList> selectLikeTitle(String title);

    /**
     * 根据风格模糊查询列表
     */
    public List<SongList> selectLikeStyle(Map<String, Object> params);

    public long countStyle(String style);

    /**
     * 查询歌单歌曲
     * @param songIds
     * @return
     */
    List<Song> selectSongs(List<Integer> songIds);

}
