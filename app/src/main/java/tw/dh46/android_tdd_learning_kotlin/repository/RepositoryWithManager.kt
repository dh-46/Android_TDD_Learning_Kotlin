package tw.dh46.android_tdd_learning_kotlin.repository

/**
 *  Created by DanielHuang on 2020/4/9
 *  練習改用介面解耦合
 */
class RepositoryWithManager(private val sharedPreferenceManager: ISharedPreferenceManager) {

    /**
     * 改透過SharedPreferenceManager來存取
     */
    fun saveUserId(id: String) {
        sharedPreferenceManager.insertString("UserId", id)
    }
}