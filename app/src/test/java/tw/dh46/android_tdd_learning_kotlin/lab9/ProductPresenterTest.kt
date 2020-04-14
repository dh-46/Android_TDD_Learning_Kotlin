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
 *  -----------------------------------
 *  MVP 架構將程式分為Model、View、Presenter。
 *  Presenter從Repository取得資料後，
 *  透過View的Interface告訴View要做什麼事。
 *  而View就只需要處理現在該呈現什麼資料。
 *  把商業邏輯與View的職責分開，這樣一來就讓可測試性提高了。
 *
 *  當我們在測試Presenter，只關注：
 *  1.是否呼叫Repository
 *  2.本身的邏輯是否正確
 *  3.是否呼叫正確的View Callback
 *
 *  如果用Robolectirc來直接測試Activity，只關注。
 *  1.Activity初始化是否有呼叫IPresenter.getProduct。
 *  2.呼叫IProductView.onGetResult，是否有將商品結果放到UI上。
 *  3.呼叫IProductView.onBuySuccess，是否有Toast。
 *  4.呼叫IProductView.onBuyFail，是否有AlertDialog。
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
        // 捕捉購買的Callback
        val buyProductCallbackCaptor = argumentCaptor<IProductRepository.BuyProductCallback>()

        val productId = "pixel3"
        val item = 3

        // 呼叫presenter.buy執行購買商品
        presenter.buy(productId, item)

        // 驗證callback與傳入的參數是否正確
        Mockito.verify(mockRepository).buy(eq(productId), eq(item), capture(buyProductCallbackCaptor))

        // 取得捕捉的callback
        buyProductCallbackCaptor.value.onBuyResult(true)

        // 驗證是否有呼叫顯示成功
        Mockito.verify(mockView).onBuySuccess()
    }

    /**
     * 購買失敗的測試
     * (假設Repository.getProduct 購買超過10份即會回傳失敗)
     * 重點在於驗證presenter與model、view的互動 而非repository buy方法的邏輯正確性
     */
    @Test
    fun buyFailureTest() {
        val buyProductCallbackCaptor = argumentCaptor<IProductRepository.BuyProductCallback>()

        val productId = "p3"
        val item = 11

        presenter.buy(productId, item)

        Mockito.verify(mockRepository).buy(eq(productId), eq(item), capture(buyProductCallbackCaptor))

        buyProductCallbackCaptor.value.onBuyResult(false)

        Mockito.verify(mockView).onBuyFailure()
    }
}