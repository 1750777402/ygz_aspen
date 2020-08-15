package com.ygz.aspen.common.utils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;

/**
 * 集合工具类
 */
public class AspenCollectionUtil {

    /**
     * 获取两个set的并集
     * @param setF
     * @param setT
     * @return
     */
    public static <T> Set<T> getSetUnion(Set<T> setF, Set<T> setT){
        Sets.SetView<T> union = Sets.union(setF, setT);
        return union.stream().collect(Collectors.toSet());
    }

    /**
     * 获取两个set差集:返回只存在于setF左集独有的数据
     * @param setF
     * @param setT
     * @return
     */
    public static <T> Set<T> getSetDifference(Set<T> setF, Set<T> setT){
        Sets.SetView<T> difference = Sets.difference(setF, setT);
        return difference.stream().collect(Collectors.toSet());
    }

    /**
     * 获取两个set的交集
     * @param setF
     * @param setT
     * @return
     */
    public static <T> Set<T> getSetIntersection(Set<T> setF, Set<T> setT){
        Sets.SetView<T> difference = Sets.intersection(setF, setT);
        return difference.stream().collect(Collectors.toSet());
    }

    /**
     * 把list的数据根据split拼接起来,自动过滤data中的null
     * 栗子：data:[1,3,4,6]  split:"."  return: "1.3.4.6"
     * @param data
     * @param split
     * @return
     */
    public static String jointList(List data, String split){
        return Joiner.on(split).skipNulls().join(data);
    }

    /**
     * 把根据split分隔的字符串str变成List
     * 栗子：str:"1.3.5.7.9" split："."   return：["1","3","5","7","9"]
     * @param str
     * @param split
     * @return
     */
    public static List<String> splitIntoList(String str, String split){
        return Splitter.on(split).omitEmptyStrings().splitToList(str);
    }

}
