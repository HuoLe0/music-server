package com.huole.music.dao;

import com.huole.music.domain.Singer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 歌手Dao
 */
@Repository
public interface SingerMapper {

    /**
     * 增加
     */
    public int insert(Singer singer);

    /**
     * 修改
     */
    public int update(Singer singer);

    /**
     * 删除
     */
    public int delete(Integer id);

    /**
     * 根据id查询歌手
     */
    public Singer selectById(Integer id);

    /**
     * 查询所有歌手
     */
    public List<Singer> selectAll();

    /**
     * 查询前十个歌手
     * @return
     */
    public List<Singer> selectTen();

    /**
     * 根据歌手名字模糊查询列表
     */
    public List<Singer> selectLikeName(String name);

    /**
     * 根据性别查询歌手
     */
    public List<Singer> selectBySex(Integer sex);
}
