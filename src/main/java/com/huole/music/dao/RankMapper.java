package com.huole.music.dao;

import com.huole.music.model.Rank;
import org.springframework.stereotype.Repository;

/**
 * 评分Dao
 */
@Repository
public interface RankMapper {

    /**
     * 增加
     */
    public int insert(Rank rank);

    /**
     * 查询总分
     */
    public int selectScoreSum(Integer songListId);

    /**
     * 查总评分人数
     */
    public int selectRankNum(Integer songListId);

    /**
     * 根据歌单id查询
     */
    public int verifyRank(Integer songListId,Integer consumerId);


}
