~~# 安卓学习笔记

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

#### 一些用法
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

```java

//显式调用
binding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

//隐式调用
通过在<activity>标签下配置<intent-filter>的内容，可以指定当前Activity 能够响应的 action和category

        <intent-filter>
                <action android:name="com.example.myapplication.ACTION_START" />
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
```

小坑：Android 12+ 要求「有 intent-filter 的 Activity 必须显式设 exported="true"」，否则系统找不到可处理该 Intent 的 Activity。

隐式 Intent 的自动匹配本质就是「用 Action、Category、Data 这三类核心条件层层过滤」，最终找到能处理这个 Intent 的组件。


exported=false	系统会剔除该组件（隐式 Intent 匹配不到）	仅可被应用内部显式 Intent 唤起

添加自定义category时必须要带默认的

其他的用法：
```java
Intent intent=new Intent(Intent.ACTION_VIEW);
intent.setData(Uri.parse("https://www.baidu.com"));
startActivity(intent);
//浏览网页
```


与此对应，我们还可以在<intent-filter>标签中再配置一个<data>标签，
用于更精确地指 定当前Activity 能够响应的数据。<data>标签中主要可以配置以下内容。
- android:scheme。用于指定数据的协议部分，如上例中的https 部分。 
- android:host。用于指定数据的主机名部分，如上例中的www.baidu.com 部分。 
- android:port。用于指定数据的端口部分，一般紧随在主机名之后。 
- android:path。用于指定主机名和端口之后的部分，如一段网址中跟在域名之后的内 容。 
- android:mimeType。用于指定可以处理的数据类型，允许使用通配符的方式进行指定。

只有当<data>标签中指定的内容和Intent 中携带的Data 完全一致 时，当前Activity 才能够响应 该Intent 

除了https 协议外，我们还可以指定很多其他协议，比如geo 表示显示地理位置、tel 表示拨打电话

> 向下一个 Activity 传递数据（启动时传，最常用）

核心：通过 Intent 携带数据，下一个 Activity 从 Intent 中获取。

intent.putExtra("key", 数据);

数据类型 变量 = intent.getXXXExtra("key", 默认值); 

> 向上一个传递数据

核心：startActivityForResult()/Activity Result API + setResult()，上一个 Activity 监听返回结果。

#### 生命周期

> 返回栈

每启动一个 新的Activity ，就会覆盖在原Activity 之上，然后点击Back 键会销毁最上面的Activity ，下面的 一个Activity 就会重新显示出来。

每当我 们按下Back 键或调用finish()方法去销毁一个Activity 时，处于栈顶的Activity 就会出栈，前一个入栈的Activity 就会重新处于栈顶的位置。系统总是会显示处于栈顶的Activity 给用户

> 四个状态

运行状态、暂停状态、停止状态、销毁状态

暂停：不在栈顶，但仍然可见的，会进入暂停状态，例如对话框。内存极低时才考虑回收

停止状态：不可见且不在栈顶的。系统仍然 会为这种Activity 保存相应的状态和成员变量，但是这并不是完全可靠的，当其他地方需要 内存时，处于停止状态的Activity 有可能会被系统回收。

销毁状态：一个Activity从返回栈中移除后就变成了销毁状态。系统最倾向于回收处于这种状态的
Activity ，以保证手机的内存充足。

> 七个回调方法

- onCreate()。这个方法你已经看到过很多次了，我们在每个Activity 中都重写了这个方 法，它会在Activity 第一次被创建的时候调用。你应该在这个方法中完成Activity 的初始化 操作，比如加载布局、绑定事件等。
- onStart()。这个方法在Activity 由不可见变为可见的时候调用。 
- onResume()。这个方法在Activity 准备好和用户进行交互的时候调用。此时的Activity 一 定位于返回栈的栈顶，并且处于运行状态。
- onPause()。这个方法在系统准备去启动或者恢复另一个Activity 的时候调用。我们通常 会在这个方法中将一些消耗CPU的资源释放掉，以及保存一些关键数据，但这个方法的执 行速度一定要快，不然会影响到新的栈顶Activity 的使用。 
- onStop()。这个方法在Activity 完全不可见的时候调用。它和onPause()方法的主要区 别在于，如果启动的新Activity 是一个对话框式的Activity ，那么onPause()方法会得到执 行，而onStop()方法并不会执行。
- onDestroy()。这个方法在Activity 被销毁之前调用，之后Activity 的状态将变为销毁状 态。
- onRestart()。这个方法在Activity 由停止状态变为运行状态之前调用，也就是Activity被重新启动了。

