package tw.dh46.android_tdd_learning_kotlin.lab8MVP.model

import tw.dh46.android_tdd_learning_kotlin.lab8MVP.api.ProductResponse

/**
 *  Created by DanielHuang on 2020/4/13
 *  Day17 - Android MVP 架構
 *  -----------------------------------
 *  建立一個Model的介面，來定義方法與資料回呼(Callback)
 */
interface IProductRepository {

    // 傳入商品編號，取得商品資料
    fun getProduct(productId: String, loadProductCallback: LoadProductCallback)

    // 購買商品
    fun buy(productId: String, numbers: Int, callback: BuyProductCallback)


    /**
     * 資料回傳的介面
     */
    interface LoadProductCallback {
        // 回傳商品資料的Response
        fun onProductResult(productResponse: ProductResponse)
    }

    interface BuyProductCallback {

        fun onBuyResult(isSuccess: Boolean)
    }
}