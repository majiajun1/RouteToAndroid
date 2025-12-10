# 安卓学习笔记

## Session 1

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


## Session 3

**跳过kotlin**

四大组件

### Activity

layout放布局页面文件

在XML中引用一个id，就使用@id/id_name这种语法，而如果你需要在XML中定义一 个id，则要使用@+id/id_name这种语法  


setContentView()方法来给当前的Activity 
加载一个布局，而在 setContentView()方法中，我们一般会传入一个布局文件的id


所有的Activity都要在AndroidManifest.xml 中进行注册才能生效


配置主Activity 的方法就是在<activity>标签的内部加入<intent-filter> 标签，
并在这个标签里添加<action android:name="android.intent.action.MAIN"/>和<category android:name="android.intent.category.LAUNCHER" />这两句声明即可。

> 总体流程

androidmanifest -> 注册一个activity -> setcontentview加载一个布局文件

> 使用toast

什么是toast？
Toast 是Android 系统提供的一种非常好的提醒方式，
在程序中可以使用它将一些短小的信息通知给用户，
这些信息会在一段时间后自动消失。

```java
//view类
    public void setOnClickListener(@Nullable OnClickListener l) {
        throw new RuntimeException("Stub!");
    }
    public interface OnClickListener { //内部接口
        void onClick(View var1);
    }

   //用法lamdba表达式
public void example()
{
        botton1.setOnClickListener(v->
        Toast.makeText(this,"You clicked Button 1", Toast.LENGTH_SHORT).show()//
        );
}


public static Toast makeText(Context context, @StringRes int resId, @Duration int duration)
        throws Resources.NotFoundException {
    return makeText(context, context.getResources().getText(resId), duration);
}

public static Toast makeText(Context context, CharSequence text, @Duration int duration) {
    return makeText(context, null, text, duration);
}

/**
 * Make a standard toast to display using the specified looper.
 * If looper is null, Looper.myLooper() is used.
 *
 * @hide
 */
public static Toast makeText(@NonNull Context context, @Nullable Looper looper,
                             @NonNull CharSequence text, @Duration int duration) {
    Toast result = new Toast(context, looper);

    if (Compatibility.isChangeEnabled(CHANGE_TEXT_TOASTS_IN_THE_SYSTEM)) {
        result.mText = text;
    } else {
        result.mNextView = ToastPresenter.getTextToastView(context, text);
    }

    result.mDuration = duration;
    return result;
}
```

button类继承textview类 textview类继承view类

通过调用setOnClickListener()方法为按钮注册一个监听 器，点击按钮时就会执行监听器中的onClick()方法

Toast类的静态方法makeText() 

makeText()方法需要传入3个参数。
第一个 参数是Context，也就是Toast要求的上下文，
由于Activity本身就是一个Context对象，（继承）
因此这里直接传入this即可。
第二个参数是Toast 显示的文本内容。
第三个参数是Toast 显示的时长，有两个内置常量可以选择:Toast.LENGTH_SHORT和Toast.LENGTH_LONG。


题外话：使用viewbinding可以替代繁琐的多刺findviewbyid。

> 使用Menu

一种菜单功能

```java
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu); //因为继承了Activity ，所以直接复用父类方法。
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    
        int itemId = item.getItemId();
        if(itemId == R.id.add_item)
        {
            Toast.makeText(this,"You clicked Add",Toast.LENGTH_SHORT).show();
        }else if(itemId ==R.id.remove_item)
        {
            Toast.makeText(this,"You clicked Remove",Toast.LENGTH_SHORT).show();
        }
    
        return true;
    
    
    }
```

onCreateOptionsMenu 是 Activity 的生命周期方法之一，
当用户点击「菜单按钮（三点图标 / 物理菜单键）」时，
系统会回调这个方法，让你「加载并显示自定义的菜单布局」。

Menu 是 Android 系统提供的「菜单容器类」，
属于 android.view 包，核心是「管理菜单的所有选项（菜单项）」，
你可以把它理解成「菜单的 “容器 / 清单”」。


> 为什么不都写在onCreate方法？

onCreate() 在 Activity 启动时只执行一次

onCreateOptionsMenu()	用户点击「菜单按钮 / 三点图标」时（按需执行）

onOptionsItemSelected()	用户点击菜单项时（按需执行）

最主要是懒加载，其次是生命周期要区别开


> 关键对比：手动监听 vs 系统回调（为什么不用手动 setOnClickListener？）

你可能会想：“按钮可以用 setOnClickListener，为什么菜单不用？”

按钮是「你自己的 View」，需要手动绑定监听；

菜单是「系统管理的 UI」，系统已经帮你做了所有监听工作，
只需要通过回调方法接收结果即可 —— 这是 Android 对系统级 UI 的统一设计（比如 ActionBar、状态栏通知都是这个逻辑）。

debug实测，调用activity的onPreparePanel方法 才会初始化menu。

> 销毁app

就是退出功能，调用finish（） 即可

> 使用Intent在Activity之间跳转

Intent 大致可以分为两种:显式Intent 和隐式Intent

Intent 一般可用于启动Activity、启动Service 以 及发送广播等场景