> 三种生存期细分
- 完整生存期。Activity 在onCreate()方法和onDestroy()方法之间所经历的就是完整生 存期。一般情况下，一个Activity 会在onCreate()方法中完成各种初始化操作，而在 onDestroy()方法中完成释放内存的操作。
- 可见生存期。Activity 在onStart()方法和onStop()方法之间所经历的就是可见生存 期。在可见生存期内，Activity 对于用户总是可见的，即便有可能无法和用户进行交互。我 们可以通过这两个方法合理地管理那些对用户可见的资源。比如在onStart()方法中对资 源进行加载，而在onStop()方法中对资源进行释放，从而保证处于停止状态的Activity 不 会占用过多内存。
- 前台生存期。Activity 在onResume()方法和onPause()方法之间所经历的就是前台生存 期。在前台生存期内，Activity 总是处于运行状态，此时的Activity 是可以和用户进行交互 的，我们平时看到和接触最多的就是这个状态下的Activity 。

![pic1](PngForMarkdown/img_3.png)


#### Activity的启动模式

四种：standard、singleTop、singleTask 和 singleInstance

> standard模式

默认模式

在standard 模式下，每当启动一个新的Activity ，
它就会在返回栈中入栈，并处于栈顶的位置。对于使用standard模式的Activity ，
系统不会在乎这个Activity 是否已经在返回栈中存在，每次启动都会创建一个该 Activity 的新实例。

也就是说 你是可以在同一个activity上不断新建相同的activity的

> singleTop模式

简单说 就是如果该Activity 已经在栈顶就不会再新建了，但是不在栈顶的话，如现在在用另外一个Activity，则启动时会新建新的


> singleTask模式



每次启动该Activity时， 系统首先会在返回栈中检查是否存在该Activity 的实例，
如果发现已经存在则直接使用该实例， 并把在这个Activity 之上的所有其他Activity 统统出栈，
如果没有发现就会创建一个新的 Activity 实例。

仅销毁「同一任务栈中」目标 Activity 之上的 Activity，跨任务栈的 Activity 不受影响。
**有风险**

> singleInstance模式

定为singleInstance 模式的Activity 会启用一个新 的返回栈来管理这个Activity (其实如果singleT ask 模式指定了不同的taskAffinity ，也会启动 一个新的返回栈)

- 独占任务栈：
启动 singleInstance 的 Activity 时，系统会为它创建一个「新的、独立的任务栈」（栈里只有它自己），和应用默认的任务栈完全分离；
- 全局唯一实例：
整个系统中，该 Activity 只有一个实例 —— 无论从哪个应用 / 哪个任务栈启动它，都会复用这个唯一实例，且自动切换到它的独立栈；
- 返回逻辑特殊：
从 singleInstance Activity 按返回键：会先回到 “启动它的那个任务栈的栈顶页面”，而非销毁自己；
例：应用默认栈（A→B）→ 启动 singleInstance 的 C → 按返回键 → 回到 B，而非销毁 C；
- 其他 Activity 无法进入它的栈：
哪怕用 Intent 跳转到 C 之后再启动 D，D 也会被放入「启动 C 的原任务栈」（而非 C 的独立栈）。


想实现其他程序和我们的程序可以共享这个Activity 的实 例，应该如何实现呢?
使用前面3 种启动模式肯定是做不到的，因为每个应用程序都会有自己的返回栈，同一个Activity 在不同的返回栈中入栈时必然创建了新的实例。而使用
singleInstance 模式就可以解决这个问题， 
在这种模式下，会有一个单独的返回栈来管理这个 Activity ，不管是哪个应用程序来访问这个Activity ，都共用同一个返回栈，也就解决了共享 Activity 实例的问题。


![pic2.png](/PngForMarkdown/img.png)
 

#### 一些实践

返回键仅仅是退出
可以写一个类，通过全局列表保存所有的Activity，然后搞一个按钮来销毁全部。

