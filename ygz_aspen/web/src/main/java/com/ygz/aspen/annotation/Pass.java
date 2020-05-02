package com.ygz.aspen.annotation;

import java.lang.annotation.*;

/**
 * 在Controller方法上加入该注解不会验证身份
 * 注意一个点  在标注了此注解的地方可以获取当前登录用户信息
 * 但能不能成功获取取决于请求有没有带认证token信息
 * 如果带了就可以正常获取，如果不带则会抛出空指针
 * 所以建议在标注了此注解的方法中获取当前用户信息时加上异常捕获，做好异常处理
 * @author : ygz
 */
@Target( { ElementType.METHOD } )
@Retention( RetentionPolicy.RUNTIME )
@Documented
public @interface Pass {

}
