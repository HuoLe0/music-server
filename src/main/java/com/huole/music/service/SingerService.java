package com.huole.music.service;

import com.huole.music.domain.Singer;

import java.util.List;

/**
 * 歌手service接口
 */

public interface SingerService {

    /**
     * 增加
     */
    public boolean insert(Singer singer);

    /**
     * 修改
     */
    public boolean update(Singer singer);

    /**
     * 删除
     */
    public boolean delete(Integer id);

    /**
     * 根据id查询歌手
     */
    public Singer selectById(Integer id);

    /**
     * 查询所有歌手
     */
    public List<Singer> selectAll();

    /**
     * 根据歌手名字模糊查询列表
     */
    public List<Singer> selectLikeName(String name);

    /**
     * 根据性别查询歌手
     */
    public List<Singer> selectBySex(Integer sex);

}