快速获取传输参数
```java
public class SecondActivity extends AppCompatActivity {
    // 1. 定义参数Key（可选，但建议统一维护，避免硬编码）
    private static final String EXTRA_PARAM1 = "param1";
    private static final String EXTRA_PARAM2 = "param2";

    // 2. 封装静态启动方法（核心）
    public static void actionStart(Context context, String data1, String data2) {
        // 构建Intent并传递参数
        Intent intent = new Intent(context, SecondActivity.class);
        intent.putExtra(EXTRA_PARAM1, data1);
        intent.putExtra(EXTRA_PARAM2, data2);
        // 启动Activity（若context非Activity，需加FLAG_ACTIVITY_NEW_TASK，避免崩溃）
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    // 3. （可选）封装参数读取方法，进一步简化内部取值逻辑
    private String getParam1() {
        return getIntent().getStringExtra(EXTRA_PARAM1);
    }

    private String getParam2() {
        return getIntent().getStringExtra(EXTRA_PARAM2);
    }

    // ... 其他生命周期/业务逻辑
}
```

就是接口化了  没啥特别


## Session 4
UI开发

#### 常用控件
> TextView

match_parent表示让当前 控件的大小和父布局的大小一样，
也就是由父布局来决定当前控件的大小。
wrap_content表示让当前控件的大小能够刚好包含住里面的内容，
也就是由控件内容决定当前控件的大小。


使用android:gravity来指定文字的对齐方式，
可选值有top、bottom、start、 end、center等，
可以用“|” 来同时指定多个值，这里我们指定的是"center"，
效果等同 于"center_vertical|center_horizontal"，
表示文字在垂直和水平方向都居中对齐。


通过android:textColor属性可以指定文字的颜色，
通过android:textSize属性可以指定 文字的大小。文字大小要使用sp 作为单位

> Button

Android 系统默认会将按钮上的英文字母全部转换成大写

如果这不是你想要的效果，可以在XML中添加 android:textAllCaps="false"这个属性，这样系统就会保留你指定的原始文字内容了。


调用button 的setOnClickListener()方法时利用了Java单抽象方法接口的特性，从而 可以使用函数式API的写法来监听按钮的点击事件
```java
  //两种方式
public void function() {
    //函数式接口
    button1.setOnClickListener(v ->
            Toast.makeText(this, "You clicked Button 1", Toast.LENGTH_SHORT).show()
    );


    //实现函数
       button1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            Intent intent = new Intent("com.example.myapplication.ACTION_START");
            intent.addCategory("com.example.myapplication.MY_CATEGORY");
            startActivity(intent);
        }
    });
}
```

> EditText

文本框

android:hint属性指定了一段提示性的文本

android:maxLines属性来限定行数

> ImageView

展示图片用的

android:src 放引用的照片

binding.imageview.setImageResource(R.drawable.ic_launcher_background); 在定义好的图片控件换图片

> ProgressBar 进度条

进度条控件

Android 控件的可见属性。所有的Android 控件都具有这个属性，可以通过 android:visibility进行指定，

可选值有3 种:visible、invisible和gone。

visible 表示控件是可见的，这个值是默认值，不指定android:visibility时，控件都是可见的。 

invisible表示控件不可见，但是它仍然占据着原来的位置和大小，可以理解成控件变成透明 状态了。

gone则表示控件不仅不可见，而且不再占用任何屏幕空间。我们可以通过代码来设置
控件的可见性，使用的是setVisibility()方法，允许传入View.VISIBLE、 View.INVISIBLE和View.GONE这3 种值。

```java
    public void fun() {
        binding2.testProgressBar.setOnClickListener(
                v ->
                {
                    int visibility = binding2.progressBar.getVisibility();
                    if (visibility == View.VISIBLE) {
                        binding2.progressBar.setVisibility(View.GONE);
                    } else {
                        binding2.progressBar.setVisibility(View.VISIBLE);
                    }
                }
        );
    }
```

指定成水平进度条后，我们还可以通过android:max属性给进度条设置一个最大值

控制水平进度条的参数是progress

> AlertDialog

AlertDialog 可以在当前界面弹出一个对话框，这个对话框是置顶于所有界面元素之上的，能够 屏蔽其他控件的交互能力，因此AlertDialog 一般用于提示一些非常重要的内容或者警告信息。


```java
public void example() {
    new AlertDialog.Builder(this)
            .setTitle("This is Dialog") // 设置标题
            .setMessage("Something important.") // 设置提示内容
            .setCancelable(false) // 设置对话框不可取消（点击外部/返回键都关不掉）
// 设置确定按钮（OK）
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 点击OK后的逻辑（可自行补充）
                    dialog.dismiss(); // 手动关闭对话框（可选，点击按钮默认会关闭）
                }
            })
            // 设置取消按钮（Cancel）
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 点击Cancel后的逻辑（可自行补充）
                    dialog.dismiss(); // 手动关闭对话框
                }
            })
            .show(); // 显示对话框
}

```

