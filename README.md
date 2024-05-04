# 软件工程团队作业Alpha冲刺

## 环境搭建

本项目使用Docker来搭建运行的环境，并使用shell脚本来快速格式化启动项目

需要安装：

1. Docker的运行环境（这里推荐Windows的Docker Desktop版本，网络上说这玩意非常占空间但胜在安装简单）
2. shell脚本的运行环境（Windows下安装了git就会有一个git bash，在git bash下用`./build/install.sh`就可以运行脚本，最好为git bash配置一下环境变量）

开发人员可依据自己主机的运行环境的安装情况来自行决定要不要使用Docker，这里说明一下Docker中安装的东西：

1. MySQL（root密码为123456）用于访问的端口是8888（因为本机安装的MySQL一般使用的是3306，所以随意改了个端口来使用）
2. Redis用于访问的端口是6379（Windwos下安装Redis可能有点困难，所以假设没安装Redis），未开启保护模式（这样不用密码都能访问）

上面的步骤不仅会启动MySQL和Redis这两个环境，还会运行打包好的jar包从而占用8080端口。

但后端开发的时候经常是使用IDEA来运行服务器的，所以我们不希望每一次都进行package然后再运行脚本，而是重新运行服务器本身。

为此，我提供了另外一个脚本：`depends.sh`用来启动MySQL和Redis并且运行完成之后保证项目服务器不启动。

还有关于连接Docker容器里的MySQL和Redis这件事，我已经在`application.yaml`中完成了。

也就是说，直接执行`depends.sh`脚本后，就可以直接用IDEA里面的RUN功能了，环境什么的不用担心。

==也就是说，后端可以这样使用本项目：==

```shell
cd build
./depend.sh
```
---

对于前端，一般不需要使用IDEA进行运行，运行的频率也不高，这时候就不用打开IDEA了。直接把项目clone到本地，然后使用如下命令：

```shell
cd build
./install.sh
```

就可以在Docker中直接运行项目，依赖什么的都准备好了的。这样就可以不用担心环境问题而专心于前端功能的开发。

当使用服务器时发现是服务器无法处理前端的请求或者请求有误时，通知PM，然后由后端解决。