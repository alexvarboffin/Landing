package com.example.android.myapplicationbrowser.repository;

import android.os.Handler;
import android.util.Log;

import com.example.android.myapplicationbrowser.Ajax;
import com.example.android.myapplicationbrowser.model.Dataset;
import com.example.android.myapplicationbrowser.model.MProxy;
import com.walhalla.ui.DLog;

import org.chromium.net.CoreApplication;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;


import java.util.Collections;

public class MainControllerHttp extends BaseController {
//    http://157.245.71.164/s/
//    http://157.245.71.164/p/

    private static final String PROXY_BASE_URL = "http://157.245.71.164";
    private static final String TAG = "@@@";


    public String txt_preload;
    public String code_preload;

    public Dataset dataset = new Dataset();

    public MainControllerHttp(MainView m, Handler handler) {
        super(m, handler);
    }


    public void asyncload_params() {
//        final MainRepository repository = new MainRepository(value -> {
//            if (value.urlForOpen.isEmpty()) {
//                code_preload = "003";
//                txt_preload = "Файл .../s/ пуст, неизвестна цель для открытия";
//                handler.sendEmptyMessage(0);
//                return;
//            }
//            dataset.urlForOpen = value.urlForOpen;
//
//            String str2 = "PROXY ";
//
//            char c = 1;
//            boolean valueOf = true;
//            if (value.proxies == null || value.proxies.isEmpty()) {
//                Log.d(TAG, "Файл с прокси-листом пуст. Поэтому грузим без прокси дальше");
//                handler.sendEmptyMessage(1);
//                dataset.openWithoutProxy = valueOf;
//                return;
//            }
//
//            StringBuilder sb = new StringBuilder();
//            sb.append("PROXIES COUNT: ");
//            sb.append(value.proxies.size());
//            Log.d(TAG, sb.toString());
//            int i = 0;
//            while (i < value.proxies.size()) {
//                StringBuilder sb2 = new StringBuilder();
//                sb2.append("CHECKING PROXY: ");
//                sb2.append(value.proxies.get(i));
//                Log.d(TAG, sb2.toString());
//                String[] _proxy_arr = value.proxies.get(i).split("@");
//                String str4 = ":";
//                String[] _proxy_host = _proxy_arr[0].split(str4);
//                String[] _proxy_auth = _proxy_arr[c].split(str4);
//                String _proxy_ip = _proxy_host[0];
//                String _proxy_port = _proxy_host[c];
//                String _proxy_login = _proxy_auth[0];
//                String _proxy_pass = _proxy_auth[c];
//                if (dataset.proxies.isEmpty()) {
//                    try {
//                        DefaultHttpClient httpclient = new DefaultHttpClient();
//                        String[] strArr = _proxy_arr;
//                        try {
//                            httpclient.getCredentialsProvider().setCredentials(new AuthScope(_proxy_ip, Integer.parseInt(_proxy_port)), new UsernamePasswordCredentials(_proxy_login, _proxy_pass));
//                            HttpHost proxy = new HttpHost(_proxy_ip, Integer.parseInt(_proxy_port));
//                            httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
//                            HttpGet get = new HttpGet("https://2ip.ru");
//                            HttpHost httpHost = proxy;
//                            String[] strArr2 = _proxy_host;
//                            try {
//                                httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Integer.valueOf(1000));
//                                if (httpclient.execute(get).getEntity() != null) {
//                                    StringBuilder sb3 = new StringBuilder();
//                                    sb3.append(str2);
//                                    DefaultHttpClient defaultHttpClient = httpclient;
//                                    sb3.append(value.proxies.get(i));
//                                    sb3.append(" @@ working");
//                                    Log.d(TAG, sb3.toString());
//                                    dataset.proxies.add(value.proxies.get(i));
//                                } else {
//                                    StringBuilder sb4 = new StringBuilder();
//                                    sb4.append(str2);
//                                    sb4.append(value.proxies.get(i));
//                                    sb4.append(" @@ NULL");
//                                    Log.d(TAG, sb4.toString());
//                                }
//                            } catch (Exception e) {
//
//                                StringBuilder sb5 = new StringBuilder();
//                                sb5.append(str2);
//                                sb5.append(value.proxies.get(i));
//                                sb5.append(" @@ FAIL!!!");
//                                sb5.append(e.getMessage());
//                                Log.d(TAG, sb5.toString());
//                                i++;
//                                c = 1;
//                            }
//                        } catch (Exception e2) {
//                            String[] strArr3 = _proxy_host;
//                            StringBuilder sb52 = new StringBuilder();
//                            sb52.append(str2);
//                            sb52.append(value.proxies.get(i));
//                            sb52.append(" @@ FAIL!!!");
//                            sb52.append(e2.getMessage());
//                            Log.d(TAG, sb52.toString());
//                            i++;
//                            c = 1;
//                        }
//                    } catch (Exception e3) {
//                        String[] strArr4 = _proxy_arr;
//                        String[] strArr5 = _proxy_host;
//                        StringBuilder sb522 = new StringBuilder();
//                        sb522.append(str2);
//                        sb522.append(value.proxies.get(i));
//                        sb522.append(" @@ FAIL!!!");
//                        sb522.append(e3.getMessage());
//                        Log.d(TAG, sb522.toString());
//                        i++;
//                        c = 1;
//                    }
//                } else {
//                    String[] strArr6 = _proxy_host;
//                }
//                i++;
//                c = 1;
//            }
//            StringBuilder sb6 = new StringBuilder();
//            sb6.append("RESULT PROXIES COUNT: ");
//            sb6.append(dataset.proxies.size());
//            Log.d(TAG, sb6.toString());
//            if (dataset.proxies.isEmpty()) {
//                Log.d(TAG, "Ни один прокси не работает. Поэтому грузим без прокси дальше");
//                handler.sendEmptyMessage(1);
//                dataset.openWithoutProxy = valueOf;
//                return;
//            }
//            for (String ___proxy : dataset.proxies) {
//                StringBuilder sb7 = new StringBuilder();
//                sb7.append("WORKING PROXY: ");
//                sb7.append(___proxy);
//                Log.d(TAG, sb7.toString());
//            }
//            handler.sendEmptyMessage(1);
//        });
//        repository.getConfig();

        Log.d(TAG, "LOAD_URL");
//        Ajax.newRequest(
//                PROXY_BASE_URL + "/s/", 1, null, new Ajax.Callback() {
//                    public void OnSuccess(String response) {
//                        if (response == null || response.isEmpty()) {
//                            code_preload = "003";
//                            txt_preload = "Файл .../s/ пуст, неизвестна цель для открытия";
//                            handler.sendEmptyMessage(0);
//                            return;
//                        }
//                        dataset.urlForOpen = response;
//                        load_proxies();
//                    }
//
//                    @Override
//                    public void OnError(int status_code, String message) {
//                        if (status_code == 407 || status_code == 500) {
//                            asyncload_params();
//                            return;
//                        }
//
//                        StringBuilder sb = new StringBuilder();
//                        sb.append("_");
//                        sb.append(status_code);
//
//                        code_preload = sb.toString();
//                        StringBuilder sb2 = new StringBuilder();
//                        sb2.append("");
//                        sb2.append(message);
//                        txt_preload = sb2.toString();
//                        StringBuilder sb3 = new StringBuilder();
//                        sb3.append("STATUS:");
//                        sb3.append(status_code);
//                        sb3.append(". MSG: ");
//                        sb3.append(message);
//                        Log.d(TAG, sb3.toString());
//                        handler.sendEmptyMessage(0);
//                    }
//                });


        //manual
        dataset.urlForOpen = "https://2ip.ru";
        dataset.openWithoutProxy = false;
        dataset.proxies = Collections.singletonList(CoreApplication.proxy);
        handler.sendEmptyMessage(1);

    }

