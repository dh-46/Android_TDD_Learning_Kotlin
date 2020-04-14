package tw.dh46.android_tdd_learning_kotlin.lab10mvvm

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
    var productName: MutableLiveData<String> = MutableLiveData<String>()
    var productDesc: MutableLiveData<String> = MutableLiveData()
    var productPrice: MutableLiveData<Int> = MutableLiveData()
    var productItems: MutableLiveData<String> = MutableLiveData()

    fun getProduct(productId: String) {
        productRepository.getProduct(productId, object : IProductRepository.LoadProductCallback{
            override fun onProductResult(productResponse: ProductResponse) {
                productName.value = productResponse.name
                productDesc.value = productResponse.desc
                productPrice.value = productResponse.price
            }
        })
    }

    fun buy(){
        println("Buy")
    }
}