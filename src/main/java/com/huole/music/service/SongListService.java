package com.huole.music.service;

import com.huole.music.domain.Pager;
import com.huole.music.domain.Song;
import com.huole.music.domain.SongList;

import java.util.List;

/**
 * 歌单service接口
 */

public interface SongListService {

    /**
     * 增加
     */
    public boolean insert(SongList songList);

    /**
     * 增加
     */
    public boolean insertByConsumer(SongList songList);
    /**
     * 修改
     */
    public boolean update(SongList songList);

    /**
     * 删除
     */
    public boolean delete(Integer id);

    /**
     * 根据id查询歌单
     */
    public SongList selectById(Integer id);

    /**
     * 查询所有歌单
     */
    public List<SongList> selectAll();

    public Pager<SongList> selectByPager(Integer page, Integer size);

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
     * 根据标题查询列表
     */
    public List<SongList> selectByTitle(String title);

    /**
     * 根据标题模糊查询列表
     */
    public List<SongList> selectLikeTitle(String title);

    /**
     * 根据风格模糊查询列表
     */
    public Pager<SongList> selectLikeStyle(String style, Integer page, Integer size);

    /**
     * 查询歌单歌曲
     * @param songIds
     * @return
     */
    List<Song> selectSongs(String[] songIds);

    /**
     * 歌单添加歌曲
     */
    boolean addSong(Integer songListId, Integer songId);

    /**
     * 歌单删除歌曲
     */
    boolean deleteSong(Integer songListId, Integer songId);
}
