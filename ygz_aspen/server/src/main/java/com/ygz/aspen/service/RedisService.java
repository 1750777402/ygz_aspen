package com.ygz.aspen.service;

import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisService {

    /**
     * 缓存一天
     * 存储数据
     */
    void set(String key, String value);


    /**
     * 定义缓存超时时间
     *
     * @param key
     * @param value
     * @param expireTime 单位秒
     */
    void set(String key, String value, Long expireTime);

    /**
     * 永久保存
     *
     * @param key
     * @param value
     */
    void setForever(String key, String value);

    /**
     * 存储对象数据
     */
    void setObj(String key, Object value);

    /**
     * 存储对象数据
     */
    void setObj(String key, Object value, Long exp);

    /**
     * 获取数据
     */
    String get(String key);

    /**
     * 获取对象
     *
     * @param key
     * @param cls
     * @param <T>
     * @return
     */
    <T> T getObj(String key, Class<T> cls);

    /**
     * 批量获取
     *
     * @param key
     * @return
     */
    List<String> multiGet(List<String> key);

    /**
     * 批量获取对象
     *
     * @param key
     * @param cls
     * @param <T>
     * @return
     */
    <T> List<T> multiGetObj(List<String> key, Class<T> cls);

    /**
     * 设置超期时间
     */
    boolean expire(String key, long expire);

    /**
     * 删除数据
     */
    void remove(String key);

    /**
     * 自增操作
     *
     * @param delta 自增步长
     */
    Long increment(String key, long delta);

    /**
     * 自减操作
     *
     * @param delta 自减步长
     */
    Long decrement(String key, long delta);

    /**
     * 扫描表
     *
     * @param prefix
     * @return
     */
    Set<String> scan(String prefix);

    /**
     * 获取过期时间
     *
     * @param key
     * @return
     */
    Long getExpire(String key);

    /**
     * 推送消息
     *
     * @param topic
     * @param message
     */
    void pushMessage(String topic, String message);

    /**
     * 拉取消息
     *
     * @param topic
     * @return
     */
    String popMessage(String topic);


    /**
     * 拉取第一个 不删除
     *
     * @param topic
     * @return
     */
    String firstMessage(String topic);

    /**
     * 队列长度
     *
     * @param topic
     * @return
     */
    Long listSize(String topic);

    /**
     * 获取集合对象数据
     *
     * @param key
     * @param cls
     * @param <T>
     * @return
     */
    <T> List<T> getArrayList(String key, Class<T> cls);


    /**
     * 向有序集合添加一个，或者更新已存在成员的分数
     *
     * @param key    有序集合的key
     * @param score  排序分数
     * @param member 集合中的唯一标识
     * @return
     */
    Boolean zSetAdd(String key, double score, String member);

    /**
     * 向有序集合添加多个成员，或者更新已存在成员的分数
     *
     * @param key
     * @param tuples
     * @return
     */
    Long zSetAddList(String key, Set<ZSetOperations.TypedTuple<String>> tuples);

    /**
     * 对集合中的某个排序值增量incrementScore
     *
     * @param key            有序集合对key
     * @param incrementScore 增量值
     * @param member         集合中的唯一标识
     * @return
     */
    Double zSetIncrement(String key, double incrementScore, String member);

    /**
     * 获取有序队列中的某个元素的分数
     *
     * @param key    有序集合的key
     * @param member 集合中的唯一标识
     * @return
     */
    Double getZSetScore(String key, String member);

    /**
     * 获取key对应有序集合的总数
     *
     * @param key
     * @return
     */
    Long getZsetSize(String key);

    /**
     * 从大到小获取第start到第end的集合中的元素 比如获取这个key的从大到小的前十名，strat为0，end为9
     *
     * @param key   有序集合的key
     * @param start 开始元素排名
     * @param end   结束元素排名
     * @return
     */
    Set<ZSetOperations.TypedTuple<String>> zSetReverseRangeWithScores(String key, long start, long end);

    /**
     * 删除zset集合中的某个元素 返回被删除元素个数
     *
     * @param key
     * @param member
     * @return
     */
    Long deleteZsetMember(String key, String... member);

    /**
     * 查看key是否存在
     *
     * @param key
     * @return
     */
    Boolean existsKey(String key);

    /**
     * 批量缓存
     *
     * @param param
     */
    void multiSet(Map<String, String> param);


    /**
     * 设置哈希值
     *
     * @param key
     * @param hKey
     * @param value
     */
    void hSet(String key, String hKey, Object value);

    /**
     * 获取哈希值
     *
     * @param key
     * @param hKey
     * @return
     */
    Object hGet(String key, String hKey);


    /**
     * 获取列表指定范围内的元素
     *
     * @param key
     * @param start
     * @param stop
     * @return
     */
    List<String> lRange(String key, int start, int stop);


}
