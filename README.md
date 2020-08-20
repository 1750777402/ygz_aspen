# ygz_aspen
**本项目是一个java+vue开发的后台管理项目，项目比较简单，有时间再慢慢完善**

demo地址: http://123.207.153.230

每两小时还原数据

账号:ygz 密码:123456

![1.png](https://img-blog.csdnimg.cn/20200820104900430.png)

![2.png](https://img-blog.csdnimg.cn/20200820104944463.png)

![3.png](https://img-blog.csdnimg.cn/20200820105045309.png)

***后端基本架构***

- springboot
- mysql，并支持多数据源(需要项目中进行配置)
- 集成了redis 但由于个人比较懒 项目中为真正使用
- jwt配合shiro做权限校验
- 一些自定义的注解(免登录、单机限流)

***本项目采用以下技术***

前端：

vue 2.5.17

elementUI 2.13.2

软件运行环境

Java jdk8

MySql 5.7

操作系统：

CentOS 7.2


***基础环境安装***


```
1、安装npm运行环境(略)

    安装前端项目依赖包
    
    cd ygz_aspen_web/
    npm install

2、修改你的配置文件

    ygz_aspen_web/config 目录下的：

    dev.env.js #开发环境

    prod.env.js #生产环境

3、安装mysql，创建数据库，使用：https://github.com/1750777402/ygz_aspen/blob/master/ygz_aspen/aspen.sql 脚本初始化数据库

4、安装redis

5、安装jdk8

```

***开发环境的运行***

运行前端
```
cd ygz_aspen_web/
npm run dev
#接下来启动后端即可进行调试
```
运行后端
```
idea 导入后端项目 运行springboot项目即可
```

***生产环境的运行***

**1、安装nginx（略）**

参考：https://blog.csdn.net/qq_34203492/article/details/85165509
然后配置nginx代理：
listen       80;
server_name  localhost;

location / {
    root   html;
    index  index.html index.htm;
}
把80端口转发到: /usr/local/nginx/html 目录下的index.html 
**这段nginx配置需要根据上面的博客中nginx进行配置，如果不按照博客中进行配置，也可以自行安装nginx进行配置**

**2、生产静态页面**
```
cd ygz_aspen_web/
npm run build
将ygz_aspen_web/dist目录下生成的文件复制到你的服务器nginx web root目录(或者按照上面博文中进行配置的话，直接复制到:/usr/local/nginx/html 目录下)
```
**3、生产后端**
```
打包后端项目成jar包 (看个人，也可以打包成war包运行)
把打包的文件上传到服务器
命令运行：nohup java -jar ygz_aspen.jar --spring.profiles.active=prod &
启动完成(注意redis和mysql要能连接成功)
```
