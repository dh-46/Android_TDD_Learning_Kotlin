package tw.dh46.android_tdd_learning_kotlin.lab3

import android.content.Context

/**
 *  Created by DanielHuang on 2020/4/10
 *  Day11 - Instrumented Tests
 *
 *  其實在練習的lab2pra就已經做過使用interface的寫法，
 *  不過lab3的練習，會是介紹Instrumented Tests，
 *  所以還是另外開一個package跟著作者練習。
 *  ----------------------------------
 *  在lab2的範例中，我們使用mock的方式去模擬Android framework的物件，
 *  來解決相依性的問題。
 *  這樣的做法能夠加速測試速度，但是被驗證的Code可能在實際的裝置上會有問題。
 *  因為是使用mock的物件做測試，無法完全模擬Android framework的情況。
 *  ----------------------------------
 *  這次的練習，要將測試拆成兩部分:
 *
 *  1. Local Unit Test: 測試 Repository.saveUserId 與 IPreferenceManager的互動
 *  2. Instrumented test: 測試 SharedPreference 物件是否有儲存成功
 *
 *  Local Unit Test:
 *  TestCode --測試--> Repository.saveUserId (SUT)
 *           --驗證是否有正確與mock互動--> IPreferenceManager (MOCK)
 *  Instrumented test:
 *  AndroidTestCode --測試--> SharedPreference
 *
 *  ----------------------------------
 *  小結:
 *  這個範例只有SP需要使用InstrumentedTest，
 *  而Repository是使用IPreferenceManger的介面來操作，
 *  所以Repository可以利用Unit Test的方式做測試，
 *  測試Repository與介面的互動是否正確(是否有呼叫、值是否正確)。
 *
 *  Repository利用Unit Test提高測試效率，
 *  PreferenceManager則使用Instrumented Test來模擬真實的情況。
 *
 */
interface IPreferenceManager {

    val context: Context

    fun saveString(key: String, value: String)

    fun getString(key: String): String
}