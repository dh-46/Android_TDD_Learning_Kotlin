package tw.dh46.android_tdd_learning_kotlin.lab8MVP

import tw.dh46.android_tdd_learning_kotlin.lab8MVP.api.ProductResponse
import tw.dh46.android_tdd_learning_kotlin.lab8MVP.model.IProductRepository

/**
 *  Created by DanielHuang on 2020/4/13
 *  Day17 - Android MVP 架構
 *  -----------------------------------
 *  負責商業邏輯，與Model互動
 */
class ProductPresenter(val view: ProductContract.IProductView,
                       private val model: IProductRepository): ProductContract.IProductPresenter{

    override fun getProduct(productId: String) {

        // 呼叫model撈取資料
        model.getProduct(productId, object: IProductRepository.LoadProductCallback {
            override fun onProductResult(productResponse: ProductResponse) {
                // 回傳給View更新畫面
                view.onGetResult(productResponse)
            }
        })
    }

    override fun buy(productId: String, numbers: Int) {
        model.buy(productId, numbers, object : IProductRepository.BuyProductCallback{
            override fun onBuyResult(isSuccess: Boolean) {
                if (isSuccess) {
                    view.onBuySuccess()
                } else {
                    view.onBuyFailure()
                }
            }
        })
    }
}