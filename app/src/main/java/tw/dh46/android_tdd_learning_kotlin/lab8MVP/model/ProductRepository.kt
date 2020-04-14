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

    /**
     * 取得商品資料
     */
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

    /**
     * 執行購買行為
     * 假設10個以上
     */
    override fun buy(
        productId: String,
        numbers: Int,
        callback: IProductRepository.BuyProductCallback
    ) {
        if (numbers > 20) {
            callback.onBuyResult(false)
        } else if (numbers in 1..19) {
            callback.onBuyResult(true)
        }
    }
}