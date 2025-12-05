# 安卓学习笔记

## Session One

> 安卓四大组件
> 
Activity、Service、Broadcast Receiver、Content Provider


> 项目结构

app/src/main：

/java 编写代码主要的地方

/res 资源存放地   图片放在drawable 目录下，布局放在layout 目录下，字符串放在values 目录下


**bulid.gradle**
这是app模块的gradle构建脚本



> AndroidManifest.xml

整个Android 项目的配置文件，你在程序中定义的所有四大组件都需要在这个文件里注 册
```xml
<activity android:name=".MainActivity"> <intent-filter>
    <action android:name="android.intent.action.MAIN" />
    <category android:name="android.intent.category.LAUNCHER" /> </intent-filter>
</activity>
```
表示MainActivity 是这个 项目的主Activity ，在手机上点击应用图标，首先启动的就是这个Activity 。

内容被遮挡），通过 lambda 参数传递给内部 UI。


> res文件夹详解

x x x-hdpi 、x x x-xhdpi 、x x x-xxhdpi
这些是属于图标的不同分辨率，用于兼容各类设备

**drawable-xxhdpi** 是主流

> strings.xml

```xml
<resources>
<string name="app_name">HelloWorld</string>
</resources>
```
有以下两种方式来引用app_name：

1. 在代码中通过R.string.app_name可以获得该字符串的引用。
2. 在XML中通过@string/app_name可以获得该字符串的引用

其中string部分是可以替换的，如果是引用的图片资源就可 以替换成drawable，
如果是引用的应用图标就可以替换成mipmap，
如果是引用的布局文件就 可以替换成layout，以此类推。

> build.gradle文件

**最外层的build.gradle**

统一管理所有子模块（比如 app 模块）能用的插件，且只「声明插件存在」，但不直接启用。

gradle/libs.versions.toml负责定义版本（新写法）

apply false：「不立即启用插件」—— 只告诉 gradle「这个插件我项目里有」，
但不在这个顶层文件里启用；
哪个子模块（比如 app 模块）需要用，
就在子模块的build.gradle里写apply true（或直接声明插件）。



**子模块（app文件夹）的gradle文件**

```groovy
    buildTypes {
        release {
        minifyEnabled false
        proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
```
**minifyEnabled**   指代码混淆，防止代码被反编译破解


proguard-android.txt vs proguard-android-optimize.txt？

proguard-android.txt：仅混淆，不做代码优化（更保守）；
proguard-android-optimize.txt：混淆 + 代码优化（比如删除未使用的代码、简化逻辑），体积更小，是新版默认推荐。

> log日志

vdiew  五种级别

logcat看日志即可  

过滤器 新版本更智能了 直接写条件就好了