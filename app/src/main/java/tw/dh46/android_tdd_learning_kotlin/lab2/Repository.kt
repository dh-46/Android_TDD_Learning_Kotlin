package tw.dh46.android_tdd_learning_kotlin.lab2

import android.content.Context

/**
 *  Created by DanielHuang on 2020/4/9
 *  Day10 - Mock Android Framework: https://ithelp.ithome.com.tw/articles/10219891
 *
 *  透過Mock Android Framework，解決當程式相依於Android framework時，仍使用Local Unit Test
 *  --------------------------------------------------
 *  Repository: 處理帳號與密碼儲存(SharedPreference)
 *
 */
class Repository(private val context: Context) {

    /**
     * 儲存帳號至SharedPreference
     */
    fun saveUserId(id: String) {
        val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("UserId", id).commit()
    }


}