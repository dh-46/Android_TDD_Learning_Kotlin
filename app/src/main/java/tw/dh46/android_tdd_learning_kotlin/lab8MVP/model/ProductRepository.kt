package tw.dh46.android_tdd_learning_kotlin.lab8MVP.model

import tw.dh46.android_tdd_learning_kotlin.lab8MVP.api.IProductAPI
import tw.dh46.android_tdd_learning_kotlin.lab8MVP.api.ProductResponse

/**
 *  Created by DanielHuang on 2020/4/13
 *  Day17 - Android MVP 架構
 *  -----------------------------------
 *  這裡是Model => 負責取得(商品)資料
 */
class ProductRepository(private val productAPI: IProductAPI) : IProductRepository {

    override fun getProduct(
        productId: String,
        loadProductCallback: IProductRepository.LoadProductCallback) {

        // 呼叫API
        productAPI.getProduct(productId, object: IProductAPI.ProductDataCallback {
            override fun onGetResult(productResponse: ProductResponse) {
                // 取 API 回傳資料
                loadProductCallback.onProductResult(productResponse)
            }
        })
    }
}