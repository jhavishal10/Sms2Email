package com.android.smstoemail

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

@SuppressLint("CommitPrefEdits")
class Prefs(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(Prefs::class.java.simpleName, Activity.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor

    init {
        this.editor = prefs.edit()
    }

    var email: String?
        get() = prefs.getString("email", "")
        set(token) = editor.putString("email", token).apply()
}
