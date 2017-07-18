# playgroundSDK
With this library you will have access to the DoButton and his awesomeness.

#How to add the library to your project?

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
    compile 'com.github.GrupoGO:playgroundsdk:1.6'
}
```

#Usage
Put it into your layout xml

```
<es.grupogo.playgroundsdk.DoButton
        android:id="@+id/button_do"
        android:layout_width="48dp"
        android:layout_height="48dp"
        app:tint="#feafea"
        app:query="android"/>
```
