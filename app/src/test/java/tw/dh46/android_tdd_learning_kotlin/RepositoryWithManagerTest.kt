package tw.dh46.android_tdd_learning_kotlin

import org.junit.Test
import org.mockito.Mockito.*
import tw.dh46.android_tdd_learning_kotlin.repository.ISharedPreferenceManager
import tw.dh46.android_tdd_learning_kotlin.repository.RepositoryWithManager

/**
 *  Created by DanielHuang on 2020/4/9
 */
class RepositoryWithManagerTest {

    @Test
    fun saveUserId() {
        // 用介面避免mock context等Android framework的類別
        val sharedPreferenceManager = mock(ISharedPreferenceManager::class.java)

        val repositoryWithManager = RepositoryWithManager(sharedPreferenceManager)

        // 執行要測試的行為
        val userId = "A2134565"
        repositoryWithManager.saveUserId(userId)

        // 驗證是否有執行該方法
        // FIXME: 如何去驗證輸入的值呢?
        verify(sharedPreferenceManager).insertString("UserId", userId)
    }
}