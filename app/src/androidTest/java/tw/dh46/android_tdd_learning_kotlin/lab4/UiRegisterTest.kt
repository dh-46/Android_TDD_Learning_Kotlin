package tw.dh46.android_tdd_learning_kotlin.lab4

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import kotlinx.android.synthetic.main.activity_main.view.*
import org.junit.Rule
import org.junit.Test
import tw.dh46.android_tdd_learning_kotlin.MainActivity
import tw.dh46.android_tdd_learning_kotlin.R

/**
 *  Created by DanielHuang on 2020/4/10
 *  Day12-UI Test by Espresso
 *  Lab4的練習會是測試種類中最耗時與成本最高的UI測試。
 *
 *  Espresso是Android UI的測試框架，
 *  可以用id或文字的方式取得元件，
 *  模擬使用者點擊、輸入資料等使用者行為，
 *  驗證App是否有出現預期的功能。
 *
 *  UI Test 一樣是寫在 androidTest的位置 (同Instrumented Test)
 *  以下的範例將測試首頁的註冊功能
 *  ---------------------------------
 *  `@LargeTest`:
 *     - 當會使用網路存取、資料庫、多執行緒的耗時工作，需在測試類別上加上此註釋。
 *     - 原則上超過一秒都需要
 *
 *  ----------------------------------
 *  其他驗證的方式
 *  - text is：檢查文字內容是否是該文字
 *  - exists：檢查View元件是存在於於螢幕可見的View中。
 *  - does not exist：檢查View元件是不存在於於螢幕可見的View中。
 *
 *
 *  -----------------------------------
 *  利用錄製的方式產生測試程式碼
 *
 *  1. Run > Record Espresso Test
 *  2. 可選擇模擬或實體手機
 *  3. 直接操作App，左邊就會依照你在App做了哪些動作，錄制下結果。
 *  4. 要驗證是否有出現「註冊成功」的字，點擊Add Assertion，左下角就會出現建議的驗證方式。
 *  5. 儲存測試類別。這樣就可以透過錄制的方式來產生測試程式碼
 *  6. 重構產生的測試碼
 *  > 適合當初學測試時的參考: 例如如何取得畫面元件
 *
 */
@LargeTest
class UiRegisterTest {

    // 設定目標的Activity並開啟
    // 使其在開始測試前開啟Activity
    @Rule
    @JvmField
    var activityActivityTestRule = ActivityTestRule(MainActivity::class.java)

    /**
     * 帳號密碼正確
     */
    @Test
    fun rightPwd_should_startActivity(){

        // 輸入帳號並關閉鍵盤
        onView(withId(R.id.edit_account)).perform(typeText("a12345678"), ViewActions.closeSoftKeyboard())

        // 輸入密碼並關閉鍵盤
        onView(withId(R.id.edit_password)).perform(typeText("b123456789"), ViewActions.closeSoftKeyboard())

        // 點選註冊按鈕
        onView(withId(R.id.btn_register)).perform(click())

        // 註冊成功跳轉成功畫面，比對註冊成功的文字
        onView(withText("註冊成功")).check(matches(isDisplayed()))
    }

    @Test
    fun wrongPwd_should_toast(){
        // 輸入帳號並關閉鍵盤
        onView(withId(R.id.edit_account)).perform(typeText("a12345678"), ViewActions.closeSoftKeyboard())

        // 輸入密碼並關閉鍵盤
        onView(withId(R.id.edit_password)).perform(typeText("b123"), ViewActions.closeSoftKeyboard())

        // 點選註冊按鈕
        onView(withId(R.id.btn_register)).perform(click())

        // 註冊失敗: toast
        onView(withText("密碼不符規則")).inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }

    @Test
    fun wrongUserId_should_toast() {
        // 輸入帳號並關閉鍵盤
        onView(withId(R.id.edit_account)).perform(typeText("a123"), ViewActions.closeSoftKeyboard())

        // 輸入密碼並關閉鍵盤
        onView(withId(R.id.edit_password)).perform(typeText("b12345678"), ViewActions.closeSoftKeyboard())

        // 點選註冊按鈕
        onView(withId(R.id.btn_register)).perform(click())

        // 註冊失敗: 驗證Toast的文字是否符合
        onView(withText("帳號不符規則")).inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }


}