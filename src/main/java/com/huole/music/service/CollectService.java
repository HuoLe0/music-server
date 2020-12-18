package com.huole.music.service;

import com.huole.music.domain.Collect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectService {
    /**
     * 增加
     */
    public boolean insert(Collect collect);

    /**
     * 修改
     */
    public boolean update(Collect collect);

    /**
     * 删除
     */
    public boolean delete(Integer id);

    public boolean deleteByUserIdSongId(Integer userId,Integer songId);

    /**
     * 查询所有收藏
     */
    public List<Collect> allCollect();

    /**
     * 查询某个用户下的所有收藏
     */
    public List<Collect> collectOfUserId(Integer userId);

    /**
     * 查询某个用户下的是否收藏某个歌曲
     */
    public boolean existSongId(@Param("userId") Integer userId, @Param("songId") Integer songId);

}
