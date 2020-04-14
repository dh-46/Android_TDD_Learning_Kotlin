package tw.dh46.android_tdd_learning_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import tw.dh46.android_tdd_learning_kotlin.lab2.Repository
import tw.dh46.android_tdd_learning_kotlin.lab1.RegisterVerify
import tw.dh46.android_tdd_learning_kotlin.lab10mvvm.MvvmActivity
import tw.dh46.android_tdd_learning_kotlin.lab8MVP.ProductActivity

/**
 * 這裡有一個範例是註冊的功能。輸入帳號及密碼後可註冊為會員。
 * 功能描述：
 * 1.帳號至少需6碼，第1碼為英文。
 * 2.密碼至少需8碼，第1碼為英文，並包含1碼數字。
 * 3.點擊「註冊」，若失敗則使用AlertDialg告訴使用者失敗原因。
 * 4.點擊「註冊」，若成功則導至註冊成功頁。
 * 這個範例先不考慮要儲存註冊會員需要呼叫Web API。
 * 只是單純的將帳號密碼填好後，做資料檢查，如果符合帳號及密碼的格式，我們就視為成功。
 *
 *
 */
class MainActivity : AppCompatActivity() {

    private lateinit var btnProductMvvm: Button
    lateinit var btnRegister: Button
    lateinit var btnProductPage: Button
    lateinit var editAccount: EditText
    lateinit var editPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        editAccount = findViewById(R.id.edit_account)
        editPassword = findViewById(R.id.edit_password)
        btnRegister = findViewById(R.id.btn_register)
        btnProductPage = findViewById(R.id.btn_product_page)
        btnProductPage.setOnClickListener {
            startActivity(Intent(this, ProductActivity::class.java))
        }
        btnProductMvvm = findViewById(R.id.btn_product_mvvm)
        btnProductMvvm.setOnClickListener {
            startActivity(Intent(this, MvvmActivity::class.java))
        }

        btnRegister.setOnClickListener {
            verify()
            /**
             * 這樣的程式碼不利於測試，所以需抽取成RegisterVerify類別等。
             * 若是現實中的專案，不建議直接這樣提取，
             * 應該先撰寫好相對應的單元測試，以確保邏輯不被破壞。
             */
//            val loginId = editAccount.text.toString()
//            val pwd = editPassword.text.toString()
//
//            var isLoginIdOK = false
//            //帳號至少6碼，第1碼為英文，
//            if (loginId.length >= 8) {
//                if (loginId.toUpperCase().first() in 'A'..'Z') {
//                    isLoginIdOK = true
//                }
//            }
        }
    }

    /**
     * 執行驗證
     */
    private fun verify() {
        val loginId = editAccount.text.toString()
        val pwd = editPassword.text.toString()
        var registerVerify =
            RegisterVerify()
        // 驗證帳號的邏輯被提取出去
        var isLoginIdOK = registerVerify.isLoginIdVerify(loginId)

        var isPasswordOk = registerVerify.isLoginPasswordVerify(pwd)

        if (isLoginIdOK && isPasswordOk) {
            // 註冊成功 儲存ID
            Repository(this).saveUserId(loginId)
            // 開啟成功畫面
            var intent = Intent(this, ResultActivity::class.java)
            // Lab4: 開啟後帶id
            intent.putExtra("ID",loginId)
            startActivity(intent)
        } else {
            if (!isLoginIdOK) {
                val builder = AlertDialog.Builder(this)
                Toast.makeText(this, "帳號不符規則", Toast.LENGTH_SHORT).show()
                builder.setMessage("帳號至少要6碼，第1碼為英文").setTitle("錯誤")
                builder.show()
            } else if (!isPasswordOk) {
                Toast.makeText(this, "密碼不符規則", Toast.LENGTH_SHORT).show()
                val builder = AlertDialog.Builder(this)
                builder.setMessage("密碼至少要8碼，第1碼為英文，並包含1碼數字").setTitle("錯誤")
                builder.show()
            }
        }
    }
}
