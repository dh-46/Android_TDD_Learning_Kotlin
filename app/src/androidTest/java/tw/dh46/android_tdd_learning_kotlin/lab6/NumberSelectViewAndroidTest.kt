package tw.dh46.android_tdd_learning_kotlin.lab6

import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.android.synthetic.main.view_lab6_number_select.view.*
import org.junit.Assert
import org.junit.Test

/**
 *  Created by DanielHuang on 2020/4/10
 *  Day14-使用Custom View Components提升可測試性
 *
 *  這樣的測試就可使用Instrumented Test
 *  而再大一點，像是像是有使用到Activity時就可使用Espresso的UI Test，
 *  以測試整個流程。
 *
 * */
class NumberSelectViewAndroidTest {

    /**
     * 測試按下+按鈕時，數字有正確增加
     */
    @Test
    fun testBtnAddPressTvValueShouldAdd(){

        // 取得目標Context
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        // 取得客製化View
        val viewNumberSelect = NumberSelectView(context)
        // 設定預設值
        viewNumberSelect.setDefaultValue(1)
        // 執行點擊行為
        viewNumberSelect.btn_add.performClick()

        // 驗證值的變動是否正確
        Assert.assertEquals(2, viewNumberSelect.currentValue)
    }

    /**
     * 測試按下-按鈕時，數字有正確減少
     */
    @Test
    fun testBtnMinusPressTvValueShouldMinus(){
        // 取得目標Context
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        // 取得客製化View
        val viewNumberSelect = NumberSelectView(context)
        // 設定預設值
        viewNumberSelect.setDefaultValue(2)
        // 執行點擊行為
        viewNumberSelect.btn_minus.performClick()

        // 驗證值的變動是否正確
        Assert.assertEquals(1, viewNumberSelect.currentValue)
    }

    /**
     * 測試不能小於最小值
     */
    @Test
    fun testMinValueLimit(){
        // 取得目標Context
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        // 取得客製化View
        val viewNumberSelect = NumberSelectView(context)
        // 設定預設值
        viewNumberSelect.setDefaultValue(2)
        viewNumberSelect.setMinValue(2)
        // 執行點擊行為
        viewNumberSelect.btn_minus.performClick()

        Assert.assertEquals(2, viewNumberSelect.currentValue)
    }

    /**
     * 測試不能大於最大值
     */
    @Test
    fun testMaxValueLimit(){
        // 取得目標Context
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        // 取得客製化View
        val viewNumberSelect = NumberSelectView(context)
        // 設定預設值
        viewNumberSelect.setDefaultValue(2)

        viewNumberSelect.setMaxValue(2)

        // 執行點擊行為
        viewNumberSelect.btn_add.performClick()

        Assert.assertEquals(2,viewNumberSelect.currentValue)
    }
}