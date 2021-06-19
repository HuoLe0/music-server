package com.huole.music.service.Impl;

import com.huole.music.dao.SongListMapper;
import com.huole.music.dao.SongMapper;
import com.huole.music.domain.Song;
import com.huole.music.domain.SongList;
import com.huole.music.service.SongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 歌单service实现
 */
@Service
public class SongListServiceImpl implements SongListService {

    @Autowired
    private SongListMapper songListMapper;

    @Autowired
    private SongMapper songMapper;

    /**
     * 增加
     *
     * @param songList
     */
    @Override
    public boolean insert(SongList songList) {
        return songListMapper.insert(songList)>0;
    }

    /**
     * 增加
     *
     * @param songList
     */
    @Override
    public boolean insertByConsumer(SongList songList) {
        return songListMapper.insertByConsumer(songList)>0;
    }

    /**
     * 修改
     *
     * @param songList
     */
    @Override
    public boolean update(SongList songList) {
        return songListMapper.update(songList)>0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return songListMapper.delete(id)>0;
    }

    /**
     * 根据id查询歌单
     *
     * @param id
     */
    @Override
    public SongList selectById(Integer id) {
        return songListMapper.selectById(id);
    }

    /**
     * 查询所有歌单
     */
    @Override
    public List<SongList> selectAll() {
        return songListMapper.selectAll();
    }

    @Override
    public List<Integer> selectAllId() {
        return songListMapper.selectAllId();
    }

    /**
     * 查询用户所有歌单
     *
     * @param userId
     */
    @Override
    public List<SongList> selectAllConsumer(Integer userId) {
        return songListMapper.selectAllConsumer(userId);
    }

    /**
     * 查询前十个歌单
     */
    @Override
    public List<SongList> selectTen() {
        return songListMapper.selectTen();
    }

    /**
     * 根据标题查询列表
     *
     * @param title
     */
    @Override
    public List<SongList> selectByTitle(String title) {
        return songListMapper.selectByTitle(title);
    }

    /**
     * 根据标题模糊查询列表
     *
     * @param title
     */
    @Override
    public List<SongList> selectLikeTitle(String title) {
        return songListMapper.selectLikeTitle(title);
    }

    /**
     * 根据风格模糊查询列表
     *
     * @param style
     */
    @Override
    public List<SongList> selectLikeStyle(String style) {
        return songListMapper.selectLikeStyle(style);
    }

    /**
     * 查询歌单歌曲
     *
     * @param songIds
     * @return
     */
    @Override
    public List<Song> selectSongs(String[] songIds) {
        List<Song> songs = new ArrayList<>();
        for (String id : songIds){
            if (id.length() > 0) {
                songs.add(songMapper.selectById(Integer.parseInt(id)));
            }
        }
        return songs;
    }

    /**
     * 歌单添加歌曲
     *
     * @param
     * @return
     */
    @Override
    public boolean addSong(Integer songListId, Integer songId) {
        String songs = songListMapper.selectById(songListId).getSongs() == null ? "" : songListMapper.selectById(songListId).getSongs();
        songs += "," + songId.toString();
        return songListMapper.addSong(songListId, songs) > 0;
    }

    /**
     * 歌单删除歌曲
     *
     * @param songListId
     * @param songId
     */
    @Override
    public boolean deleteSong(Integer songListId, Integer songId) {
        String songs = songListMapper.selectById(songListId).getSongs();
        songs = songs.replace(","+songId.toString(), "");
        return songListMapper.addSong(songListId, songs) > 0;
    }
}
