package tw.dh46.android_tdd_learning_kotlin.mock.lab7

/**
 *  Created by DanielHuang on 2020/4/13
 *  Flavor為Mock時會執行這裡
 */
class FlavorRepository {

    fun getResult(): String {
        return "Get Result from Mock"
    }
}