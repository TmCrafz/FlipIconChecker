# FlipIconChecker
This library let you define a front and back layout, which get added to the FlipIconChecker. The view flips with a animation to the other side when it is checked and a custom view scale in. This library makes it easy to make flipping views like Google in the Holo and Material Gmail app or the material sms app. Compatible with api level 15+.

## Preview

![alt tag](/images/example_round.gif "Round Example") ![alt tag](/images/example_rect.gif "Rect Example")

## Usage

##### Add this to your build.grandle:
```
compile 'org.tmcrafz.flipiconchecker:flipiconchecker:0.8.3'
```

By default, when you dont specify an extra atrribute, the front layout contains a red colored shape and a gray back, with a white check icon "scaling" in:

 Front: | Back: 
 -------| ----- 
 ![alt tag](/images/deafult_front.png "Round Example") | ![alt tag](/images/deafult_back.png "Round Example")
```xml
<org.tmcrafz.flipiconchecker.FlipIconChecker
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />

```

You can specify your own custom layouts for the front, back and the layout which "scale" in when the view get checked.
When you use custom layouts it is best you set "layout_width" and "layout_height" of the FlipIconChecker to "wrap_content".
You get the best results when all custom layouts have the same size. The checkView layout, which you specify get scaled from 0% to 80% of his full size.
With the "duration" attribute you can set a custom duration for the animation from one side to the other side. By default one flip takes 200ms:

```xml
<org.tmcrafz.flipiconchecker.FlipIconChecker
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:frontView="@layout/custom_front_layout"
        app:backView="@layout/custom_back_layout"
        app:checkView="@layout/custom_check_layout"
        app:duration="250" />
```

Instead of custom layouts you can specify custom drawables. If you have specified a custom drawable and a custom layout for the same purpose (e.g. front) the layout is ignored. So only specify a custom drawable or a custom layout for the same purpose:

```xml
<org.tmcrafz.flipiconchecker.FlipIconChecker
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:frontSrc="@drawable/red_shape"
        app:backSrc="@drawable/blue_shape"
        app:checkSrc="@drawable/ic_camera" />
```

Get the state of the FlipIconChecker:
```java
flipIconChecker.isChecked();
```

You can add a click listener to the flipIconChecker:

```java
flipIconChecker.setOnFlipIconCheckerClickedListener(
                new FlipIconChecker.OnFlipIconCheckerClickedListener() {
      @Override
      public void onFlipIconCheckerClicked() {
                // Do something
      }
});
```

## License

    FlipIconChecker	
    Copyright 2016 Tim Sterzel

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.




