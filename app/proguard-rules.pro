# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\1WxlDir\installFolder\androidStudio\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# banner 的混淆代码
-keep class com.youth.banner.** {
    *;
 }

 #以下混淆规则是自定义的
 # 代码混淆压缩比，在0~7之间，默认为5，一般不做修改
 -optimizationpasses 5

 # 混合时不使用大小写混合，混合后的类名为小写
 -dontusemixedcaseclassnames

 # 指定不去忽略非公共库的类
 -dontskipnonpubliclibraryclasses
 # 这句话能够使我们的项目混淆后产生映射文件
 # 包含有类名->混淆后类名的映射关系
 -verbose
 # 指定不去忽略非公共库的类成员
 -dontskipnonpubliclibraryclassmembers

 # 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
 -dontpreverify

 # 保留Annotation不混淆
 -keepattributes *Annotation*,InnerClasses

 # 避免混淆泛型
 -keepattributes Signature

 # 抛出异常时保留代码行号
 -keepattributes SourceFile,LineNumberTable

 # 指定混淆是采用的算法，后面的参数是一个过滤器
 # 这个过滤器是谷歌推荐的算法，一般不做更改
 -optimizations !code/simplification/cast,!field/*,!class/merging/*

 # 保留我们使用的四大组件，自定义的Application等等这些类不被混淆
 # 因为这些子类都有可能被外部调用
 -keep public class * extends android.app.Activity
 -keep public class * extends android.app.Appliction
 -keep public class * extends android.app.Service
 -keep public class * extends android.content.BroadcastReceiver
 -keep public class * extends android.content.ContentProvider
 -keep public class * extends android.app.backup.BackupAgentHelper
 -keep public class * extends android.preference.Preference
 -keep public class * extends android.view.View
 -keep public class com.android.vending.licensing.ILicensingService

 # 保留support下的所有类及其内部类
 -keep class android.support.** {*;}

 # 保留继承的
 -keep public class * extends android.support.v4.**
 -keep public class * extends android.support.v7.**
 -keep public class * extends android.support.annotation.**

 # 保留R下面的资源
 -keep class **.R$* {*;}

 # 保留本地native方法不被混淆
 -keepclasseswithmembernames class * {
     native <methods>;
 }

 # 保留在Activity中的方法参数是view的方法，
 # 这样以来我们在layout中写的onClick就不会被影响
 -keepclassmembers class * extends android.app.Activity{
     public void *(android.view.View);
 }

 # 保留枚举类不被混淆
 -keepclassmembers enum * {
     public static **[] values();
     public static ** valueOf(java.lang.String);
 }

 # 保留我们自定义控件（继承自View）不被混淆
 -keep public class * extends android.view.View{
     *** get*();
     void set*(***);
     public <init>(android.content.Context);
     public <init>(android.content.Context, android.util.AttributeSet);
     public <init>(android.content.Context, android.util.AttributeSet, int);
 }

# 保留Parcelable序列化类不被混淆
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# 保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}

# webView处理，项目中没有使用到webView忽略即可
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
-keepclassmembers class * extends com.tencent.smtt.sdk.WebViewClient {
    public void *(com.tencent.smtt.sdk.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(com.tencent.smtt.sdk.WebView, java.lang.String);
}
-keepclassmembers class * extends com.tencent.smtt.sdk.WebViewClient {
    public void *(com.tencent.smtt.sdk.WebView, jav.lang.String);
}

#极光推送混淆开始
-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }
#极光推送混淆结束

### greenDAO 3开始
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties

# If you do not use SQLCipher:
-dontwarn org.greenrobot.greendao.database.**
# If you do not use RxJava:
-dontwarn rx.**
### greenDAO 3结束

#-keep class com.goldencarp.lingqianbao.view.custom.** { *; }