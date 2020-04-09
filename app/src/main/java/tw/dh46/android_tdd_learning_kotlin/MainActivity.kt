package tw.dh46.android_tdd_learning_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import tw.dh46.android_tdd_learning_kotlin.verification.RegisterVerify

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

    lateinit var btnRegister: Button
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
        var registerVerify = RegisterVerify()
        // 驗證帳號的邏輯被提取出去
        var isLoginIdOK = registerVerify.isLoginIdVerify(loginId)

        var isPasswordOk = registerVerify.isLoginPasswordVerify(pwd)

        if (isLoginIdOK && isPasswordOk) {
            // 註冊成功 儲存ID
            Repository(this).saveUserId(loginId)

            AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("註冊成功")
                    .show()

        } else {
            if (!isLoginIdOK) {
                Toast.makeText(this, "帳號不符規則", Toast.LENGTH_SHORT).show()
            }
            if (!isPasswordOk) {
                Toast.makeText(this, "密碼不符規則", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
