package tw.dh46.android_tdd_learning_kotlin.lab9

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import tw.dh46.android_tdd_learning_kotlin.lab8MVP.api.IProductAPI
import tw.dh46.android_tdd_learning_kotlin.lab8MVP.api.ProductResponse
import tw.dh46.android_tdd_learning_kotlin.lab8MVP.model.IProductRepository
import tw.dh46.android_tdd_learning_kotlin.lab8MVP.model.ProductRepository

/**
 *  Created by DanielHuang on 2020/4/13
 *  測試Model: ProductRepository
 *  1. 呼叫API
 *  2. 呼叫Callback回傳取得的資料
 *  ---------------------------------------
 *  1.建立被測試物件ProductRepository
 *  2.建立Mock IProductAPI
 *  3.建立Mock IProductRepository.loadProductCallback
 *  4.初始化setup
 *  ----------------------------------------
 */
class ProductRepositoryTest {

    private lateinit var productRepository: IProductRepository

    private var productResponse = ProductResponse()

    @Mock
    private lateinit var productAPI: IProductAPI

    @Mock
    private lateinit var productCallback: IProductRepository.LoadProductCallback

    @Before
    fun setupPresenter(){

        // 初始化
        MockitoAnnotations.initMocks(this)

        productRepository = ProductRepository(productAPI)

        // 塞假資料
        productResponse.id = "pixel3"
        productResponse.name = "Google Pixel 3"
        productResponse.price = 27000
        productResponse.desc = "Desc"
    }

    @Test
    fun getProductTest(){
        val productId = "pixel3"

        // 呼叫被測試方法
        productRepository.getProduct(productId, productCallback)

        // 驗證是否有呼叫IProductAPI.getProduct
        val productApiCallbackCaptor = argumentCaptor<IProductAPI.ProductDataCallback>()
        Mockito.verify(productAPI).getProduct(any(), capture(productApiCallbackCaptor))

        // 將callback攔截下載並指定productResponse的值。
        productApiCallbackCaptor.value.onGetResult(productResponse)

        // 驗證是否有呼叫productCallback
        Mockito.verify(productCallback).onProductResult(productResponse)
    }

}