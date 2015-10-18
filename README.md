# J-Feiq

## 这是一个简单的JAVA版的飞秋(飞鸽).
## 能与桌面版和手机版的飞秋对接通讯.
* 没有任何第三方依赖.
* 面向接口的编程,方便扩展和二次开发.
* 轻松与应用程序结合互动,做到人机交互.

## 你要问它有什么用
* 放在web程序中,可以动态改变服务器日志级别,动态选择性接收日志信息.
* 代理Jenkins做到消息实时提醒,可以与飞秋互动来选择构建JOB.
* 智能机器人聊天问答系统.
* 只有你想不到的没有做不到的...


## 调用案例

```java
public static void main(String[] args) {
        FeiqServer feiqServer = new FeiqServer();
        feiqServer.setServerName("Hybebrin");
        feiqServer.start();
        feiqServer.tellAll("Hybebrin已经启动!");
}
```