调用setPositiveButton()方法为对话框设置确定按钮的点击事件，
调用 setNegativeButton()方法设置取消按钮的点击事件



#### 三种基本布局

布局是一种可用于放置很多控件的容器，它可以按照一定的规律调整内部控件的位置，从而编写出精美的界面

> LinearLayout 线性布局

这个布局会将它所包含的控件在线性方向上依次排列

通过**android:orientation**属性指定了排列方向是vertical ，如果指定的 是horizontal ，控件就会在水平方向上排列了

内部的控件就绝对不能将宽度指定 为match_parent，否则，单独一个控件就会将整个水平方向占满，其他的控件就没有可放置 的位置了。同样的道理，如果LinearLayout 的排列方向是vertical，内部的控件就不能将高 度指定为match_parent。

原因：

**match_parent** 的含义是控件的尺寸（宽 / 高）完全等于父容器的可用尺寸

**wrap_parent** 是 根据内容自适应

如果垂直排列且height是match-parent 那就会占满页面

**android:gravity** 用于指定文字在控件中的对齐方式，
而android:layout_gravity用于指定控件在布局中的对齐方式 **（针对副轴）**


当LinearLayout 的排列方向是horizontal时，只有垂直方向上的对齐方式才会生效。因为此时水平方向上的长度是不固定的，
每添加一个控件，水平方向上的长度都会改变，因而无法指定该方向上的对齐方式。

同样的道理，当LinearLayout 的排列方向是vertical时，只有水平方向上的对齐方式才会生效

**android:layout_weight**：这个属性允许我们使用比例的方式来指定控件的大小，它在手机屏幕的适配性方面可以起到非常重要的作用

使用了android:layout_weight属性，此时控件的宽度就不应该再由android:layout_width来决定了，这里指定成0dp是一种比较规范的写法

> RelativeLayout相对布局

可以通过相对定位的方式让控件出现在布局的任何位置。
```xml
    android:layout_alignParentRight="true"
    android:layout_alignParentTop="true" 

```
可以让控件放左上角右上角 中间

还可以相对于另外一个控件的指定位置放

```xml
    android:layout_above="@+id/button3"
    android:layout_toLeftOf="@+id/button3"
//相对于button3的左上角来放


```
android:layout_alignLeft 表示让一个控件的左边缘和另一个控件的左边缘对齐，
android:layout_alignRight表示 让一个控件的右边缘和另一个控件的右边缘对齐。
此外，还有android:layout_alignTop和 android:layout_alignBottom




> FrameLayout帧布局

由于定位方式的欠缺，FrameLayout 的应用场景相对偏少一些，不过在下一章中介 绍Fragment 的时候我们还是可以用到它的。


> ConstraintLayout约束布局

一些定位的方法：

水平居中：Start_toStartOf="parent" + End_toEndOf="parent" + width=wrap_content；
垂直居中：Top_toTopOf="parent" + Bottom_toBottomOf="parent" + height=wrap_content；


1. 约束属性的命名规则（一眼看懂）
   所有约束属性都遵循「app:layout_constraint[当前控件的边]_[绑定规则]_to[目标控件的边]」：
 -  Start = 左（适配 RTL 布局，比 Left 更通用）
  - End = 右（适配 RTL 布局，比 Right 更通用）
  - Top = 上
  - Bottom = 下
  - Center = 中心（水平 / 垂直）
 -  parent = 父布局（ConstraintLayout 本身）
 -  目标控件 = 可以是 parent，也可以是其他控件的 id（比如 @id/pb_water）

举个例子：
  - app:layout_constraintStart_toStartOf="parent" → 「当前控件的左侧」绑定到「父布局的左侧」
  - app:layout_constraintEnd_toEndOf="parent" → 「当前控件的右侧」绑定到「父布局的右侧」
  - app:layout_constraintTop_toBottomOf="@id/pb_water" → 「当前控件的顶部」绑定到「进度条 pb_water 的底部」

#### 自定义控件

> 布局

相当于弄了一个容器装控价，然后另外一个布局里面引用这个布局

在喝水助手的自定义喝水进度圆环就实现了自定义的view，自行设计触发逻辑


