package com.royspins.app.adapter

import android.content.Context
import android.os.Handler
import android.text.TextUtils
import com.royspins.app.presenter.AbstractPresenter
import com.royspins.app.presenter.MainContract
import com.walhalla.landing.pagination.CatItem
import com.walhalla.ui.DLog.d
import com.walhalla.ui.DLog.handleException
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.apache.cordova.domen.ScreenType
import org.apache.cordova.domen.UIVisibleDataset
import org.apache.cordova.repository.impl.RemoteBotNoBotRepository
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Get Url from https://docs.google.com/spreadsheets/
 */
class SpreadsheetsRepository
    (var url: String, var handler: Handler, context: Context, view: MainContract.View) :
    AbstractPresenter(view) {

    private var categories: ArrayList<CatItem>? = null
    var call: Call? = null

    private fun mView_empty() {
        d("@@@")
        //new Dataset(false, "", false, "")
        //new Dataset(false, "", false, "")
    }


    //    public void write() {
    //        // Write a message to the database
    //        FirebaseDatabase database = FirebaseDatabase.getInstance();
    //        database.setPersistenceEnabled(true);
    //        DatabaseReference myRef = database.getReference();
    //        myRef.setValue(new Dataset(true, "http://ya.ru", true, "ru|ua|de|ch"));
    //        myRef.addValueEventListener(postListener);
    //    }
    //    private void writeNewUser(String userId, String name, String email) {
    //        Dataset user = new Dataset(
    //                //        name, email
    //        );
    //        FirebaseDatabase database = FirebaseDatabase.getInstance();
    //        database.setPersistenceEnabled(true);
    //
    //        DatabaseReference mDatabase = database.getReference("message");
    //        mDatabase.child(Const.KEY_USERS).child(userId).setValue(user);
    //        mDatabase.addValueEventListener(postListener);
    //    }
    override fun onNavigationItemSelected(itemId: String?) {
        val url = getUrlForNavigationItem(itemId)
        d("$itemId $url")
        mView.loadUrlInWebView(url!!)
    }

    fun getUrlForNavigationItem(title: String?): String? {
        if (categories != null) {
            for (item in categories!!) {
                if (item.title == title) {
                    return item.url
                }
            }
        }
        return null
    }

    override fun onBottomNavigationItemSelected(itemId: String?) {
        //none
    }

    fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .followRedirects(true) // Включить следование за редиректами (по умолчанию true)
            .followSslRedirects(true) // Включить следование за SSL-редиректами (по умолчанию true)
            .connectTimeout(30, TimeUnit.SECONDS) // Таймаут подключения
            .readTimeout(30, TimeUnit.SECONDS) // Таймаут чтения
            .writeTimeout(30, TimeUnit.SECONDS) // Таймаут записи
            .build()
    }

    override fun fetchRemoteConfig(context: Context) {
        val url0 = url // MainPresenter.makeUrl(url, preferences, context)
        println("@@$url0")

        val client: OkHttpClient = createOkHttpClient()

        try {
            val request: Request = Request.Builder().url(url0).build()

            if (call != null) {
                println("@@@@@@@@@@@: ${call!!.isExecuted()}")

                if (call!!.isExecuted()) {
                    call!!.cancel()
                    call = null
                }
            }

            call = client.newCall(request)
            call?.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // Обработка ошибки
                    handleException(e)
                    if (mView != null) {
                        //dataset.setEnabled(true);
                        //dataset.url = "https://twitter.com/";
                        categories = ArrayList()
                        mView_empty()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        // Получение строки из тела ответа
                        val responseBody = response.body?.string()
                        if (responseBody != null) {
                            // Обработка успешного ответа
                            d("Response: $responseBody")
                            if (mView != null){
                                handler.post {
                                    mView.loadUrlInWebView(
                                        responseBody
                                    )
                                }
                            }

                        } else {
                            // Обработка пустого тела ответа
                            d("Empty response body")
                            if (mView != null) {
                                //dataset.setEnabled(true);
                                //dataset.url = "https://twitter.com/";
                                categories = ArrayList()
                                mView_empty()
                            }
                        }
                    } else {
                        // Обработка неуспешного ответа
                        d("Request failed with code: ${response.code}")
                        if (mView != null) {
                            //dataset.setEnabled(true);
                            //dataset.url = "https://twitter.com/";
                            categories = ArrayList()
                            mView_empty()
                        }
                    }
                }
            })
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override fun destroy() {

        if (call != null) {
            println("@@@@@@@@@@@: ${call!!.isExecuted()}")

            if (call!!.isExecuted()) {
                call!!.cancel()
                call = null
            }
        }
    }

}