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
# Untuk Compose
-keep class androidx.compose.** { *; }
-keep class androidx.lifecycle.** { *; }
-keep class kotlin.Metadata { *; }
-keep class kotlin.** { *; }
-dontwarn kotlin.**

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

-keep class com.dicoding.newsapp.favorite.** { *; }

# Tambahan untuk material3
-keep class androidx.compose.material3.** { *; }