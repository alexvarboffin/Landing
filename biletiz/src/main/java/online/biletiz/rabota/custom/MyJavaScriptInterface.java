package online.biletiz.rabota.custom;

import android.content.Context;
import android.webkit.JavascriptInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.UWView;


public class MyJavaScriptInterface {
    
//    <script type="text/javascript">
//    function showUserInfo() {
//        var userInfo = Android.getUserInfo();
//        document.getElementById("userInfo").innerText = userInfo;
//    }
//    </script>


    private final Context context;

    public MyJavaScriptInterface(Context ctx, @NonNull UWView mView) {
        this.context = ctx;
    }

//    @JavascriptInterface
//    public void showHTML(String html) {
//        new AlertDialog.Builder(context).setTitle("HTML").setMessage(html)
//                .setPositiveButton(android.R.string.ok, null).setCancelable(false).create().show();
//    }

    @JavascriptInterface
    public String getUserInfo() {
        String userName = "John Doe";
        int balance = 100;
        return "User: " + userName + ", Balance: $" + balance;
    }
}
