package tw.dh46.android_tdd_learning_kotlin.repository

import android.content.Context

/**
 *  Created by DanielHuang on 2020/4/9
 */
class SharedPreferenceManager(private var context: Context): ISharedPreferenceManager {

    override fun insertString(key: String, value: String) {
        // 這裡應該要實作寫入SharedPreference
    }
}