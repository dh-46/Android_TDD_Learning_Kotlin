package tw.dh46.android_tdd_learning_kotlin.lab5

import android.os.Build
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowAlertDialog
import tw.dh46.android_tdd_learning_kotlin.MainActivity
import tw.dh46.android_tdd_learning_kotlin.ResultActivity

/**
 *  Created by DanielHuang on 2020/4/10
 *  Day13-使用Robolectric 撰寫 Android test
 *  Robolectric，讓可以讓我們用單元測試的方式來執行Android Tests，
 *  也就是可以在JVM上執行測試，大大的提升了執行測試的效率。
 *
 *  --------------------------------------
 *
 *  延續上一個範例，以下測試要用Robolectric單元測試的方式來測試View
 *
 *  1. 當註冊失敗，alert "註冊失敗"
 *  2. 當註冊成功，開啟註冊成功頁
 *
 *
 * ---------------------------------------
 * Build Failed 筆記
 *
 * Error:
 * WARN: Android SDK 29 requires Java 9 (have Java 8). Tests won't be run on SDK 29 unless explicitly requested.
 * java.lang.UnsupportedOperationException: Failed to create a Robolectric sandbox: Android SDK 29 requires Java 9 (have Java 8)
 *
 * Ref:
 * https://stackoverflow.com/questions/56821193/does-robolectric-require-java-9
 *
 * Solutions:
 * 1. 在測試類別上加上 @Config(sdk = [Build.VERSION_CODES.P]) 指定SDK
 * 2. Run / Edit Configurations / JRE => 調成Java SDK 9
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class MainActivityTest {

    private lateinit var activity: MainActivity

    @Before
    fun setupActivity(){
        MockitoAnnotations.initMocks(this)

        activity = Robolectric.buildActivity(MainActivity::class.java).setup().get()
    }

    /**
     * 測試註冊成功後，應該開啟結果Activity
     */
    @Test
    fun registerSuccessShouldOpenResultActivity(){
        // 建立一個ShadowActivity，將用來觀察是否有開啟別的Activity
        val shadowActivity = Shadows.shadowOf(activity)

        val userId = "A123456789"
        val pwd = "BB23456789"

        activity.editAccount.setText(userId)
        activity.editPassword.setText(pwd)

        activity.btnRegister.performClick()

        // 驗證是否有開啟下一個Activity
        val nextIntent = shadowActivity.nextStartedActivity

        // 比對類別名稱 => Intent的目的地的Activity
        // tw.dh46.android_tdd_learning_kotlin
        Assert.assertEquals(nextIntent.component!!.className, ResultActivity::class.java.name)

        // 比對Intent的Size (雙驚嘆號 !! => 斷言不為null)
        Assert.assertEquals(1, nextIntent.extras!!.size())

        // 比對Intent傳送的Key:Value
        Assert.assertEquals(userId, nextIntent.extras!!.getString("ID"))
    }

    @Test
    fun registerFailedShouldAlert(){
        val userId = "A123456789"
        val pwd = "6789"

        activity.editAccount.setText(userId)
        activity.editPassword.setText(pwd)

        activity.btnRegister.performClick()

        // 驗證是否跳出AlertDialog
        val shadowAlertDialog = ShadowAlertDialog.getLatestDialog()

        // 驗證Dialog有被Create
        Assert.assertNotNull(shadowAlertDialog)
        // 驗證Dialog有顯示
        Assert.assertTrue(shadowAlertDialog.isShowing)
    }
}