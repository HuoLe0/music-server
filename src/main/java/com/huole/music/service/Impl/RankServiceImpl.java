package com.huole.music.service.Impl;

import com.huole.music.dao.RankMapper;
import com.huole.music.model.Rank;
import com.huole.music.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 评分service实现
 */
@Service
public class RankServiceImpl implements RankService {

    @Autowired
    private RankMapper rankMapper;
    /**
     * 增加
     *
     * @param rank
     */
    @Override
    public boolean insert(Rank rank) {
        return rankMapper.insert(rank)>0;
    }

    /**
     * 查询总分
     *
     * @param songListId
     */
    @Override
    public int selectScoreSum(Integer songListId) {
        return rankMapper.selectScoreSum(songListId);
    }

    /**
     * 查总评分人数
     *
     * @param songListId
     */
    @Override
    public int selectRankNum(Integer songListId) {
        return rankMapper.selectRankNum(songListId);
    }

    /**
     * 计算平均分
     *
     * @param songListId
     */
    @Override
    public int rankOfSongListId(Integer songListId) {
        int rankNum = rankMapper.selectRankNum(songListId);
        if(rankNum==0){
            return 5;
        }
        return rankMapper.selectScoreSum(songListId) / rankNum;
    }

    /**
     * 校验
     * @param songListId
     * @param consumerId
     */
    @Override
    public boolean verifyRank(Integer songListId, Integer consumerId) {
        return rankMapper.verifyRank(songListId,consumerId)>0;
    }
}
