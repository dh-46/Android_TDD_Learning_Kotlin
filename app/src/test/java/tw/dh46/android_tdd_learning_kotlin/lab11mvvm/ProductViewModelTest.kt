package tw.dh46.android_tdd_learning_kotlin.lab11mvvm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import tw.dh46.android_tdd_learning_kotlin.lab10mvvm.ProductViewModel
import tw.dh46.android_tdd_learning_kotlin.lab8MVP.api.ProductResponse
import tw.dh46.android_tdd_learning_kotlin.lab8MVP.model.IProductRepository
import tw.dh46.android_tdd_learning_kotlin.lab9.argumentCaptor
import tw.dh46.android_tdd_learning_kotlin.lab9.capture
import tw.dh46.android_tdd_learning_kotlin.lab9.eq

/**
 * Day21_MVVM的單元測試
 * 1. 測試ProductViewModel.getProduct
 *      - 這個方法只負責把資料抓回來放到LiveData
 *      - 與View沒有直接關係
 */
class ProductViewModelTest {

    /**
     * java.lang.RuntimeException: Method getMainLooper in android.os.Looper not mocked.
     * 沒加下面的Code 會出上面的Exception
     */
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: IProductRepository

    private lateinit var productResponse: ProductResponse
    private lateinit var viewModel: ProductViewModel

    // 初始化
    @Before
    fun setup() {

        MockitoAnnotations.initMocks(this)

        productResponse = ProductResponse()

        productResponse.id = "pixel3"
        productResponse.name = "Google Pixel 3"
        productResponse.price = 27000
        productResponse.desc = "Desc"

        viewModel = ProductViewModel(repository)
    }

    /**
     * 驗證
     * 1. 是否有呼叫IProductRepository.getProduct
     * 2. 是否有改變LiveData的值
     */
    @Test
    fun getProductTest(){

        // 執行被測試的程式碼
        val productId = "pixel3"
        viewModel.getProduct(productId)

        // 準備捕捉repository是否有被呼叫成功
        val loadProductCallbackCaptor = argumentCaptor<IProductRepository.LoadProductCallback>()

        // 驗證是否有呼叫IProductRepository.getProduct
        Mockito.verify(repository).getProduct(eq(productId), capture(loadProductCallbackCaptor))

        // 將callback攔截下載並指定productResponse的值。
        loadProductCallbackCaptor.value.onProductResult(productResponse)

        // 驗證預先定義的response值與viewModel中的值是否相同
        Assert.assertEquals(productResponse.name, viewModel.productName.value)
        Assert.assertEquals(productResponse.desc, viewModel.productDesc.value)
        Assert.assertEquals(productResponse.price, viewModel.productPrice.value)
    }
}