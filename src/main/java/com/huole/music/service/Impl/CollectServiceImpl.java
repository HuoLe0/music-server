package com.huole.music.service.Impl;

import com.huole.music.dao.CollectMapper;
import com.huole.music.dao.SongMapper;
import com.huole.music.domain.Collect;
import com.huole.music.domain.Song;
import com.huole.music.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏service实现
 */
@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private SongMapper songMapper;
    /**
     * 增加
     *
     * @param collect
     */
    @Override
    public boolean insert(Collect collect) {
        return collectMapper.insert(collect)>0;
    }

    /**
     * 修改
     *
     * @param collect
     */
    @Override
    public boolean update(Collect collect) {
        return collectMapper.update(collect)>0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return collectMapper.delete(id)>0;
    }

    @Override
    public boolean deleteByUserIdSongId(Integer userId, Integer songId) {
        return collectMapper.deleteByUserIdSongId(userId,songId)>0;
    }

    /**
     * 查询所有收藏
     */
    @Override
    public List<Collect> selectAll() {
        return collectMapper.selectAll();
    }

    /**
     * 查询某个用户下的所有收藏
     *
     * @param userId
     */
    @Override
    public List<Song> selectByUserId(Integer userId) {
        List<Collect> collectList = collectMapper.selectByUserId(userId);
        List<Song> songList = new ArrayList<>();
        for(Collect collect : collectList){
            songList.add(songMapper.selectById(collect.getSongId()));
        }
        return songList;
    }

    /**
     * 查询某个用户下的是否收藏某个歌曲
     *
     * @param userId
     * @param songId
     */
    @Override
    public boolean existSongId(Integer userId, Integer songId) {
        return collectMapper.existSongId(userId,songId)>0;
    }


}
