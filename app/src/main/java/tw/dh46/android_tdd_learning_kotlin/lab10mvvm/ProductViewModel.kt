package tw.dh46.android_tdd_learning_kotlin.lab10mvvm

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tw.dh46.android_tdd_learning_kotlin.lab10mvvm.utils.Event
import tw.dh46.android_tdd_learning_kotlin.lab8MVP.api.ProductResponse
import tw.dh46.android_tdd_learning_kotlin.lab8MVP.model.IProductRepository
import tw.dh46.android_tdd_learning_kotlin.lab8MVP.model.ProductRepository

/**
 * Day19_MVVM_DataBinding
 * ----------------------
 * 用來做資料繫結用的物件
 * ----------------------
 * Day20_MVVM_LiveData & ViewModel
 *
 * ViewModel:
 * - Android Jetpack裡的lifecycle類
 * - 解決記憶體洩漏
 * - Activity生命週期問題 (ViewModel比Activity生命週期更長)
 * - 解決旋轉後資料不見或使用者離開過後Activity被回收後的問題
 *
 * LiveData:
 * - 觀察者模式的類別
 * - 相較於一般Observable 對於生命週期有概念
 * - 能夠確保Activity或Fragment在活耀時才收到資料
 *
 * -------------------------------------------
 * 使用ViewModel，資料就可以從UI中分離出來，
 * 讓每個元件的職責更清礎，
 * 在Activity或Fragment重新產生時，
 * ViewModel仍會保留資料給Activity與Fragment使用。
 * -------------------------------------------
 * LiveData優點
 * - UI和資料保持一致
 * - 避免Memory Leak 及 Activity處於stop狀態而造成閃退
 * - 不需要手動處理生命週期的問題
 * - 解決Configuration Change的問題
 */
class ProductViewModel(private val productRepository: IProductRepository) : ViewModel() {

    var productId: MutableLiveData<String> = MutableLiveData()

    var productName: MutableLiveData<String> = MutableLiveData<String>()
    var productDesc: MutableLiveData<String> = MutableLiveData()
    var productPrice: MutableLiveData<Int> = MutableLiveData()
    var productItems: MutableLiveData<String> = MutableLiveData()

    // 購買成功顯示文字
    var buySuccessText: MutableLiveData<Event<String>> = MutableLiveData()
    // 購買失敗顯示文字
    var alertText: MutableLiveData<Event<String>> = MutableLiveData()

    fun getProduct(productId: String) {
        this.productId.value = productId

        productRepository.getProduct(productId, object : IProductRepository.LoadProductCallback{
            override fun onProductResult(productResponse: ProductResponse) {
                productName.value = productResponse.name
                productDesc.value = productResponse.desc
                productPrice.value = productResponse.price
            }
        })
    }

    /**
     * 1.購買成功將buySuccessText 指定為Event("購買成功")
     * 2.購買失敗將buyFailText 指定為Event("購買失敗")
     *
     * TODO: 為什麼要用Event包裝，看下面連結
     * https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
     */
    fun buy(){
        // println("Buy")
        val productId = productId.value ?: ""
        val numbers = Integer.parseInt((productItems.value ?: "0"))

        productRepository.buy(productId, numbers, object : IProductRepository.BuyProductCallback {
            override fun onBuyResult(isSuccess: Boolean) {
                if (isSuccess) {
                    buySuccessText.value = Event("購買成功")
                } else {
                    alertText.value = Event("購買失敗")
                }
            }
        })

    }
}