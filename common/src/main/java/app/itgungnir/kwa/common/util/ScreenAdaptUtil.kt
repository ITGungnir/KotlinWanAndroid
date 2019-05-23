package app.itgungnir.kwa.common.util

import android.app.Activity
import android.app.Application
import android.content.res.Resources
import android.os.Bundle
import app.itgungnir.kwa.common.ADAPT_WIDTH

class ScreenAdaptUtil : Util {

    override fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(AppActivityLifecycleCallback(application))
    }

    inner class AppActivityLifecycleCallback(private val application: Application) :
        Application.ActivityLifecycleCallbacks {

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            activity?.let {
                val systemDM = Resources.getSystem().displayMetrics
                val appDM = application.resources.displayMetrics
                val activityDM = it.resources.displayMetrics

                activityDM.apply {
                    density = activityDM.widthPixels / ADAPT_WIDTH
                    scaledDensity = activityDM.density
                    densityDpi = (160 * activityDM.density).toInt()
                }

                appDM.apply {
                    density = activityDM.density
                    scaledDensity = activityDM.scaledDensity
                    densityDpi = activityDM.densityDpi
                }
            }
        }

        override fun onActivityStarted(activity: Activity?) {}

        override fun onActivityResumed(activity: Activity?) {}

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}

        override fun onActivityPaused(activity: Activity?) {}

        override fun onActivityStopped(activity: Activity?) {}

        override fun onActivityDestroyed(activity: Activity?) {}
    }
}