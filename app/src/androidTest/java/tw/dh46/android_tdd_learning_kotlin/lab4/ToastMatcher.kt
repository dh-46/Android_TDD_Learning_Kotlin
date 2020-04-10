package tw.dh46.android_tdd_learning_kotlin.lab4

import android.os.IBinder
import android.view.WindowManager
import androidx.test.espresso.Root
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

/**
 *  Created by DanielHuang on 2020/4/10
 *  檢查Toast所呈現的訊息
 *
 *  Source: https://stackoverflow.com/q/28390574/9982091
 */
class ToastMatcher : TypeSafeMatcher<Root>() {

    override fun describeTo(description: Description?) {
        description?.appendText("Is Toast")
    }

    override fun matchesSafely(item: Root?): Boolean {
        if (item != null) {
            var type = item.windowLayoutParams.get().type
            if (type == WindowManager.LayoutParams.TYPE_TOAST) {
                var windowToken: IBinder = item.decorView.windowToken
                var appToken: IBinder = item.decorView.applicationWindowToken
                if (windowToken == appToken) {
                    // windowToken == appToken means this window isn't contained by any other windows.
                    // if it was a window for an activity, it would have TYPE_BASE_APPLICATION.
                    return true
                }
            }
        }
        return false
    }
}