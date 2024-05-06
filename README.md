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

## 接口可能用到的Util

### 验证码发送

发送给指定邮箱验证码的方法定义在`util`包下的`EmailUtil`类中

使用演示：

```Java
// 向邮箱2457699535@aliyun.com发送验证码
// EmailUtil.sendCaptcha会返回发送出去的验证码
String chaptcha = EmailUtil.sendCaptcha("2457699535@aliyun.com");
System.out.println(chaptcha);
```

## 一些工具的接口

### 邮件发送验证码

使用的例子：

```Java
String reciever = "2457699535@aliyun.com";
// 向指定邮箱发送随机的6位数字验证码，并返回发送的验证码
String chaptcha = EmailUtil.sendCaptcha(reciever);
System.out.println(chaptcha);
```

### JWT的生成

```Java
import com.southdipper.teamwork.util.JwtUtil;

User user;
// 生成JWT令牌，并将username和id存入令牌中 
String token = JwtUtil.generateToken(user.getId(), user.getUsername());
// 通过token可以取出事先存好的id, username
Integer id = JwtUtil.getIdFromToken(token);
String username = JwtUtil.getUsernameFromToken(token);
// 检查JWT是否被篡改,true表示未被篡改，false表示被篡改了
if(!JwtUtil.verify(token)) {
    System.out.println("已被篡改")
}
```

### Redis存储JWT和邮箱验证码

```Java
import com.southdipper.teamwork.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
// 注入Redis服务
@Autowired
RedisService redisService;
// Redis缓存JWT令牌
User user; 
String token = JwtUtil.generateToken(user.getId(), user.getUsername());
redisService.saveJWT(user.getUsername(), token);
// 如何检查JWT
if(!JwtUtil.verify(token)) {
        System.out.println("已被篡改");
}
else {
    // true: token存在于Redis缓存
    boolean result = redisService.checkJWT(JwtUtil.getUsernameFromToken(token), token);
    if(!result) {
        System.out.println("token不存在于缓存中, 可能是过期了，或者修改了密码");
    }
}


```