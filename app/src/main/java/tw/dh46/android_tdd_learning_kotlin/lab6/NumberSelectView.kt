package tw.dh46.android_tdd_learning_kotlin.lab6

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.view.*
import tw.dh46.android_tdd_learning_kotlin.R

/**
 *  Created by DanielHuang on 2020/4/10
 *  Day14-使用Custom View Components提升可測試性
 *
 *  Android 能夠將不同的UI元件組合成一個客製化的View，
 *  並將邏輯封裝在一起，相對地提高了程式的測試性。
 *  ----------------------------------
 *  以下範例將製作一個常見的加減按鈕與文字呈現，
 *  就如同一般購物網站中常見的加減UI。
 *  當加減的事件與邏輯都封裝在這個客製的components中處理時，
 *  就不用牽扯到Activity，只要單獨對其測試就好。
 *  ----------------------------------
 *  類別會載入建好的layout，並將attributes做處理。再新增類別的公開方法：設定最大值、最小值、設定listener。
 */
class NumberSelectView : LinearLayout {

    private lateinit var btnAdd: Button
    private lateinit var btnMinus: Button
    private lateinit var tvValue: TextView

    // 最小值
    private var minValue: Int = 0

    // 最大值
    private var maxValue: Int = 0

    // 預設值
    private var defaultValue: Int = 0

    // 目前數值
    var currentValue: Int = 0

    private var listener: NumberSelectListener? = null

    // 數字選擇Callback
    interface NumberSelectListener {
        fun onValueChanged(value: Int)
    }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int)
            : super(context, attrs, defStyle) {
        init(context, attrs)
    }

    /**
     * 處理畫面與事件
     */
    private fun init(context: Context, attrs: AttributeSet?) {
        View.inflate(context, R.layout.view_lab6_number_select, this)
        // descendantFocusability 這句是啥
        descendantFocusability = ViewGroup.FOCUS_BLOCK_DESCENDANTS
        this.btnAdd = findViewById(R.id.btn_add)
        this.btnMinus = findViewById(R.id.btn_minus)
        this.tvValue = findViewById(R.id.tv_value)

        // 初始值
        this.currentValue = 0
        this.maxValue = Int.MAX_VALUE
        this.minValue = 0

        if (attrs != null) {
            // 有設定參數
            val attributes = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.NumberSelectView,
                0, 0
            )

            // 從layout上取得設定的值
            this.maxValue = attributes.getInt(R.styleable.NumberSelectView_max_value, this.maxValue)
            this.minValue = attributes.getInt(R.styleable.NumberSelectView_min_value, this.minValue)
            this.defaultValue = attributes.getInt(R.styleable.NumberSelectView_default_value, 0)

            // 設定畫面上的預設值
            this.tvValue.text = defaultValue.toString()
            // 設定預設值為目前的值
            this.currentValue = defaultValue
        }

        // 設定點擊事件
        // 點下「+」Button，將TextValue數字+1，並呼叫listener.onValueChange
        this.btnAdd.setOnClickListener(OnClickListener {
            // 加
            addCurrentValue()
            if (listener != null) {
                // 拋給Listener 如果不是null的話
                listener!!.onValueChanged(currentValue)
            }
        })

        // 點下「-」Button，將TextValue數字-1，並呼叫listener.onValueChange
        this.btnMinus.setOnClickListener {
            // 減
            minusCurrentValue()
            if (listener != null) {
                listener!!.onValueChanged(currentValue)
            }
        }
    }

    fun setListener(numberSelectListener: NumberSelectListener) {
        this.listener = numberSelectListener
    }

    fun setMaxValue(value: Int) {
        this.maxValue = value
    }

    fun setMinValue(value: Int) {
        this.minValue = value
    }

    fun setDefaultValue(value: Int) {
        this.defaultValue = value
        this.currentValue = value;
    }

    /**
     * 加
     */
    private fun addCurrentValue() {
        if (this.currentValue < this.maxValue) {
            this.currentValue++
            this.tvValue.text = currentValue.toString()
        }
    }

    /**
     * 減
     */
    private fun minusCurrentValue() {
        if (this.currentValue > this.minValue) {
            currentValue--
            this.tvValue.text = currentValue.toString()
        }
    }
}