# java_vnpy

#### 介绍
java版本期货程序化交易系统，包括行情服务，交易服务会逐步完善，CTP版本上期技术官网下载6.3.15 穿透式，
目前项目无架构设计，只针对ctp进行封装调用，实现基本交易，以及行情，无对外提供接口，

#### 软件架构
封装 CTP API 编译成java可调用，用java代码封装mdApi(行情服务Api)，td（交易服务Api）。
CTP系统架构图：
![输入图片说明](https://images.gitee.com/uploads/images/2019/1211/135138_f91d5d2a_4920822.png "屏幕截图.png")
此项目目前，封装ctp：
![输入图片说明](https://images.gitee.com/uploads/images/2019/1211/140710_1624e759_4920822.png "无标题.png")

#### 安装教程

1.  sinmnow官网申请账号，以及官网常见问题里查看服务器地址，也可用项目中默认的测试账号
2.  spring boot  无需安装 下载依赖jar包即可  目前无对外提供接口

#### 使用说明

1.   //资源目录library.path
        System.out.println(System.getProperty("java.library.path"));  查看
     引入 动态库 会报错，可在环境 变量更改，或者项目启动时的 VM options 设置java.library.path
2.  7-24 小时行情服务器，早上  周末关机不运行 tcp://180.168.146.187:10131       7-24 模拟环境地址      

    若服务器地址填错项目启动会报错 RuntimeError:Invalid location in line 44 of file ..\..\source\network\ServiceName.cpp
    jvm 会崩溃
3.  可运行gateway--ctpGateway--CtpGateway.java中的main方法进行测试
    可运行event--EventEngine.java测试事件引擎

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 码云特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5.  码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
