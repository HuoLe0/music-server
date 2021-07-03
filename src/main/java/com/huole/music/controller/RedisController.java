package com.huole.music.controller;

import com.huole.music.domain.Song;
import com.huole.music.service.SongService;
import com.huole.music.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @program: springbootdemo
 * @Date: 2019/2/22 15:03
 * @Author: zjjlive
 * @Description:
 */
@Slf4j
@RequestMapping("/redis")
@RestController
public class RedisController {

    private static int ExpireTime = 60;   // redis中存储的过期时间60s

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private SongService songService;

    @RequestMapping("set")
    public boolean redisset(String key, String value){
        List<Song> songList = songService.selectAll();
        return redisUtil.set(key,songList);
    }

    @RequestMapping("get")
    public Object redisget(String key){
        return redisUtil.get(key);
    }

    @RequestMapping("expire")
    public boolean expire(String key){
        return redisUtil.expire(key,ExpireTime);
    }
}
