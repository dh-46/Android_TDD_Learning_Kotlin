package tw.dh46.android_tdd_learning_kotlin.lab1

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 *  Created by DanielHuang on 2020/4/9
 *  開始寫測試
 *  測試登入驗證邏輯
 */
class RegisterVerifyTest {

    private lateinit var registerVerify: RegisterVerify

    @Before
    fun setup(){
        registerVerify =
            RegisterVerify()
    }

    @Test
    fun loginVerifyTrue(){
        // val registerVerify = RegisterVerify()
        // 驗證帳號為A123456，長度滿6個字，驗證結果應為true
        assertTrue(registerVerify.isLoginIdVerify("A123456"))
    }

    @Test
    fun loginVerifyFalse(){
        // val registerVerify = RegisterVerify()

        // 驗證帳號為A1234，長度不滿6個，驗證結果應為false
        assertFalse(registerVerify.isLoginIdVerify("A1234"))
    }

    @Test
    fun passwordVerifyTrue(){
        // 驗證密碼，密碼至少需8碼，第1碼為英文，並包含1碼數字。
        assertTrue(registerVerify.isLoginPasswordVerify("abc12345"))
    }

    @Test
    fun passwordVerifyFalse(){
        assertFalse(registerVerify.isLoginPasswordVerify("123456"))
    }
}