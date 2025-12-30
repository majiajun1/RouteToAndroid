# Kotlin 学习杂记

> 伴生对象
```kotlin
 // 伴生对象：属于 User 类本身，所有实例共享
    companion object {
        // 静态常量
        const val DEFAULT_USERNAME = "guest"
        // 静态变量
        var onlineCount = 0
        // 静态方法
        fun createGuestUser(): User {
            val guest = User()
            guest.username = DEFAULT_USERNAME
            onlineCount++ // 共享变量
            return guest
        }
    }
```
代替static的更灵活方案

所有实例共享，静态成员

伴生对象 可作为接口实现类来使用

> val和var

val：只读属性，不能被修改

var：可读可写属性

kotlin自动推断变量类型，不需要自己定义（当然可以自己定义，只是麻烦点)

```kotlin
var name:String = "mjj"

var name = "mjj"  这两句等价的
```
自动推断对性能的影响微乎其微，主要是编译器的工作

> 接口的一些语法？构造函数？

```kotlin
// Kotlin 接口
interface KotlinInterface {
    // 抽象属性（必须由实现类重写）
    val abstractProp: String
    
    // 带默认实现的属性（无需重写，可按需覆盖）
    val defaultProp: String
        get() = "Kotlin默认属性值"

    // 抽象方法
    fun abstractMethod()

    // 默认实现的方法（无需关键字）
    fun defaultMethod() {
        println("Kotlin默认方法，属性值：$defaultProp")
    }
}

// 实现接口（重写抽象属性和方法）
class MyClass : KotlinInterface {
    override val abstractProp: String = "重写的抽象属性"
    
    override fun abstractMethod() {
        println("重写抽象方法，属性值：$abstractProp")
    }
}


// 普通类（默认final，不可继承）
class KotlinClass {
    // 方法（默认final，不可重写）
    fun method() {
        println("Kotlin方法")
    }
    
    // 内部类（默认静态，无外部引用）
    class StaticInnerClass {
        fun innerMethod() {
            println("Kotlin静态内部类")
        }
    }
    
    // 用inner关键字才持有外部类引用
    inner class InnerClass {
        fun innerMethod() {
            println("Kotlin内部类，外部类方法：${this@KotlinClass.method()}")
        }
    }
}

// 可继承的类（加open）
open class OpenClass {
    // 可重写的方法（加open）
    open fun openMethod() {
        println("可重写的方法")
    }
}

// 继承类（加override）
class SubClass : OpenClass() {
    override fun openMethod() {
        super.openMethod()
        println("重写后的方法")
    }
}

// 数据类（自动生成equals/hashCode/toString等）
data class User(val name: String, val age: Int)



// 主构造函数（类头），参数用val/var直接声明为成员变量
class KotlinConstructor(
    val name: String, 
    var age: Int
) {
    // 初始化代码块（主构造函数的逻辑）
    init {
        println("Kotlin初始化：$name,$age")
    }

    // 次构造函数（必须调用主构造函数）
    constructor() : this("默认名称", 0) {
        println("次构造函数执行")
    }

    // 带参数的次构造函数
    constructor(name: String) : this(name, 0)
}

// 调用示例
fun main() {
    // 主构造函数
    val obj1 = KotlinConstructor("张三", 20)
    // 无参次构造函数
    val obj2 = KotlinConstructor()
    // 单参数次构造函数
    val obj3 = KotlinConstructor("李四")
}



```
这是 Kotlin 和 Java 构造函数差异最大的地方，Kotlin 把构造函数分为 “主构造函数” 和 “次构造函数”，语法更紧凑。
