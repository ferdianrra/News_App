# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


# Keep Dagger-generated classes and interfaces
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-keep,allowobfuscation interface <1>
-dontwarn kotlinx.**
-keep class kotlinx.coroutines.** { *; }

# Preserve Dagger annotations
-keepattributes *Annotation*

-keep class * extends dagger.internal.Factory
-keep class * implements dagger.Component
-keep class * implements dagger.Module
-keep class * implements dagger.Subcomponent
-keep class * implements dagger.Binds

-keep class com.dicoding.newsapp.core.domain.repository.INewsRepository { *; }
-keep class com.dicoding.newsapp.core.domain.model.News { *; }
-keep class com.dicoding.newsapp.core.data.NewsRepository { *; }
-keep class com.dicoding.newsapp.core.domain.** { *; }
-keep class com.dicoding.newsapp.core.data.** { *; }
-keepattributes Signature
-keepattributes *Annotation*
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }



# Untuk menjaga kelas-kelas dari CoreComponent dan Dagger
-keep class com.dicoding.newsapp.core.di.CoreComponent { *; }
-keep class com.dicoding.newsapp.core.di.DaggerCoreComponent { *; }
-keep class com.dicoding.newsapp.core.di.CoreComponent$Factory { *; }


# Untuk menjaga kelas ViewModelFactory
-keep class com.dicoding.newsapp.core.ui.ViewModelFactory { *; }

# Untuk menjaga utilitas
-keep class com.dicoding.newsapp.core.utils.FormatDate { *; }


-keep class org.mockito.** { *; }
-keep class org.junit.** { *; }
-dontwarn org.mockito.**
-dontwarn org.junit.**