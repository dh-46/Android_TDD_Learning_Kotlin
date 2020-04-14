package tw.dh46.android_tdd_learning_kotlin.lab10mvvm

import androidx.databinding.ObservableField
import tw.dh46.android_tdd_learning_kotlin.lab8MVP.api.ProductResponse
import tw.dh46.android_tdd_learning_kotlin.lab8MVP.model.IProductRepository
import tw.dh46.android_tdd_learning_kotlin.lab8MVP.model.ProductRepository

/**
 * Day19_MVVM_DataBinding
 * ----------------------
 * 用來做資料繫結用的物件
 */
class ProductViewModel(val productRepository: IProductRepository) {
    var productName: ObservableField<String> = ObservableField("")
    var productDesc: ObservableField<String> = ObservableField("")
    var productPrice: ObservableField<Int> = ObservableField()
    var productItems: ObservableField<String> = ObservableField("")


    fun getProduct(productId: String) {
        productRepository.getProduct(productId, object : IProductRepository.LoadProductCallback{
            override fun onProductResult(productResponse: ProductResponse) {
                productName.set(productResponse.name)
                productDesc.set(productResponse.desc)
                productPrice.set(productResponse.price)
            }
        })
    }

    fun buy(){
        println("Buy")
    }
}