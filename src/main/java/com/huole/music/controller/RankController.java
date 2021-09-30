package com.huole.music.controller;


import com.alibaba.fastjson.JSONObject;
import com.huole.music.model.Rank;
import com.huole.music.model.ResultModel;
import com.huole.music.service.RankService;
import com.huole.music.utils.Consts;
import com.huole.music.utils.ResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 评分管理controller
 */
@RestController
@RequestMapping("/rank")
public class RankController {

    @Autowired
    private RankService rankService;

    /**
     * 添加歌曲
     */
    @PostMapping("/add")
    public Object addRank(Integer songListId, Integer consumerId, Integer score) {
        ResultModel resultModel = new ResultModel();
        Rank rank = new Rank();
        boolean flag1 = rankService.verifyRank(songListId,consumerId);
        if (!flag1){
            rank.setSongListId(songListId);
            rank.setConsumerId(consumerId);
            rank.setScore(score);
            boolean flag = rankService.insert(rank);
            if (flag) {//保存成功
                resultModel.setSuccess(ResponseEnum.RANK_SUCCESS.isSuccess());
                resultModel.setCode(ResponseEnum.RANK_SUCCESS.getCode());
                resultModel.setMsg(ResponseEnum.RANK_SUCCESS.getMsg());
                resultModel.setTimestamp(System.currentTimeMillis()/1000);
                return resultModel;
            }
            resultModel.setSuccess(ResponseEnum.RANK_FAILED.isSuccess());
            resultModel.setCode(ResponseEnum.RANK_FAILED.getCode());
            resultModel.setMsg(ResponseEnum.RANK_FAILED.getMsg());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        }else {
            resultModel.setSuccess(ResponseEnum.RANK_REPEATED.isSuccess());
            resultModel.setCode(ResponseEnum.RANK_REPEATED.getCode());
            resultModel.setMsg(ResponseEnum.RANK_REPEATED.getMsg());
            resultModel.setTimestamp(System.currentTimeMillis()/1000);
            return resultModel;
        }
    }

    /**
     * 查询歌单总分
     */
    @GetMapping("/score")
    public Object selectScoreSum(Integer songListId){
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setData(rankService.selectScoreSum(songListId));
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    /**
     * 查询歌单总数
     */
    @GetMapping("/num")
    public Object selectRankNum(Integer songListId){
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setData(rankService.selectRankNum(songListId));
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }

    /**
     * 查询歌单总分数
     * @return
     */
    @GetMapping("/rank")
    public Object rankOfSongListId(Integer songListId){
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(ResponseEnum.SUCCESS.isSuccess());
        resultModel.setCode(ResponseEnum.SUCCESS.getCode());
        resultModel.setData(rankService.rankOfSongListId(songListId));
        resultModel.setMsg(ResponseEnum.SUCCESS.getMsg());
        resultModel.setTimestamp(System.currentTimeMillis()/1000);
        return resultModel;
    }
}
