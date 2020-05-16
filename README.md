Aspen
==========
## 一、背景

我也不知道我为啥要写这个项目，名字也是灵光一闪就定了个aspen，反正就是一个springboot的普通的web项目，逐渐完善成一个可以便捷开发中小型服务的项目

## 二、项目特性

1.自定义了一些注解，做了一下基本的项目组件

2.支持按钮级别的权限认证

3.支持mysql和redis多数据源

4.支持Generator一键生成数据库对应的单表增删改查相关代码

## 三、程序逻辑

1.填写用户名密码用POST请求访问/login接口，返回token令牌等信息，失败则直接返回身份错误信息。

2.在之后需要验证身份的请求的Headers中添加Authorization和登录时返回的token令牌。

3.服务端进行token认证，失败身份错误信息。

4.用JWT做认证（登录），Shiro做授权。

## 四、运行项目

-   通过git下载源码，本项目基于JDK1.8
    
-   采用Maven项目管理，模块化，导入IDEA
    
-   创建数据库aspen，数据库编码为UTF-8，执行aspen.sql文件，初始化数据
    
-   修改application-dev.yml，更新MySQL账号和密码
    
-   Eclipse、IDEA运行WebApplication.java，则可启动项目。或在liugh-parent目录下运行命令mvn clean package，然后在liugh-web/target目录下运行java -jar liugh-web.jar命令

-   访问登录接口：localhost:8080/login   账号：    密码：  
    
-   获取token访问其他接口

-   注意!!!!!编译器请安装lombok插件,不然会报红
    
    
彩蛋：项目注释完整，并且自定义了启动图案~


第一次做自己的项目，经验不足，如果大家有什么好的意见或批评，请务必issue一下。
