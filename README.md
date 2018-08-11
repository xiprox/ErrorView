# ErrorView

A custom view that displays an image, a title, and a subtitle. It can be used for various purposes like displaying errors, empty states, or just messages with images.

![](/graphics/screenshots/ss_01.png)

<p align="right">
<a href='https://github.com/xiprox/ErrorView/releases/latest'><img height="48" alt='Get apk' src='https://cloud.githubusercontent.com/assets/2550945/21590907/dd74e0f0-d0ff-11e6-971f-d429148fd03d.png'/></a>
</p>

## Download
```gradle
implementation 'tr.xip.errorview:library:4.0.0'
```
**Note:** You might have to add `jcenter()` to your repositories.

Pre-3.0 versions were `com.github.xiprox.errorview:library:x.y.z`.

If you're migrating from v2, you may find the changelog in the [releases](https://github.com/xiprox/ErrorView/releases) page helpful.

## Usage

#### Image
```java
setImage(int res)
setImage(Drawable)
setImage(Bitmap)

setImageTint(int color)

setImageVisible(boolean)

setImageSize(int width) // The ImageView adjusts its height to preserve aspect ratio.

.isImageVisible
```
```xml
app:ev_image="@drawable/,,,"
app:ev_imageTint="@color/..."
app:ev_imageVisible="boolean"
app:ev_imageSize="123dp"
```

#### Title
```java
setTitle(String)
setTitle(int res)
setTitleColor(int color)
setTitleVisible(boolean)
.title
.isTitleVisible
```
```xml
app:ev_title="@string/..."
app:ev_titleColor="@color/..."
app:ev_titleVisible="boolean"
```

#### Subtitle
```java
setSubtitle(String)
setSubtitle(int res)
setSubtitleColor(int color)
setSubtitleVisible(boolean visible)
.subtitle
.isSubtitleVisible
```
```xml
app:ev_subtitle="@string/..."
app:ev_subtitleColor="@color/..."
app:ev_subtitleVisible="boolean"
```

#### Retry button
```java
setRetryText(String)
setRetryText(int res)
setRetryColor(int color)
setRetryVisible(boolean)
.retryText
.isRetryVisible
```
```xml
app:ev_retryText="@string/..."
app:ev_retryBackground="@drawable/..."
app:ev_retryColor="@color/..."
app:ev_retryVisible="boolean"
```
#### Retry listener
```java
setRetryListener(RetryListener)
```

#### Builder pattern
All set methods return `ErrorView`, so you can chain them like such:
```java
errorView.setImage(image).setTitle(e.title).setSubtitle(e.message).setRetryVisible(false)
```

## Theming
You can theme ErrorView app-wide:
```xml
<style name="AppTheme" parent="Theme.AppCompat.DayNight.DarkActionBar">
    ...
    <item name="ev_style">@style/MyErrorView</item>
</style>
 
<style name="MyErrorView">
    <item name="ev_retryText">@string/error_retry</item>
    <item name="ev_image">@drawable/sadface</item>
</style>
```

You can also apply themes to specific ErrorViews:
```xml
<tr.xip.errorview.ErrorView
    android:id="@+id/specialErrorView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:theme="@style/MySpecialErrorView"/>
```
```xml
<style name="MySpecialErrorView">
    <item name="ev_imageSize">120dp</item>
    <item name="ev_retryColor">@color/apptheme_accent>>
</style>
```

And yes, ErrorView supports `AppCompat.DayNight` out-of-the-box. In fact the above style snippet is from the sample app. 

## Further customization
If you are looking for further customization, you can always do something like the following, albeit a bit hacky:
```java
val image = errorView.findViewById(R.id.ev_image)
val title = errorView.findViewById(R.id.ev_title)
val subtitle = errorView.findViewById(R.id.ev_subtitle)
val retryButton = errorView.findViewById(R.id.ev_retry)
```
These view ids will not be changed unless there is a major version increment (e.g. 4.0.0 -> 5.0.0).

## License
```
Copyright (C) 2014 İhsan Işık

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
Apache License Version 2.0 ([LICENSE](/LICENSE))
