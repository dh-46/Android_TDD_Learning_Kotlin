package tw.dh46.android_tdd_learning_kotlin.lab8MVP

import tw.dh46.android_tdd_learning_kotlin.lab8MVP.api.ProductResponse

/**
 *  Created by DanielHuang on 2020/4/13
 *  Day17 - Android MVP 架構
 *  -----------------------------------
 *  定義View與Presenter之間的互動
 */
class ProductContract {

    interface IProductView {

        // 取得資料後的Callback
        fun onGetResult(productResult: ProductResponse)

        // 購買成功的Callback
        fun onBuySuccess()

        // 購買失敗的Callback
        fun onBuyFailure()
    }

    interface IProductPresenter {

        // 取得商品資料
        fun getProduct(productId: String)

        // 購買商品
        fun buy(productId: String, numbers: Int)
    }
}