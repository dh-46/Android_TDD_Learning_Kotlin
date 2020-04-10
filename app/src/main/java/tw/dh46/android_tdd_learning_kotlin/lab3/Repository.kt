package tw.dh46.android_tdd_learning_kotlin.lab3

/**
 *  Created by DanielHuang on 2020/4/10
 */
class Repository(private val preferenceManager: IPreferenceManager) {

    fun saveUserId(userId: String) {
        // 直接呼叫preferenceManager來儲存使用者帳號
        preferenceManager.saveString("UserId", userId)
    }
}