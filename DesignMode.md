# 安卓设计架构

这三种架构的核心目标都是把界面、逻辑、数据拆分开，避免代码全都堆在 Activity/Fragment 里（也就是常说的 “上帝类”），让代码好维护、好测试。

## MVC （已淘汰）

最早的设计架构

MVC 其实是 Android 默认的设计，MVC 里将代码分为三个部分：

- Model：封装数据与业务规则（如 User、Repository）。

- View：纯展示层（XML 布局）。

- Controller：协调输入与输出（接收点击 → 调用 Model → 更新 View）。

在 Android 中，没有独立的 Controller。Activity / Fragment 被迫同时承担 View（UI 渲染） 和 Controller（事件处理） 双重角色：

也就是说V和C都放在一起 无法拆分

 
##  MVP 


View： Activity 和 Layout XML 文件；

Model： 负责管理业务数据逻辑，如网络请求、数据库处理；

Presenter： 负责处理表现逻辑。

MVP 通过引入 Presenter 彻底切断 View 与 Model 的直接联系，实现“被动视图”理念。



- 彻底解耦：View 与 Model 零依赖，Presenter 成为唯一中介。
- 高可测试性：Presenter 可在 JVM 上通过 Mockito Mock View 和 Model 进行完整单元测试。
- 职责清晰：View 只负责“显示什么”，Presenter 决定“显示什么”。

缺点：每个页面都要绑定一个contract接口


MVP 是“为了解耦而解耦”的典型


## MVVM

不再依赖“接口回调”，而是通过 可观察数据 + 生命周期感知 实现自动同步。


ViewModel：继承 androidx.lifecycle.ViewModel，持有 LiveData / StateFlow 封装的 UI 状态。

View：Activity / Fragment 通过 observe() 监听状态变化，自动更新 UI。

Model：通常由 Repository 统一管理多数据源（网络 + 本地缓存）。