-keep,includedescriptorclasses class net.sqlcipher.** { *; }
-keep,includedescriptorclasses interface net.sqlcipher.** { *; }

# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**

# Keep the ListNewsResponse model class for Gson
-keep class com.dicoding.newsapp.core.data.source.remote.response.ListNewsResponse { *; }
-keep class com.dicoding.newsapp.core.data.source.remote.response.** { *; }

# Keep fields annotated with @SerializedName
-keepclassmembers class com.dicoding.newsapp.core.data.source.remote.response.ListNewsResponse {
    @com.google.gson.annotations.SerializedName <fields>;
}
# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { <fields>; }

# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
@com.google.gson.annotations.SerializedName <fields>;
}


##---------------Begin: proguard configuration for Retrofit ----------
# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod, *Annotation*

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
@retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep class com.google.gson.** { *; }
-keep,allowobfuscation interface <1>

-dontwarn kotlinx.**
-keep class kotlinx.coroutines.** { *; }

# Preserve Dagger annotations
-keepattributes *Annotation*

# Keep Dagger-generated classes and interfaces
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }


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

# Untuk menjaga kelas Resource dan subkelasnya
-keep class com.dicoding.newsapp.core.data.Resource { *; }
-keep class com.dicoding.newsapp.core.data.Resource$Error { *; }
-keep class com.dicoding.newsapp.core.data.Resource$Loading { *; }
-keep class com.dicoding.newsapp.core.data.Resource$Success { *; }

# Untuk menjaga kelas-kelas dari CoreComponent dan Dagger
-keep class com.dicoding.newsapp.core.di.CoreComponent { *; }
-keep class com.dicoding.newsapp.core.di.DaggerCoreComponent { *; }
-keep class com.dicoding.newsapp.core.di.CoreComponent$Factory { *; }

# Untuk menjaga model dan use case
-keep class com.dicoding.newsapp.core.domain.model.News { *; }
-keep class com.dicoding.newsapp.core.domain.repository.INewsRepository { *; }
-keep class com.dicoding.newsapp.core.domain.usecase.NewsInteractor { *; }
-keep class com.dicoding.newsapp.core.domain.usecase.NewsUseCase { *; }
-keep class com.dicoding.newsapp.core.domain.usecase.NewsInteractor_Factory { *; }


# Untuk menjaga kelas ViewModelFactory
-keep class com.dicoding.newsapp.core.ui.ViewModelFactory { *; }

# Untuk menjaga utilitas
-keep class com.dicoding.newsapp.core.utils.FormatDate { *; }


-keep class org.mockito.** { *; }
-keep class org.junit.** { *; }
-dontwarn org.mockito.**
-dontwarn org.junit.**

-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-keep,allowobfuscation interface <1>
-dontwarn kotlinx.**
-keep class kotlinx.coroutines.** { *; }
