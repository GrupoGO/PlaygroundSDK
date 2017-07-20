# playgroundSDK
This module allows integrating the widget of Playground in your app in only two easy steps. With this widget, called ActionsPagerView, you will have access to the social actions and take part of them.

#How to add the module to your project?

You will need to add the jitpack.io repository in your build.gradle project file (Note: do not add the jitpack.io repository under buildscript)

```
repositories {
    jcenter()
    maven { url "https://jitpack.io" }
}
```

You will also need to add this line in the dependencies of your build.gradle module file

```
dependencies {
    compile 'com.github.GrupoGO:playgroundsdk:2.0'
}
```

#How to use the widget?

You have to add an ActionsPagerView into your XML layout file:

```
<es.grupogo.playgroundsdk.widget.ActionsPagerView
        android:id="@+id/pager"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```
At this point, your widget will work! It will show the more popular actions. However, if you want to add a personalization layer, in order to show more concrete actions, it is also possible.

You can set these variables:
    - Query: Only the actions which contain in their content the specified query will be showed.
    - Number of actions: You can specify the number of actions which you want to be showed in the ActionsPagerView.
    - Position: You can introduce a position (latitude and longitude), and only the closest actions will be showed.
    
These variables could be set from XML layout...

```
<es.grupogo.playgroundsdk.widget.ActionsPagerView
        android:id="@+id/pager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:query_widget="animals"
        app:num_actions_widget="10"
        app:latitude_widget="41.455335"
        app:longitude_widget="2.184219"/>
```

...or programatically.

```
ActionsPagerView actionsPagerView = (ActionsPagerView) findViewById(R.id.pager);
actionsPagerView.setNumActions(10);
actionsPagerView.setQuery("animals");
actionsPagerView.setPosition(41.455335, 2.184219);

```






