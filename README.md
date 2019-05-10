# KotlinWanAndroid
WanAndroid client programmed with Kotlin.

## ToFinish
* 我的页面：日程按钮
* 设置页面：UI
* 设置页面：关于我们
* 设置页面：意见反馈
* 设置页面：自动缓存
* 设置页面：清除缓存
* 设置页面：无图模式
* 设置页面：夜间模式

## ToFix
* 公共的Delegate尽量抽取出来
* 为所有EasyAdapter的使用添加Differ
* 导航页面交互问题
* 模块化

## 项目模块化计划
本项目拟分为四个模块：app、app_main、app_support和common

app依赖app_main和app_support；app_main和app_support依赖common

后期将common中的widget包抽出放到远端依赖中，拟命名为UIKit