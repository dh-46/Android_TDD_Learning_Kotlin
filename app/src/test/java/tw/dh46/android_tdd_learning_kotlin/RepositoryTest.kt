package tw.dh46.android_tdd_learning_kotlin

import android.content.Context
import android.content.SharedPreferences
import org.junit.Test
import org.mockito.ArgumentMatcher
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

/**
 *  Created by DanielHuang on 2020/4/9
 *
 *  Repository中使用的SharedPreference物件有使用到Context物件，
 *  因此，就常理推斷應該使用InstrumentTest(AndroidTest)，
 *  但是為了增加測試的速度與效率，我們希望能夠直接在JVM上執行UnitTest。
 *  所以必須使用Mockito框架
 *
 *  測試步驟：
 *
 *  1. Mock Context、SharePreference
 *  2. 使用when thenReturn 讓Production code 呼叫sharedPreference時回傳模擬的物件
 *  3. 執行被測試物件：Activity 呼叫repository.saveUserId()
 *  4. 使用verify method，驗證模擬物件是否有呼叫putString，並傳入正確的參數
 *  5. 檢查SharedPreference是否有呼叫commit
 *
 */
class RepositoryTest {

    @Test
    fun saveUserId() {

        val userId = "A1234567"
        val preKey = "UserId"

        // 1. Mock Context & SharedPreference
        val mockSharedPrefs = mock(SharedPreferences::class.java)
        val mockSharedPrefsEditor = mock(SharedPreferences.Editor::class.java)
        val mockContext = mock(Context::class.java)

        // 2. 使用when thenReturn 讓Production code 呼叫sharedPreference時回傳模擬的物件
        // 當Production code呼叫SP時回傳mock的物件
        `when`(
            mockContext.getSharedPreferences(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyInt()
            )
        ).thenReturn(mockSharedPrefs)
        // 當Production code呼叫SP.edit()時回傳mock的物件
        `when`(mockSharedPrefs.edit()).thenReturn(mockSharedPrefsEditor)
        // Production code呼叫editor.putString時回傳mock的物件
        `when`(
            mockSharedPrefsEditor.putString(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
            )
        ).thenReturn(mockSharedPrefsEditor)

        // 3. 執行被測試物件 => 呼叫repository.saveUserId()
        val repository = Repository(mockContext)
        repository.saveUserId(userId)

        // 4. 使用verify method，驗證模擬物件是否有呼叫putString，並傳入正確的參數
        verify(mockSharedPrefsEditor).putString(
            // FIXME: 這裡Lambda有點不太懂
            // argThat 好像是ArgumentMathers的方法
            argThat { key ->
                key == preKey
            },
            argThat { value ->
                value == userId
            }
        )

        // 5. 檢查SharedPreference是否有呼叫commit
        verify(mockSharedPrefsEditor).commit()
    }
}