## Session 5  Fragment


表示应用界面中可重复使用的一部分。fragment 定义和管理自己的布局。fragment 不能独立存在。它们必须由 activity 或其他 fragment 托管。fragment 的视图层次结构会成为宿主的视图层次结构的一部分，或附加到宿主的视图层次结构。

常用于平板中，让平板获得更多页面的展示

同一个 Fragment 可以在不同的 Activity 中使用，降低代码冗余。

> 不同fragment之间可以传递消息吗？

可以的，但不推荐 Fragment 之间直接通信（会导致耦合度太高），Android 官方推荐的最佳实践是通过它们所属的 Activity 作为中间媒介 来传递消息。


> Transaction 的作用？

Transaction 就是把一堆对 Fragment 的操作（增删改、动画、返回栈）打包在一起，一次性提交给系统执行的工具。

```java
           FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, secondFragment);
            transaction.addToBackStack(null); // Add to back stack so we can navigate back
            transaction.commit();
```
- 核心中的核心 : 这是 Fragment 切换的标准动作。
  - beginTransaction() : 开启一个事务。意思是“我要开始做一系列操作了，请把它们当成一个整体”。
  - replace(R.id.fragment_container, secondFragment) : “把容器里的旧 Fragment 拿走，换成新的 secondFragment ”。
  - addToBackStack(null) : 这行极其重要！
    - 如果不写：旧的 Fragment 会被直接销毁。你按手机返回键，会直接退出 Activity。
    - 写了这行：旧的 Fragment 只是被压到了“栈”的下面。你按手机返回键，系统会把旧的 Fragment 重新拿出来显示。这就实现了“返回上一页”的效果。
  - commit() : “操作定义完了，立即执行！”



### Session 6 Brocast

又分为标准广播和有序广播

前者是一对多   后者是按链条一对一

最大的优点是解藕得更彻底了

> 用法

建立自定义的receiver类 继承BroadcastReceiver
然后定制onReceive方法。

然后需要注册该频道

```xml
以静态注册为例
<receiver
    android:name=".MyReceiver"
    android:enabled="true"
    android:exported="false"> <!-- exported="false" 表示只接收本应用内的广播，安全关键！ -->
    <intent-filter>
        <action android:name="com.example.app.MY_CUSTOM_ACTION" />
    </intent-filter>
</receiver>

```

android：name可自定义。

发送端再调用接口发送即可



```java
Intent intent = new Intent("com.example.app.MY_CUSTOM_ACTION"); 
// Android 8.0+ 必须设置包名以显式发送给特定应用（如果是应用内广播）
intent.setPackage(getPackageName()); 
// 携带参数
intent.putExtra("status_code", 200);



```


> 有序广播使用


```xml

        <!-- 注册有序广播接收者 -->
        <!-- android:priority 决定优先级，数字越大优先级越高（最大1000） -->
        <receiver android:name=".HighPriorityReceiver"
            android:exported="true">
            <intent-filter android:priority="100">
                <action android:name="com.example.myapplication.ORDERED_BROADCAST"/>
            </intent-filter>
        </receiver>

        <receiver android:name=".LowPriorityReceiver"
            android:exported="true">
            <intent-filter android:priority="0">
                <action android:name="com.example.myapplication.ORDERED_BROADCAST"/>
            </intent-filter>
        </receiver>

```

主要用法是注册的时候加上priority属性

高优先级的广播如果调用abortBroadcast()，则后面低优先级的广播接收着都不会接收到广播


sendOrderedBroadcast()方法接收两个参数：第一个
参数仍然是Intent；第二个参数是一个与权限相关的字符串，




## Session 7    安卓存储

#### 文件存储

最原始的存储方式

Android 的原始文件存储其实就是 Java 的文件 I/O，只不过 Android 帮你封装好了路径，让你不用去操心文件到底存在手机的哪个角落。

> 特点

这些文件默认都保存在： /data/data/<你的包名>/files/ 目录下。可以使用java的api来保存，只是没必要罢了

> 主要接口 

Context类中提供了一个openFileOutput()方法，可以用于将数据存储到指定的文件中。这
个方法接收两个参数：

