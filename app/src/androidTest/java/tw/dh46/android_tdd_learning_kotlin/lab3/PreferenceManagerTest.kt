package tw.dh46.android_tdd_learning_kotlin.lab3

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 *  Created by DanielHuang on 2020/4/10
 *
 *  執行時會開啟模擬器或手機，表示是使用InstrumentedTest
 *  因為已使用PreferenceManagerTest來處理SP寫入，
 *  所以之後程式擴充，應該不需要再新寫別的Android測試程式。
 *
 *  記得，如果不是使用Android framework就該使用Local Unit Test
 *
 */

@RunWith(AndroidJUnit4::class)
class PreferenceManagerTest {

    @Test
    fun useAppContext(){
        // 取得被測試app的Context物件
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val userId = "A1234567"
        val preKey = "UserId"

        // 建立PManager物件
        val preferenceManager: IPreferenceManager = PreferenceManager(appContext)
        // 寫入
        preferenceManager.saveString(preKey, userId)
        // 寫出
        val valueFromManager = preferenceManager.getString(preKey)

        // 驗證輸入輸出是否相同
        Assert.assertEquals(userId, valueFromManager)
    }
}