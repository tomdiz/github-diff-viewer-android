# github-diff-viewer-android
Github PR Diff Viewer Android App using Github Rest API web services 

Introduction
------------

The goal of this application is to implement interface with Github REST API web service. 

Getting Started
---------------
This project uses the Gradle build system. To build this project, use the
`gradlew build` command or use "Import Project" in Android Studio.

Github Documentation
---------
The official [Github Documentation REST API v3](https://developer.github.com/v3/) in details.


Libraries Used
--------------
* Android
  * [AppCompat](https://developer.android.com/jetpack/androidx) - Degrade gracefully on older versions of Android.
  * [Android Kotlin](https://developer.android.com/kotlin) - Write more concise, idiomatic Kotlin code.
  * [Test](https://developer.android.com/training/testing/) - An Android testing framework for unit and runtime UI tests.
* Third party
  * [Retrofit](https://square.github.io/retrofit/) to consume JSON data via HTTP REST client
  * [Picasso](https://square.github.io/picasso/) for image loading
  * [Custom Tabs](https://developer.chrome.com/multidevice/android/customtabs) for launching URLs in our android applications
  * [Multidex](https://developer.android.com/studio/build/multidex) for Enable multidex for apps with over 64K methods

Android Studio IDE setup
------------------------
For development, the latest version of Android Studio is required with Kotlin plugin configure. The latest version can be
downloaded from [here](https://developer.android.com/studio/).

- Start Android Studio and import or open project.
