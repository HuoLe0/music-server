package com.huole.music.service;

import com.huole.music.model.Rank;

/**
 * 评分service接口
 */

public interface RankService {

    /**
     * 增加
     */
    public boolean insert(Rank rank);

    /**
     * 查询总分
     */
    public int selectScoreSum(Integer songListId);

    /**
     * 查总评分人数
     */
    public int selectRankNum(Integer songListId);

    /**
     * 计算平均分
     */
    public int rankOfSongListId(Integer songListId);

    /**
     * 校验
     */
    public boolean verifyRank(Integer songListId,Integer consumerId);
}
