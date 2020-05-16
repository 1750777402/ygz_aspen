package com.ygz.aspen.generator;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

/**
 * tinyint 转换成 Integer 而不是Byte
 * Created by xy on 2019/2/18.
 */
public class TinyIntTypeResolver extends JavaTypeResolverDefaultImpl {

    /**
     * 将tinyint转换为Integer，这里是关键所在
     */
    public TinyIntTypeResolver() {
        super();
        super.typeMap.put(-6, new JavaTypeResolverDefaultImpl.JdbcTypeInformation("TINYINT", new FullyQualifiedJavaType(Integer.class.getName())));
    }
}
