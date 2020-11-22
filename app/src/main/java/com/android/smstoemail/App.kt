package com.android.smstoemail

import android.app.Application
import android.content.Intent


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startService(Intent(this, YourService::class.java))
        prefs = Prefs(this)
    }

    companion object {
        lateinit var prefs: Prefs
    }
}