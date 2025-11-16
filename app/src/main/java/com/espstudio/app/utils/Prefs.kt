package com.espstudio.app.utils

import android.content.Context

object Prefs {

    private const val KEY_LAST_SKETCH = "last_sketch"
    private const val KEY_FORMAT_ON_SAVE = "format_on_save"

    fun saveLastSketch(ctx: Context, path: String) {
        ctx.getSharedPreferences("espstudio", Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_LAST_SKETCH, path)
            .apply()
    }

    fun getLastSketch(ctx: Context): String? {
        return ctx.getSharedPreferences("espstudio", Context.MODE_PRIVATE)
            .getString(KEY_LAST_SKETCH, null)
    }

    fun setFormatOnSave(ctx: Context, value: Boolean) {
        ctx.getSharedPreferences("espstudio", Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_FORMAT_ON_SAVE, value)
            .apply()
    }

    fun getFormatOnSave(ctx: Context): Boolean {
        return ctx.getSharedPreferences("espstudio", Context.MODE_PRIVATE)
            .getBoolean(KEY_FORMAT_ON_SAVE, false)
    }
}
