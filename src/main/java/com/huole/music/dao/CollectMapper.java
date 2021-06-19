package com.huole.music.dao;

import com.huole.music.domain.Collect;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 收藏Dao
 */
@Repository
public interface CollectMapper {

    /**
     * 增加
     */
    public int insert(Collect collect);

    /**
     * 修改
     */
    public int update(Collect collect);

    /**
     * 删除
     */
    public int delete(Integer id);

    public int deleteByUserIdSongId(@Param("userId") Integer userId, @Param("songId") Integer songId);

    /**
     * 查询所有收藏
     */
    public List<Collect> selectAll();

    /**
     * 查询某个用户下的所有收藏
     */
    public List<Collect> selectByUserId(Integer userId);

    /**
     * 查询某个用户下的是否收藏某个歌曲
     */
    public int existSongId(@Param("userId") Integer userId, @Param("songId") Integer songId);


}
