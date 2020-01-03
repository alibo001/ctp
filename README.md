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
3.  可运行gateway--ctpGateway--CtpGateway.java中的main方法进行测试（注意，请注释本类的第54行代码，如果使用springboot启动类则不需要更改）。

```
eventEngine.start();//测试main方法所需注释，sping boot启动，不须此步骤。
```

    可运行event--EventEngine.java测试事件引擎。

4.端口

```java
server.port=8088
```
5.接口封装位置

```
javaCtp\XR_Vnpy\src\main\java\com\nbplus\vnpy\rest
```
调用步骤  127.0.0.1:8088/javactp/con  连接ctp后其他接口才可正常工作。




#### 参与贡献
gt_vv

### 项目支持
1.有问题提issues，会持续更新项目。
2.作者QQ：754992236 可以联系探讨交流。
3.欢迎start。