- 第一个参数是文件名，在文件创建的时候使用，注意这里指定的文件名
不可以包含路径，因为所有的文件都默认存储到/data/data/<pack age name>/files/ 目录
下；
- 第二个参数是文件的操作模式，主要有MODE_PRIVATE和MODE_APPEND两种模式可选，默
认是MODE_PRIVATE，表示当指定相同文件名的时候，所写入的内容将会覆盖原文件中的内
容，而MODE_APPEND则表示如果该文件已存在，就往文件里面追加内容，不存在就创建新文
件。

> 用法展示

```java
    private void save(String inputText) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            // MODE_PRIVATE: The default mode, where the created file can only be accessed by the calling application
//            out = openFileOutput("data.txt", Context.MODE_PRIVATE);
            out=openFileOutput("data.txt", Context.MODE_APPEND);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
            tvStatus.setText("Saved to: " + getFilesDir() + "/data.txt");
            Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
```
> 接口的问题

```java
    @Override
    public FileOutputStream openFileOutput(String name, int mode)
            throws FileNotFoundException {
        return mBase.openFileOutput(name, mode);
    }
    

```
当前 mBase 的真实身份是： android.app.ContextImpl

一个隐藏类  所以找不到
```java

    @Override
    public FileOutputStream openFileOutput(String name, int mode) throws FileNotFoundException {
        checkMode(mode);
        final boolean append = (mode&MODE_APPEND) != 0;
        File f = makeFilename(getFilesDir(), name);
        try {
            FileOutputStream fos = new FileOutputStream(f, append);
            setFilePermissionsFromMode(f.getPath(), mode, 0);
            return fos;
        } catch (FileNotFoundException e) {
        }

        File parent = f.getParentFile();
        parent.mkdir();
        FileUtils.setPermissions(
            parent.getPath(),
            FileUtils.S_IRWXU|FileUtils.S_IRWXG|FileUtils.S_IXOTH,
            -1, -1);
        FileOutputStream fos = new FileOutputStream(f, append);
        setFilePermissionsFromMode(f.getPath(), mode, 0);
        return fos;
    }
```


#### SharedPreferences

在喝水助手里面用过了

用于存储简单的键值对数据，比如用户的登录状态、应用的配置信息等。

> 用法和接口

 ```java

 // 1. 拿对象
SharedPreferences sp = getSharedPreferences("UserConfig", Context.MODE_PRIVATE);

// 2. 读
String name = sp.getString("username", "Guest"); // 第一个参数是key，第二个参数是默认值

// 3. 写 (三步走：edit -> put -> apply)
sp.edit().putString("username", "Admin").putInt("level", 99).apply(); // 别忘了这最后一下！
```





> 为什么写入要用editor  但读取直接读preferences？

就是读写分离
sharedpreferences 的写入需要用到io，把数据放到文件里面。 多次写入会导致io性能问题
为了性能优化，则使用editor来写入，而使用preferences来读取（直接从内存里面读）

editor类写入为异步批量写入
```java
SharedPreferences.Editor editor = sp.edit(); // 1. 开启一个“草稿本”
editor.putString("name", "Jack"); // 2. 在内存草稿里改
editor.putInt("age", 18);         // 3. 在内存草稿里改
editor.putBoolean("vip", true);   // 4. 在内存草稿里改
editor.apply();                   // 5. 【一次性】把草稿本的内容合并，并写入磁盘

````


#### SQLite 数据库

安卓内置的数据库

> 一些用法和接口

*助手类：SQLiteOpenHelper*
这是你的数据库管家，必须继承它并重写两个方法。

- onCreate(SQLiteDatabase db) :
  - 时机 ：数据库第一次被创建时调用。
  - 作用 ：在这里执行 create table 语句。
- onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) :
  - 时机 ：数据库版本号增加时调用。
  - 作用 ：在这里执行 drop table 或 alter table 升级表结构。


*核心对象*
- SQLiteDatabase :
  - 作用 ：数据库的操作入口（执行增删改查）。
  - 获取 ： dbHelper.getWritableDatabase() (读写) 或 getReadableDatabase() (只读)。
- Cursor :
  - 作用 ：查询结果的游标（就像 Excel 的当前选中行）。
  - 注意 ：用完必须调用 close() ，否则内存泄漏。


可以直接执行语句
```java
db.execSQL("INSERT INTO Book (name, price) VALUES (?, ?)", new String[]{"Android开发", "99"});
```


> CRUD方法

增：
```java
ContentValues values = new ContentValues();
values.put("name", "Android开发");
values.put("price", 99);
db.insert("Book", null, values);
//n第二个参数 null没啥用的
相当于 INSERT INTO Book (name, price) VALUES ('Android开发', 99);
```
删:
```java
db.delete("Book", "name=?", new String[]{"Android开发"});
 相当于 DELETE FROM Book WHERE name='Android开发';

