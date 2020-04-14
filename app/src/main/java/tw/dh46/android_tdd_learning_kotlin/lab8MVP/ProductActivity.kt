package tw.dh46.android_tdd_learning_kotlin.lab8MVP

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import tw.dh46.android_tdd_learning_kotlin.R
import tw.dh46.android_tdd_learning_kotlin.lab8MVP.api.ProductAPI
import tw.dh46.android_tdd_learning_kotlin.lab8MVP.api.ProductResponse
import tw.dh46.android_tdd_learning_kotlin.lab8MVP.model.ProductRepository
import java.text.NumberFormat

/**
 * Day17 - Android MVP 架構
 * -------------------------------------
 * Model - 管理資料來源。例：SharedPreferences、Room、呼叫API
 * View - 顯示UI和與使用者互動I，如 Activity、Fragment
 * Presenter - 負責邏輯處理
 * -------------------------------------
 * Activity是View
 * - 負責向Presenter要資料
 * - 實作IProductView以將資料更新於UI
 */
class ProductActivity : AppCompatActivity(), ProductContract.IProductView {

    lateinit var tvProductName: TextView
    lateinit var tvProductDescription: TextView
    lateinit var tvProductPrice: TextView
    lateinit var btnBuyProduct: Button
    lateinit var productPresenter: ProductContract.IProductPresenter
    private val productId = "AAA"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        // 建立Model
        val productRepository = ProductRepository(ProductAPI())

        // View必須把自己傳給Presenter
        productPresenter = ProductPresenter(this, productRepository)

        initView()
    }


    private fun initView() {
        tvProductName = findViewById(R.id.tv_product_name)
        tvProductDescription = findViewById(R.id.tv_product_descript)
        tvProductPrice = findViewById(R.id.tv_product_price)
        btnBuyProduct = findViewById(R.id.btn_buy_product)
    }

    override fun onStart() {
        super.onStart()

        productPresenter.getProduct(productId)
    }

    override fun onGetResult(productResult: ProductResponse) {
        // 更新View
        tvProductName.text = productResult.name
        tvProductDescription.text = productResult.desc


        val currencyFormat = NumberFormat.getCurrencyInstance()
        currencyFormat.maximumFractionDigits = 0
        val price = currencyFormat.format(productResult.price)
        tvProductPrice.text = price
    }

    /**
     * Lab9_購買成功後顯示Toast
     */
    override fun onBuySuccess() {
        Toast.makeText(this, "購買成功", Toast.LENGTH_SHORT).show()
    }

    /**
     * Lab9_購買失敗顯示錯誤對話框
     */
    override fun onBuyFailure() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("購買失敗").setTitle("錯誤")
        builder.show()
    }
}
