# FlipChecker
This library let you define a front and back layout, which get added to the FlipIconChecker. The view flips with a animation to the outher side when its checked and a custom view scale in. This library makes it easy to make flipping views like Google in the Holo and Material Gmail app.

Preview:

![alt tag](/images/example_round.gif "Round Example") ![alt tag](/images/example_rect.gif "Rect Example")

## Usage

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
When you use custom layouts its best you set "layout_width" and "layout_height" of the FlipIconChecker to "wrap_content".
You get the best results when all custom layouts have the same size. The checkView layout, which you specify get scaled from 0 to 80% of his full size. 

```xml
<org.tmcrafz.flipiconchecker.FlipIconChecker
        android:id="@+id/flipChecker"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:frontView="@layout/custom_front_layout"
        app:backView="@layout/custom_back_layout"
        app:checkView="@layout/custom_check_layout" />

```


```java

```