SQL: DELETE FROM Book WHERE name LIKE '%Java%'
db.delete("Book", "name LIKE ?", new String[]{"%Java%"});

SQL: DELETE FROM Book WHERE id = 1
db.delete("Book", "id = 1", null);

SQL: DELETE FROM Book WHERE price > 50 AND pages < 100
db.delete("Book", "price > ? AND pages < ?", new String[]{"50", "100"});
```

改：
```java
ContentValues values = new ContentValues();
values.put("price", 88);
db.update("Book", values, "name=?", new String[]{"Android开发"});
```

查：
```java
Cursor cursor = db.query("Book", null, null, null, null, null, null);
while (cursor.moveToNext()) {
    String name = cursor.getString(cursor.getColumnIndex("name"));
    int price = cursor.getInt(cursor.getColumnIndex("price"));
    Log.d("Book", "name: " + name + ", price: " + price);
}
cursor.close();
```
用游标是因为安卓资源受限，没办法直接像mybatis那样返回一整个列表
```java
cursor = db.query(
    "Book",                           // 1. table
    new String[] { "author", "AVG(price) as avg_price" }, // 2. columns: 查作者和平均价格
    "pages > ?",                      // 3. selection: 只要页数 > 100 的书
    new String[] { "100" },           // 4. selectionArgs
    "author",                         // 5. groupBy: 按作者分组算平均值
    "avg_price > 20",                 // 6. having: 只要均价 > 20 的组
    "avg_price DESC"                  // 7. orderBy: 贵的排前面
);   // 7. orderBy: 排序

SELECT author, AVG(price) as avg_price 
FROM Book 
WHERE pages > 100 
GROUP BY author 
HAVING avg_price > 20 
ORDER BY avg_price DESC

```


>  一些注意点

sqlite的数据库存储路径都是默认的，可以改但没必要

当你使用 SQLiteOpenHelper 或 Context.openOrCreateDatabase("BookStore.db", ...) 时，如果不指定绝对路径，数据库文件默认会生成在：

/data/data/<你的包名>/databases/BookStore.db

在你的这个项目里，具体的路径就是： /data/data/com.example.myapplication/databases/BookStore.db

<br>

getWritableDatabase() ：返回一个可读写的数据库对象。

getReadableDatabase() ：返回一个只读的数据库对象。

异同：
 
  - 相同点：如果数据库不存在，会创建它；如果存在，会打开它。
  - 不同点：如果数据库文件大小超过了系统限制，getReadableDatabase() 会返回一个只读数据库，而不会抛出异常。而 getWritableDatabase() 会直接抛出异常。


> onUpgrade方法
当用户更新了 App，代码里版本号变了，但用户手机里的数据库文件还是旧版本（version 1），这时候系统检测到差异，就会触发 onUpgrade 。

```java
@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // 假设是从版本 1 升到 2
    if (oldVersion <= 1) {
        // 给 Book 表加一列 'category_id'
        db.execSQL("ALTER TABLE Book ADD COLUMN category_id INTEGER");
    }
    
    // 假设是从版本 2 升到 3
    if (oldVersion <= 2) {
        // 又加了一列 'publish_date'
        db.execSQL("ALTER TABLE Book ADD COLUMN publish_date TEXT");
    }
}
```

> 事务
```java
db.beginTransaction(); // 1. 开启事务
try {
    // ... 做一堆操作 (删除、插入、更新...)
    db.delete(...);
    db.insert(...);
    
    db.setTransactionSuccessful(); // 2. 标记成功 (如果没跑这一行就结束，就是回滚)
} catch (Exception e) {
    // 捕获异常，什么都不用做，或者打个日志
} finally {
    db.endTransaction(); // 3. 结束事务 (提交或回滚)
}
```

和jdbc区别是 没有rollback和submit



## Session 8  Contenct Provider

先学service




## Session 9  多媒体




## Session 10 Service

Service 是Android 中实现程序后台运行的解决方案，它非常适合执行那些不需要和用户交互而
且还要求长期运行的任务。Service 的运行不依赖于任何用户界面，即使程序被切换到后台，或
者用户打开了另外一个应用程序，Service 仍然能够保持正常运行。


## Session 11 网络