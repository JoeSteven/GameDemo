package com.joey.gamedemo

import android.app.Application
import android.content.Context
import com.joey.cheetah.core.CheetahApplicationInitializer
import com.joey.cheetah.core.init.InitManager
import com.joey.cheetah.core.utils.CLog

/**
 * Description:
 * author:Joey
 * date:2018/11/28
 */
class GameApplication: Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        CheetahApplicationInitializer.attachBaseContext(this, object :InitManager(){
            override fun addTask() {
                CLog.debug(BuildConfig.DEBUG)
            }

        })
    }

    override fun onCreate() {
        CheetahApplicationInitializer.beforeSuperOnCreate()
        super.onCreate()
        CheetahApplicationInitializer.afterSuperOnCreate()
    }
}