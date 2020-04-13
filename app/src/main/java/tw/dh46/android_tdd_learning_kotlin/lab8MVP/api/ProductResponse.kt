package tw.dh46.android_tdd_learning_kotlin.lab8MVP.api

/**
 *  Created by DanielHuang on 2020/4/13
 *  Day17 - Android MVP 架構
 *  -----------------------------------
 *  資料物件 DataModel
 */
class ProductResponse {
    lateinit var id: String
    lateinit var name: String
    lateinit var desc: String
    var price: Int = 0
}