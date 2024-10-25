package com.dicoding.newsapp.utils

import android.text.TextUtils
import android.util.Log
import java.lang.reflect.Method
import java.lang.reflect.Modifier

fun findMethod(classMethod: Class<*>?, methodName: String): Method? {
    return try {
        if (!TextUtils.isEmpty(methodName)) {
            classMethod?.let { classnm ->
                classnm.methods.find {
                    it.name.equals(methodName) && Modifier.isStatic(it.modifiers)
                }
            }?: run {
                    null
                }
            } else {
                null
            }
        }catch(e:Throwable) {
            null
        }
}

fun loadClass(className: String): Class<*>? {
    return try {
        val classLoader = ::loadClass.javaClass.classLoader
        classLoader?.loadClass(className)
    } catch (e:Throwable) {
        null
    }
}

fun invokeMethod(method: Method, objek: Any, vararg args: Any): Boolean {
    return try {
        method.invoke(objek,*(args))
        true
    } catch (e: Throwable) {
        Log.e("ObjectExtension", e.message.toString())
        false
    }
}