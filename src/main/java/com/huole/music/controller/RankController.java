package com.huole.music.controller;


import com.alibaba.fastjson.JSONObject;
import com.huole.music.domain.Rank;
import com.huole.music.service.RankService;
import com.huole.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

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
        JSONObject jsonObject = new JSONObject();
        Rank rank = new Rank();
        boolean flag1 = rankService.verifyRank(songListId,consumerId);
        if (!flag1){
            rank.setSongListId(songListId);
            rank.setConsumerId(consumerId);
            rank.setScore(score);
            boolean flag = rankService.insert(rank);
            if (flag) {//保存成功
                jsonObject.put(Consts.CODE, 1);
                jsonObject.put(Consts.MSG, "评价成功");
                return jsonObject;
            }
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "评价失败");
            return jsonObject;
        }else {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "请勿重复评价！");
            return jsonObject;
        }
    }

    /**
     * 查询歌单总分
     */
    @GetMapping("/score")
    public Object selectScoreSum(Integer songListId){
        return rankService.selectScoreSum(songListId);
    }

    /**
     * 查询歌单总数
     */
    @GetMapping("/num")
    public Object selectRankNum(Integer songListId){
        return rankService.selectRankNum(songListId);
    }

    /**
     * 查询歌单总分数
     * @return
     */
    @GetMapping("/rank")
    public Object rankOfSongListId(Integer songListId){
        return rankService.rankOfSongListId(songListId);
    }




}
