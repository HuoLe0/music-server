package com.huole.music.service.Impl;

import com.huole.music.dao.SingerMapper;
import com.huole.music.model.Pager;
import com.huole.music.model.Singer;
import com.huole.music.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 歌手Service实现类
 */
@Service
public class SingerServiceImpl implements SingerService {


    @Autowired
    private SingerMapper singerMapper;
    /**
     * 增加
     *
     * @param singer
     */
    @Override
    public boolean insert(Singer singer) {
        return singerMapper.insert(singer)>0;
    }

    /**
     * 修改
     *
     * @param singer
     */
    @Override
    public boolean update(Singer singer) {
        return singerMapper.update(singer)>0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return singerMapper.delete(id)>0;
    }

    /**
     * 根据id查询歌手
     *
     * @param id
     */
    @Override
    public Singer selectById(Integer id) {
        return singerMapper.selectById(id);
    }

    /**
     * 查询所有歌手
     */
    @Override
    public List<Singer> selectAll() {
        return singerMapper.selectAll();
    }

    @Override
    public Pager<Singer> selectByPager(Integer page, Integer size) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", (page-1)*size);
        params.put("size", size);
        Pager<Singer> pager = new Pager<Singer>();
        List<Singer> list = singerMapper.selectByPager(params);
        pager.setRows(list);
        pager.setTotal(singerMapper.count());
        return pager;
    }

    /**
     * 查询前十个歌手
     *
     * @return
     */
    @Override
    public List<Singer> selectTen() {
        return singerMapper.selectTen();
    }

    /**
     * 根据歌手名字模糊查询列表
     *
     * @param name
     */
    @Override
    public List<Singer> selectLikeName(String name) {
        return singerMapper.selectLikeName(name);
    }

    /**
     * 根据性别查询歌手
     *
     * @param sex
     */
    @Override
    public Pager<Singer> selectBySex(Integer sex, Integer page, Integer size) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sex", sex);
        params.put("page", (page-1)*size);
        params.put("size", size);
        Pager<Singer> pager = new Pager<Singer>();
        List<Singer> list = singerMapper.selectBySex(params);
        pager.setRows(list);
        pager.setTotal(singerMapper.countSex(sex));
        return pager;
    }
}
