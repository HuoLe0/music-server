package com.huole.music.service.Impl;

import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //字符串
    public void setStringKey(String key,String value,Long time){
        setObject(key,value,time);
    }
    public void setStringKey(String key,String value){
        setObject(key,value,null);
    }

    //set
    public void setSetKey(String key, Set value){
        setObject(key,value,null);
    }
    //list
    public void setListKey(String key, List value){
        setObject(key,value,null);
    }

    public String getStringKey(String key){
        return (String) getObject(key,new String());
    }
    public Set getSetKey(String key){
        return (Set) getObject(key,new HashSet<String>());
    }
    public List getListKey(String key){
        return (List) getObject(key,new ArrayList<String>());
    }




    public void setObject(String key,Object value,Long time){
        if(StringUtils.isEmpty(key)||value==null){
            return;
        }
        //字符串类型
        if(value instanceof String){
            String value1= (String) value;
            if(time!=null){
                stringRedisTemplate.opsForValue().set(key,value1,time, TimeUnit.SECONDS);
            } else{
                stringRedisTemplate.opsForValue().set(key,value1);
            }
            return;
        }
        //list类型
        else if(value instanceof List){
            List<String> list= (List<String>) value;
            for (String s:list) {
                stringRedisTemplate.opsForList().leftPush(key,s);
            }
            return;
        }
        //set
        else if(value instanceof Set){
            Set<String> strings= (Set<String>) value;
            for (String s : strings) {
                stringRedisTemplate.opsForSet().add(key,s);
            }
            return;
        }
        /**
         * .....
         */
    }

    public Object getObject(String key,Object object){
        if(StringUtils.isEmpty(key)||object==null){
            return null;
        }
        else if (object instanceof String){
            return stringRedisTemplate.opsForValue().get(key);
        }
        else if(object instanceof List){
            return stringRedisTemplate.opsForList().range(key,0,stringRedisTemplate.opsForList().size(key));
        }
        else if(object instanceof Set){
            return stringRedisTemplate.opsForSet().members(key);
        }
        return null;
    }
}
