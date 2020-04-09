package tw.dh46.android_tdd_learning_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

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

            // 這樣的程式碼不利於測試
            val loginId = editAccount.text.toString()
            val pwd = editPassword.text.toString()

            var isLoginIdOK = false
            //帳號至少6碼，第1碼為英文，
            if (loginId.length >= 8) {
                if (loginId.toUpperCase().first() in 'A'..'Z') {
                    isLoginIdOK = true
                }
            }
        }
    }
}
