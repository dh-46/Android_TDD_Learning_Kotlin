package tw.dh46.android_tdd_learning_kotlin.verification

/**
 *  Created by DanielHuang on 2020/4/9
 *
 *  驗證邏輯抽取成類別或方法，可容易撰寫測試。
 *  繼續擴大就會是選擇適合的開發架構，如MVVM、MVP、MVC等...
 */
class RegisterVerify {

    /**
     * 驗證帳號是否符合規則
     */
    fun isLoginIdVerify(loginId: String): Boolean {
        var isLoginIdOK = false
        //帳號至少6碼，第1碼為英文，j
        if (loginId.length >= 6) {
            if (loginId.toUpperCase().first() in 'A'..'Z') {
                isLoginIdOK = true
            }
        }
        return isLoginIdOK
    }

    /**
     * 驗證密碼是否符合規則
     */
    fun isLoginPasswordVerify(password: String): Boolean {
        var isPasswordOK = false
        if (password.length >= 8) {
            if (password.toUpperCase().first() in 'A'..'Z') {
                isPasswordOK = true
            }
        }
        return isPasswordOK
    }
}