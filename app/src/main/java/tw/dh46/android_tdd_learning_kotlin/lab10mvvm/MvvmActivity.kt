package tw.dh46.android_tdd_learning_kotlin.lab10mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import tw.dh46.android_tdd_learning_kotlin.R
import tw.dh46.android_tdd_learning_kotlin.databinding.ActivityMvvmBinding
import tw.dh46.android_tdd_learning_kotlin.lab8MVP.api.ProductAPI
import tw.dh46.android_tdd_learning_kotlin.lab8MVP.model.ProductRepository

/**
 * Day19,20 - Android MVVM 架構
 *
 * - MVVM是Model、View、ViewModel的簡稱。
 * - Model: 負責管理資料來源
 * - View:
 *      - Activity、Fragment、res/layout裡xml，這些都屬於View。
 *      - 只處理顯示UI及與使用者互動
 * - ViewModel:
 *      - 接收View的請求並從Model取得資料
 *      - 只處理商業邏輯與資料相關的事
 *      - 使用DataBinding的技術自動綁定至UI
 *      - ViewModel不會持有任何的UI實體
 * ---------------------------------------
 * DataBinding:
 * - DataBinding 是一種實現ViewModel與View 協作的方式。
 * - 是UI與資料綁定的一種方式。
 * - DtatBinding則是使用聲明式的方式讓UI綁定到資料來源
 * ---------------------------------------
 * 步驟
 * - 建立Model，也就是Data Model，用來存放要放到UI上的資料。
 * - 將UI與Data Model繫結。
 * - 綁定事件，點擊購買的事件。
 */
class MvvmActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMvvmBinding
    private val productId = "pixel3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_mvvm)

        mBinding = DataBindingUtil.setContentView<ActivityMvvmBinding>(this, R.layout.activity_mvvm)

        // 使用MVP練習時的ProductAPI
        var productAPI = ProductAPI()
        var productRepository = ProductRepository(productAPI)

        // 初始化ViewModel
        val productViewModel = ProductViewModel(productRepository)

        mBinding.productViewModel = productViewModel

        // 加這一段就可以讓model有變就更新回UI
        mBinding.lifecycleOwner = this

        // 取得資料
        productViewModel.getProduct(productId)
    }
}
