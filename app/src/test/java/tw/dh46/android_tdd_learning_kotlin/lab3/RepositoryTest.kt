package tw.dh46.android_tdd_learning_kotlin.lab3

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 *  Created by DanielHuang on 2020/4/10
 */
class RepositoryTest {

    @Test
    fun saveUserId(){
        // Mock PreferenceManager
        val mockPreferenceManager = mock(IPreferenceManager::class.java)
        val userId = "A1234567"
        val preKey = "UserId"
        val repository = Repository(mockPreferenceManager)

        // 執行要被測試的程式碼
        repository.saveUserId(userId)

        // 檢測
        verify(mockPreferenceManager).saveString(preKey, userId)
    }
}