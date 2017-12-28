# MMSNS博客系统

## 概述  
**MMSNS博客是一个正在开发的开源的的个人博客管理系统。MMSNS博客主要包括我的文章、我的动弹、我的群组、我的资讯、我的图库、我的文档等功能模块。**


## 项目架构
#### 前台展示
前台展示主要包含主页、我的文章、我的动弹、我的群组、我的资讯、我的图库、我的文档六大功能模块。用户在未登录的情况下也可以预览这些信息，同时也可以查看详情信息。而且在详情页面可以查看关于该资源的评论信息。   

![MMSNS博客系统首页](https://github.com/babymm/mmsns/blob/master/wiki/images/index.png?raw=true)
![MMSNS我的文章列表](https://github.com/babymm/mmsns/blob/master/wiki/images/article.png?raw=true)
![我的文章详情](https://github.com/babymm/mmsns/blob/master/wiki/images/articleDetail.png?raw=true)

#### 个人中心
用户登录系统之后可以上传资料（编写文章、发布动弹、创建群组、发布资讯、发布图库、上传文档），同时也可以对自己上传的资源进行管理。同时用户也可以对各种资讯进行点赞和收藏功能，并且在个人中心中进行管理。同时用户可以在个人中心中修改用户资料、修改登录密码、查看资讯评论等。  
![](https://github.com/babymm/mmsns/blob/master/wiki/images/myArticle.png?raw=true)
![](https://github.com/babymm/mmsns/blob/master/wiki/images/admin.png?raw=true)

#### 后台管理
后台管理系统是为管理员用户准备的，主要包含三方面内容。第一是准备系统必须要的资源，例如一些数据字典（文章系统分类数据字典、省市区数据字典、ip地址映射数据字典等。）、新闻资讯定时爬取。第二是审核管理系统各种资源，例如图片审查。第三是权限分配，将各种功能分配给不同的角色不同的用户，方便后台管理。  
![后台管理系统尚在创建中]()

## 技术选型
#### 前端组件
- layui
- jquery
- velocity

#### 后台组件
- spring
- springMVC
- mybatis
- druid
- apache-shiro 
- weibocom matan

## 功能模块
各个功能模块通过 [motan](https://github.com/weibocom/motan) 提供rpc服务供网站和管理后台系统进行调用。简化了调试速度（如果哪一个模块出现问题，直接修改问题并将该模块重启即可，而不需要重启整个项目。）同时也大大减少了资源浪费，因为部署了网站项目和后台管理系统项目，所以通过rpc调用增加了系统的可利用性。
![模块之间架构](https://raw.githubusercontent.com/babymm/mmsns/master/wiki/images/modular.png)
#### 我的文章
- 我的文章模块主要包含文章分类、文章、文章点赞、文章收藏、文章评论四个功能。用户未登录的情况在可以在首页和我的文章页面进行文章的浏览和评论查看。如果想要进行文章评论、文章点赞、文章收藏则必须要进行登录才可以操作（通过apache-shiro做权限控制的）。
- 用户登录之后可以进行文章发布、文章点赞、文章收藏等功能。同时可以再我的文章里面进行文章模块管理，对发布的文章、转载的文章、收藏的文章、点赞的文章进行相应的操作。
- 在个人信息管理页面，用户可以对文章分类进行添加、删除等操作。同时也可以对文章评论进行恢复、删除等操作。后期会慢慢的添加文章导入导出等功能，方便用户操作。

#### 我的动弹
#### 我的群组
#### 我的资讯
#### 我的图库
#### 我的文库  




## 重要说明
**对于一个专门搞后台的程序员来说，最大的痛苦莫过于设计了，自己设计出来的页面，总有一种想死的感觉（真丑）。所以有的页面借鉴与[oschina](http://www.oschina.net/)的设计风格，同时功能也有参考[jeesns](http://www.jeesns.cn/)。再此做郑重声明。**

## 联系方式
**以上观点纯属个人看法，如有不同，欢迎指正。  
email:<babymm@aliyun.com>  
github:[https://github.com/babymm](https://github.com/babymm)**