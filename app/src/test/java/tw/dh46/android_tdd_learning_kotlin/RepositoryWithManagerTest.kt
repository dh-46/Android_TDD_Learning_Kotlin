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
        val preKey = "UserId"
        repositoryWithManager.saveUserId(userId)

        // 驗證是否有執行該方法以及是否有正確的值
        verify(sharedPreferenceManager).insertString(
            preKey,
            userId
        )

        // 驗證是否有執行該方法，不管其值
//        verify(sharedPreferenceManager).insertString(
//            ArgumentMatchers.anyString(),
//            ArgumentMatchers.anyString()
//        )
    }
}