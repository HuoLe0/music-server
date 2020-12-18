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
    public Object addRank(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        //获取前端传来的参数
        String songListId = request.getParameter("songListId");//歌单id
        String consumerId = request.getParameter("consumerId");//用户id
        String score = request.getParameter("score");//评分
        Rank rank = new Rank();
        boolean flag1 = rankService.verifyRank(Integer.parseInt(songListId),Integer.parseInt(consumerId));
        if (!flag1){
            rank.setSongListId(Integer.parseInt(songListId));
            rank.setConsumerId(Integer.parseInt(consumerId));
            rank.setScore(Integer.parseInt(score));
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
//        System.out.println(songListId+ "-"+ consumerId+ "-"+ score);
    }


    /**
     * 查询歌单总分
     * @return
     */
    @GetMapping("/score")
    public Object selectScoreSum(HttpServletRequest request){
        String songListId = request.getParameter("songListId").trim();//歌单id
        return rankService.selectScoreSum(Integer.parseInt(songListId));
    }

    /**
     * 查询歌单总人数
     * @return
     */
    @GetMapping("/num")
    public Object selectRankNum(HttpServletRequest request){
        String songListId = request.getParameter("songListId").trim();//歌单id
        return rankService.selectRankNum(Integer.parseInt(songListId));
    }

    /**
     * 查询歌单总人数
     * @return
     */
    @GetMapping("/rank")
    public Object rankOfSongListId(HttpServletRequest request){
        String songListId = request.getParameter("songListId").trim();//歌单id
        return rankService.rankOfSongListId(Integer.parseInt(songListId));
    }




}
