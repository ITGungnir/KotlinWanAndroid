# <center>KotlinWanAndroid</center>
<div align="center">
  <img src="./ic_launcher_origin.png"/>
  <br/><br/>
  <img src="https://img.shields.io/badge/Version-V1.0.0-brightgreen.svg" />
  <img src="https://img.shields.io/badge/build-passing-brightgreen.svg" />
  <img src="https://img.shields.io/badge/API-19+-blue.svg" alt="Min Sdk Version" />
  <img src="https://img.shields.io/badge/License-Apache2.0-blue.svg" alt="License" />
  <img src="https://img.shields.io/badge/Email-itgungnir@163.com-ff69b4.svg" />
</div>

## 项目简介
KotlinWanAndroid是以[WanAndroid API](https://www.wanandroid.com/)为基础开发的一款阅读类APP，其主要功能包括：
* **首页**，展示广告栏、置顶文章和最新文章列表；
* **搜索**，提供热门搜索关键词列表和历史搜索关键词列表，可搜索文章；
* **知识体系**，按不同分类展示知识体系，点击可查看分类下的文章；
* **常用网站**，展示常用网站；
* **导航**，按不同工具网站的分类排序，展示各种工具网站的入口；
* **公众号**，按公众号名称展示业内知名大神的公众号最新推送文章；
* **项目**，按项目类型展示网站收录的项目；
* **文章详情**，文章详情页可以关注文章、取关文章、分享文章；
* **登录注册**，登录注册功能对用户身份进行验证，登录后可查看个人收藏列表和日程列表；
* **我的**，我的页面中展示个人收藏列表，可添加站外文章，并提供设置页面和日程页面的入口；
* **设置**，设置页面可以切换自动缓存、无图模式和夜间模式、清除缓存、意见反馈、关于我们、退出登录；
* **日程**，日程页面展示个人所有待办日程，可以添加、修改、删除日程以及查看已完成日程。

## 应用下载
扫码下载应用(Android 4.4+)。如果在使用中发现任何问题或Bug，欢迎issue，或email(itgungnir@163.com)

![KotlinWanAndroid Apk Download](./images/download_code.png)

## 功能截屏
|Image Column 01|Image Column 02|Image Column 03|
|---|---|---|
|<img src="./images/screen_shot_01.png" width="250"/>|<img src="./images/screen_shot_02.png" width="250"/>|<img src="./images/screen_shot_03.png" width="250"/>|
|<img src="./images/screen_shot_04.png" width="250"/>|<img src="./images/screen_shot_05.png" width="250"/>|<img src="./images/screen_shot_06.png" width="250"/>|
|<img src="./images/screen_shot_07.png" width="250"/>|<img src="./images/screen_shot_08.png" width="250"/>|<img src="./images/screen_shot_09.png" width="250"/>|
|<img src="./images/screen_shot_10.png" width="250"/>|<img src="./images/screen_shot_11.png" width="250"/>|<img src="./images/screen_shot_12.png" width="250"/>|
|<img src="./images/screen_shot_13.png" width="250"/>|<img src="./images/screen_shot_14.png" width="250"/>|<img src="./images/screen_shot_15.png" width="250"/>|
|<img src="./images/screen_shot_16.png" width="250"/>|<img src="./images/screen_shot_17.png" width="250"/>|<img src="./images/screen_shot_18.png" width="250"/>|
|<img src="./images/screen_shot_19.png" width="250"/>|<img src="./images/screen_shot_20.png" width="250"/>|<img src="./images/screen_shot_21.png" width="250"/>|

## 项目架构
本项目共分为四个模块：`app`、`app_main`、`app_support`、`common`，其模块依赖关系如下图所示。

![project structure](./images/project_structure.png)

* `app`模块是项目的壳模块，其中仅存放了`App`和项目的入口页面`SplashActivity`；
* `app_main`模块中包含的是`MainActivity`及其中的五个`Fragment`，和点击弹出的各种`Dialog`；
* `app_support`模块是除上述两个模块包含的页面之外的页面；
* `common`模块是项目的通用模块，其中包含了`工具类`、`HTTP相关类`、`Redux的本地实现`等。

具体的技术会在`技术栈`章节中详细介绍。

## 技术栈
### 1、[RxMVVM框架](https://github.com/ITGungnir/RxMVVM)

## License
```text
Copyright 2019 ITGungnir

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```