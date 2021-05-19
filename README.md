# BuildConfig [![](https://jitpack.io/v/runnchild/BuildConfig.svg)](https://jitpack.io/#runnchild/BuildConfig)
项目的gradle配置插件，包含基本的脚本配置和第三方依赖库管理

使用：
> 1. 项目根目录下build.gralde

```
buildscript {
  ...
  repositories {
    ...
    // 添加jitpack
    maven { url 'https://jitpack.io' }
  }
  
  dependencies {
    ...
    classpath "com.github.runnchild:BuildConfig:$config_version"
  }
}
```

> 2. app目录下的build.gradle

```
 plugins { 
    id 'com.rongc.app'
 }
 // or apply plugin: "com.rongc.app"
```
在根目录下的gradle.properties添加包名和版本号
```
APPLICATION_ID=com.rongc.config
VERSION_NAME=2.0.3
VERSION_CODE=203
```

> 3.library目录下的build.gradle
```
plugins { 
    id 'com.rongc.lib'
 }
 // or apply plugin: "com.rongc.lib"
```

> 3.如果有java library
```
plugins { 
    id 'com.rongc.java'
 }
 // or apply plugin: "com.rongc.java"
```

添加完插件后，就已经添加好了基本配置，其余的配置均可删除。如果要增加额外的配置，往对应的闭包里加就行了，例如添加依赖：

```
plugins { id 'com.rongc.lib' }

dependencies {
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle"
}
```
