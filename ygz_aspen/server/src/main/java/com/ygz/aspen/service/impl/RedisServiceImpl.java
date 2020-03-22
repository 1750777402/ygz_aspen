package com.ygz.aspen.service.impl;

import com.alibaba.fastjson.JSON;
import com.ygz.aspen.common.constant.CacheConstant;
import com.ygz.aspen.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    @Qualifier("redisTemplateMaster")
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void set(String key, String value) {
        this.set(key, value, CacheConstant.HOURS_24);
    }

    @Override
    public void set(String key, String value, Long expireTime) {
        stringRedisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
    }

    @Override
    public void setForever(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public Set<String> scan(String prefix) {
        Set<String> data = stringRedisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {

                Set<String> binaryKeys = new HashSet<>();

                Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(prefix + "*").count(2000).build());
                while (cursor.hasNext()) {
                    binaryKeys.add(new String(cursor.next()));
                }
                return binaryKeys;
            }
        });
        return data;
    }

    @Override
    public Long getExpire(String key) {
        return stringRedisTemplate.getExpire(key);
    }

    @Override
    public void pushMessage(String topic, String message) {
        stringRedisTemplate.opsForList().leftPush(topic, message);
    }

    @Override
    public String popMessage(String topic) {
        return stringRedisTemplate.opsForList().rightPop(topic);
    }

    @Override
    public String firstMessage(String topic) {
        List<String> messsages = stringRedisTemplate.opsForList().range(topic, 0, 0);
        if (CollectionUtils.isNotEmpty(messsages)) {
            return messsages.get(0);
        }
        return null;
    }

    @Override
    public Long listSize(String topic) {
        return stringRedisTemplate.opsForList().size(topic);
    }


    @Override
    public void setObj(String key, Object value) {
        this.setObj(key, value, CacheConstant.HOURS_24);
    }

    @Override
    public void setObj(String key, Object value, Long exp) {
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(value), exp, TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public <T> T getObj(String key, Class<T> cls) {
        String data = this.get(key);
        try {
            if (!StringUtils.isEmpty(data)) {
                return JSON.parseObject(data, cls);
            }
        } catch (Throwable e) {
            log.error("get obj failed : {}, {}", key, data);
        }
        return null;
    }

    @Override
    public List<String> multiGet(List<String> keys) {
        return stringRedisTemplate.opsForValue().multiGet(keys);
    }

    @Override
    public <T> List<T> multiGetObj(List<String> keys, Class<T> cls) {
        List<String> data = this.multiGet(keys);
        if (CollectionUtils.isNotEmpty(data)) {
            return data.stream().map(str -> JSON.parseObject(str, cls)).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public boolean expire(String key, long expire) {
        return stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public Long increment(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public Long decrement(String key, long delta) {
        return stringRedisTemplate.opsForValue().decrement(key, delta);
    }

    @Override
    public <T> List<T> getArrayList(String key, Class<T> cls) {
        String data = this.get(key);
        try {
            if (!StringUtils.isEmpty(data)) {
                return JSON.parseArray(data, cls);
            }
        } catch(Throwable e) {
            log.error("get arrayList failed : {}, {}", key, data);
        }
        return null;
    }



    @Override
    public Boolean zSetAdd(String key, double score, String member) {
        return stringRedisTemplate.opsForZSet().add(key, member, score);
    }

    @Override
    public Long zSetAddList(String key, Set<ZSetOperations.TypedTuple<String>> tuples) {
        return stringRedisTemplate.opsForZSet().add(key, tuples);
    }

    @Override
    public Double zSetIncrement(String key, double incrementScore, String member) {
        return stringRedisTemplate.opsForZSet().incrementScore(key, member, incrementScore);
    }

    @Override
    public Double getZSetScore(String key, String member) {
        return stringRedisTemplate.opsForZSet().score(key, member);
    }

    @Override
    public Long getZsetSize(String key) {
        return stringRedisTemplate.opsForZSet().zCard(key);
    }

    @Override
    public Set<ZSetOperations.TypedTuple<String>> zSetReverseRangeWithScores(String key, long start, long end) {
        return stringRedisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    @Override
    public Long deleteZsetMember(String key, String... member) {
        return stringRedisTemplate.opsForZSet().remove(key, member);
    }

    @Override
    public Boolean existsKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    @Override
    public void multiSet(Map<String, String> param) {
        stringRedisTemplate.opsForValue().multiSet(param);
    }


    @Override
    public void hSet(String key, String hKey, Object value) {
        stringRedisTemplate.opsForHash().put(key, hKey, value);
    }

    @Override
    public Object hGet(String key, String hKey) {
        return stringRedisTemplate.opsForHash().get(key, hKey);
    }

    @Override
    public List<String> lRange(String key, int start, int stop) {
        List<String> list = stringRedisTemplate.opsForList().range(key, start, stop);
        if(list != null && list.size() > 0){
            return list;
        }
        return null;
    }


}
