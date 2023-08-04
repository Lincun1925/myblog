# 个人在线博客系统:smile:
#### 功能描述：前台可查看博文等信息，后台可管理系统和前台信息，如删写博文等
#### 涉及技术栈：MySQL，Redis，MyBatisPlus，SpringSecurity，OSS，SpringBoot，Nginx
***
>测试用户
>>账号：test
>>密码：test_1
>>
>云服务器配置：内存2核4G  
>[前台地址](http://118.195.213.131/)    
>[后台地址](http://118.195.213.131:81)   
***
## 设计流程
### 1. 基本框架
+ 分两个服务，即前台博客和后台管理系统，均为Springboot工程
+ MySQL做业务存储，Redis缓存Token和文章实时浏览量
+ <ins>基于OSS存储图片数据，Swagger编写接口文档</ins>
### 2. 认证鉴权
+ <ins>基于SpringSecurity做认证鉴权，基于JWT封装和解析Token</ins>
### 3. 其他重点
+ <ins>基于CommandLineRunner做缓存预热，基于Scheduled异步批量写入数据库</ins>
+ <ins>自定义注解，基于AOP切面编程为重要接口织入日志信息</ins>
