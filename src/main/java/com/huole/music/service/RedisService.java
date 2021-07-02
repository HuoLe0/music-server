package com.huole.music.service;

import com.alibaba.druid.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisService {
    //字符串
    public void setStringKey(String key,String value,Long time);
    public void setStringKey(String key,String value);

    //set
    public void setSetKey(String key, Set value);
    //list
    public void setListKey(String key, List value);

    public String getStringKey(String key);
    public Set getSetKey(String key);
    public List getListKey(String key);




}
