package tw.dh46.android_tdd_learning_kotlin.lab3

import android.content.Context
import android.content.SharedPreferences

/**
 *  Created by DanielHuang on 2020/4/10
 *
 *  實作 IPreferenceManager 介面，
 *  由這個類別統一處理SharedPreference的儲存與提取
 */
class PreferenceManager(override val context: Context) : IPreferenceManager {

    private val sharedPreferenceName = "UserData"

    var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE)
    }

    override fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key,value).commit()
    }

    override fun getString(key: String): String {
        return sharedPreferences.getString(key, "")!! // 雙驚嘆號斷言不可能為null
    }
}