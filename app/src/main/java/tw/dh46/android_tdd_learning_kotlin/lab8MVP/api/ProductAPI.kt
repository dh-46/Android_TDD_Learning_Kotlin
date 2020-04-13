package tw.dh46.android_tdd_learning_kotlin.lab8MVP.api

import android.os.Handler


interface IProductAPI {

    interface ProductDataCallback {
        fun onGetResult(productResponse: ProductResponse)
    }

    fun getProduct(productId: String, ProductDataCallback: ProductDataCallback)
}

/**
 *  Created by DanielHuang on 2020/4/13
 *  Day17 - Android MVP 架構
 *  --------------------------------
 *  這裡是模擬API取資料
 *
 */
class ProductAPI : IProductAPI {

    override fun getProduct(
        productId: String,
        productDataCallback: IProductAPI.ProductDataCallback
    ) {
        val handler = Handler()
        // 等一秒後回傳(模擬API耗時)
        handler.postDelayed(Runnable {
            val productResponse = ProductResponse()
            productResponse.id = "pixel3"
            productResponse.name = "Google Pixel 3"
            productResponse.desc = "5.5吋螢幕"
            productResponse.price = 27000
            productDataCallback.onGetResult(productResponse)
        }, 1000)
    }
}

