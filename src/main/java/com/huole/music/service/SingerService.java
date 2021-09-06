package com.huole.music.service;

import com.huole.music.model.Pager;
import com.huole.music.model.Singer;

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

    public Pager<Singer> selectByPager(Integer page, Integer size);

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
    public Pager<Singer> selectBySex(Integer sex, Integer page, Integer size);

}
