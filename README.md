
# AndroidKit

[![](https://jitpack.io/v/ldh-star/AndroidKit.svg)](https://jitpack.io/#ldh-star/AndroidKit) ![](https://img.shields.io/badge/author-ldh-orange.svg) ![](https://img.shields.io/hexpm/l/plug.svg)

## 说明

自用安卓工具库，这里面的工具函数都是实际项目中使用过的，就是整合一下，其中有些代码是很久以前写的，比较烂也懒得重构（人和代码有一个能跑就行）。

大部分是kotlin代码，很多函数也是kotlin的插件函数、插件属性。具体有哪些API见源码。

## Start

#### Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

```gradlex
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

#### Step 2. Add the dependency

```gradle
dependencies {
	implementation 'com.github.ldh-star:AndroidKit:$last_version'
}
```

## Licenses

```
 Copyright 2022 original author or authors.
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