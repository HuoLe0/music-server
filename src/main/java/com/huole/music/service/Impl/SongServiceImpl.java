package com.huole.music.service.Impl;

import com.huole.music.dao.SongMapper;
import com.huole.music.domain.Song;
import com.huole.music.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 歌曲service实现
 */
@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongMapper songMapper;
    /**
     * 增加
     *
     * @param song
     */
    @Override
    public boolean insert(Song song) {
        return songMapper.insert(song)>0;
    }

    /**
     * 修改
     *
     * @param song
     */
    @Override
    public boolean update(Song song) {
        return songMapper.update(song)>0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return songMapper.delete(id)>0;
    }

    /**
     * 根据歌曲id查询歌曲
     *
     * @param id
     */
    @Override
    public Song selectById(Integer id) {
        return songMapper.selectById(id);
    }

    /**
     * 查询所有歌曲
     */
    @Override
    public List<Song> selectAll() {
        return songMapper.selectAll();
    }

    /**
     * 根据歌曲名字查询列表
     *
     * @param name
     */
    @Override
    public List<Song> selectByName(String name) {
        return songMapper.selectByName(name);
    }

    /**
     * 根据歌曲名字模糊查询列表
     *
     * @param name
     */
    @Override
    public List<Song> selectLikeName(String name) {
        return songMapper.selectLikeName(name);
    }

    /**
     * 根据歌手id查询列表Singer
     *
     * @param singerId
     */
    @Override
    public List<Song> selectBySingerId(Integer singerId) {
        return songMapper.selectBySingerId(singerId);
    }
}
