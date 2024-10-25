package com.dicoding.newsapp.utils

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.Modifier
import com.dicoding.newsapp.DFFactory

object DFUtils {

    @Composable
    fun favoriteScreen(modifier: Modifier = Modifier, navigateBack:() -> Unit,  navigateToDetail: (String) -> Unit): Boolean {
        return loadClass(modifier = modifier, navigateBack = navigateBack, navigateToDetail = navigateToDetail, className = DFFactory.FavScreen.DF_FAV_SCREEN, methodName = DFFactory.FavScreen.COMPOSE_METHOD)
    }

    @Composable
    private fun loadClass(modifier: Modifier = Modifier, navigateBack: () -> Unit,  navigateToDetail: (String) -> Unit, className: String, methodName: String, instanceObject: Any = Any()): Boolean {
        val dynamicClass = loadClass(className)
        if (dynamicClass != null) {
            val composer = currentComposer
            val method = findMethod(dynamicClass, methodName)
            if (method != null) {
                val isMethodInvoke = invokeMethod(method = method, objek = instanceObject, modifier, navigateBack, navigateToDetail, composer, 0,0)
                if (!isMethodInvoke) {
                    return false
                }
                return true
            } else {
                return false
            }
        } else {
            return false
        }
    }
}