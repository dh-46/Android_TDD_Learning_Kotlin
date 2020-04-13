package tw.dh46.android_tdd_learning_kotlin.lab9

import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import tw.dh46.android_tdd_learning_kotlin.lab8MVP.ProductContract
import tw.dh46.android_tdd_learning_kotlin.lab8MVP.ProductPresenter
import tw.dh46.android_tdd_learning_kotlin.lab8MVP.api.ProductResponse
import tw.dh46.android_tdd_learning_kotlin.lab8MVP.model.IProductRepository

/**
 *  Created by DanielHuang on 2020/4/13
 *  Day18 - Android MVP 架構的單元測試
 *  -----------------------------------
 *  測試ProductPresenter的getProduct()
 *  1. productRepository.getProduct取得產品資料
 *  2. view.onGetResult 回傳 productResponse
 *  以上兩個測試重點
 *  -----------------------------------
 *  步驟
 *  1. 建立被測試物件: IProductPresenter
 *  2. 建立 Mock: IProductRepository 需要驗證是否有呼叫 IProductRepository 的Callback
 *  3. 建立 Mock: IProductView 需要驗證是否有呼叫 IProductView 的 Callback
 *
 */
class ProductPresenterTest {

    private lateinit var presenter: ProductContract.IProductPresenter
    private var productResponse = ProductResponse()

    @Mock
    private lateinit var mockRepository: IProductRepository

    @Mock
    private lateinit var mockView: ProductContract.IProductView

    @Before
    fun setupPresenter() {
        // 1. 使用Mockito初始化Mock
        MockitoAnnotations.initMocks(this)

        // 初始化被測試的Presenter
        presenter = ProductPresenter(mockView, mockRepository)

        // ProductResponse初始化 => 測試資料
        productResponse.id = "pixel3"
        productResponse.name = "Google Pixel 3"
        productResponse.price = 27000
        productResponse.desc = "Desc"
    }

    /**
     * 測試
     *
     * 在MVP的架構，Presenter的測試非常重要，
     * 負責呼叫Repository與將資料處理過後呼叫View的Callback。
     * 對於Presenter的getProduct在取得資料後，
     * 只要呼叫View的Callback就做到Presenter的職責了，
     * 而畫面有沒有正確的顯示資料則是Activity該處理的事。
     * 在Presenter，你不會有處理View的行為，只
     * 會透過在Presenter初始化時傳進來的IProductView，
     * 要求View該做什麼事。
     */
    @Test
    fun getProductTest(){
        val productId = "pixel3"

        // 呼叫SUT測試
        presenter.getProduct(productId)

        // 透過實作的ArgumentCaptor來接Callback的參數
        // 用來取得callback，準備攔截並給值。
        val loadProductCallbackCaptor = argumentCaptor<IProductRepository.LoadProductCallback>()

        // 驗證是否有呼叫IProductRepository.getProduct
        // 使用eq()方法驗證repository.getProduct傳入的參數是否正確
        // loadProductCallbackCaptor，可以攔截callback並指定回傳的值
        Mockito.verify(mockRepository).getProduct(eq(productId), capture(loadProductCallbackCaptor))

        // 將callback攔截下載並指定productResponse的值。
        loadProductCallbackCaptor.value.onProductResult(productResponse)

        // 驗證是否有呼叫View.onGetResult及是否傳入productResponse
        // (驗證是否有呼叫View的Callback)
        Mockito.verify(mockView).onGetResult(productResponse)
    }

    /**
     * 驗證商品購買成功
     * 1. 驗證是否有呼叫Repository的方法且值是否正確
     * 2. 驗證buyCallback的值是否正確
     * 3. 驗證UI是否顯示購買成功
     */
    @Test
    fun buySuccessTest(){

    }

    @Test
    fun buyFailureTest() {

    }
}