    @Override
    public void load_proxies() {
        Log.d(TAG, "LOAD_PROXIES();");
        Ajax.newRequest(PROXY_BASE_URL + "/p/", 1, null, new Ajax.Callback() {

            @Override
            public void OnSuccess(String response) {

                String str2 = "PROXY ";

                boolean valueOf = true;
                if (response == null || response.isEmpty()) {
                    Log.d(TAG, "Файл с прокси-листом пуст. Поэтому грузим без прокси дальше");
                    handler.sendEmptyMessage(1);
                    dataset.openWithoutProxy = valueOf;
                    return;
                }

                String[] rawProxy = response.split("\\r\\n|\\n|\\r");
                //List<MProxy> list = new ArrayList<>();

                StringBuilder sb = new StringBuilder();
                sb.append("PROXIES COUNT: ");
                sb.append(rawProxy.length);
                Log.d(TAG, sb.toString());
                int i = 0;
                while (i < rawProxy.length) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("CHECKING PROXY: ");
                    sb2.append(rawProxy[i]);
                    Log.d(TAG, sb2.toString());

                    MProxy _p = MProxy.parse(rawProxy[i]);
                    //list.add(_p);

                    String str4 = ":";
                    if (dataset.proxies.isEmpty()) {
                        try {
                            DefaultHttpClient httpclient = new DefaultHttpClient();
                            try {
                                httpclient.getCredentialsProvider()
                                        .setCredentials(new AuthScope(
                                                        _p.host, _p.port
                                                )
                                                , new UsernamePasswordCredentials(_p.user, _p.password));
                                HttpHost proxy = new HttpHost(_p.host, _p.port);
                                httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
                                HttpGet get = new HttpGet("https://2ip.ru");
                                try {
                                    httpclient.getParams()
                                            .setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000);
                                    if (httpclient.execute(get).getEntity() != null) {
                                        StringBuilder sb3 = new StringBuilder();
                                        sb3.append(str2);
                                        DefaultHttpClient defaultHttpClient = httpclient;
                                        sb3.append(rawProxy[i]);
                                        sb3.append(" @@ working");
                                        Log.d(TAG, sb3.toString());
                                        dataset.proxies.add(_p);
                                    } else {
                                        StringBuilder sb4 = new StringBuilder();
                                        sb4.append(str2);
                                        sb4.append(rawProxy[i]);
                                        sb4.append(" @@ NULL");
                                        Log.d(TAG, sb4.toString());
                                    }
                                } catch (Exception e) {

                                    StringBuilder sb5 = new StringBuilder();
                                    sb5.append(str2);
                                    sb5.append(rawProxy[i]);
                                    sb5.append(" @@ FAIL!!!");
                                    sb5.append(e.getMessage());
                                    Log.d(TAG, sb5.toString());
                                    i++;
                                    String str5 = response;
                                }
                            } catch (Exception e2) {
                                StringBuilder sb52 = new StringBuilder();
                                sb52.append(str2);
                                sb52.append(rawProxy[i]);
                                sb52.append(" @@ FAIL!!!");
                                sb52.append(e2.getMessage());
                                Log.d(TAG, sb52.toString());
                                i++;
                            }
                        } catch (Exception e3) {
                            StringBuilder sb522 = new StringBuilder();
                            sb522.append(str2);
                            sb522.append(rawProxy[i]);
                            sb522.append(" @@ FAIL!!!");
                            sb522.append(e3.getMessage());
                            Log.d(TAG, sb522.toString());
                            i++;
                            String str522 = response;
                        }
                    } else {
                    }
                    i++;
                }
                StringBuilder sb6 = new StringBuilder();
                sb6.append("RESULT PROXIES COUNT: ");
                sb6.append(dataset.proxies.size());
                Log.d(TAG, sb6.toString());
                if (dataset.proxies.isEmpty()) {
                    Log.d(TAG, "Ни один прокси не работает. Поэтому грузим без прокси дальше");
                    handler.sendEmptyMessage(1);
                    dataset.openWithoutProxy = valueOf;
                    return;
                }
                for (MProxy ___proxy : dataset.proxies) {
                    DLog.d("WORKING PROXY: " + ___proxy.toString());
                }
                handler.sendEmptyMessage(1);
            }

            public void OnError(int status_code, String message) {
                if (status_code == 407 || status_code == 500) {
                    load_proxies();
                    return;
                }

                StringBuilder sb = new StringBuilder();
                String str = "";
                sb.append(str);
                sb.append(status_code);

                code_preload = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append(message);

                txt_preload = sb2.toString();
                StringBuilder sb3 = new StringBuilder();
                sb3.append("STATUS:");
                sb3.append(status_code);
                sb3.append(". MSG: ");
                sb3.append(message);
                Log.d(TAG, sb3.toString());
                handler.sendEmptyMessage(0);
            }
        });
    }
}
