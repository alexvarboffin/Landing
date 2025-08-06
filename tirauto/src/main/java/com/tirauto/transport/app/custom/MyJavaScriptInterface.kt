package com.tirauto.transport.app.custom

import android.content.Context
import android.webkit.JavascriptInterface
import androidx.appcompat.UWView

class MyJavaScriptInterface(private val context: Context, mView: UWView) {

    //    <script type="text/javascript">
    //    function showUserInfo() {
    //        var userInfo = Android.getUserInfo();
    //        document.getElementById("userInfo").innerText = userInfo;
    //    }
    //    </script>

    @get:JavascriptInterface
    val userInfo: String
        //    @JavascriptInterface
        get() {
            val userName = "John Doe"
            val balance = 100
            return "User: $userName, Balance: $$balance"
        }
}
