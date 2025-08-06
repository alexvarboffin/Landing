package com.example.android.myapplicationbrowser;

import static java.nio.charset.StandardCharsets.UTF_8;

import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;


public class Ajax {
    public static final int METHOD_GET0 = 0;
    public static final int METHOD_POST0 = 1;


    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    private static final int SC_INTERNAL_SERVER_ERROR = 888;
    private static final String CONTENT_TYPE0 = "text/html; charset=UTF-8";


    public interface Callback {
        void OnError(int i, String str);

        void OnSuccess(String str);
    }

    public static void newRequest(final String web_url, final int method, final HashMap<String, String> params, final Callback callback) {
        new Thread(() -> {
            String str = "?";
            try {
                String url = web_url;
                int i = method;
                String str2 = "UTF-8";
                if (i == 0) {
                    if (params != null) {
                        for (Entry<String, String> item : params.entrySet()) {
                            String key = URLEncoder.encode(item.getKey(), str2);
                            String value = URLEncoder.encode(item.getValue(), str2);
                            String str3 = "=";
                            if (!url.contains(str)) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(url);
                                sb.append(str);
                                sb.append(key);
                                sb.append(str3);
                                sb.append(value);
                                url = sb.toString();
                            } else {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(url);
                                sb2.append("&");
                                sb2.append(key);
                                sb2.append(str3);
                                sb2.append(value);
                                url = sb2.toString();
                            }
                        }
                    }
                }
                HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setUseCaches(false);
                urlConnection.setConnectTimeout(3000);
                urlConnection.setReadTimeout(3000);
                urlConnection.setRequestProperty("User-Agent", "SKLADCHINA_APP");
                urlConnection.setRequestProperty("Content-Type",CONTENT_TYPE0);
                urlConnection.setRequestProperty("charset", "utf-8");
                if (method == 0) {
                    urlConnection.setRequestMethod(METHOD_GET);
                } else if (method == 1) {
                    urlConnection.setRequestMethod(METHOD_POST);
                }
                if (method == 1 && params != null) {
                    StringBuilder postData = new StringBuilder();
                    for (Entry<String, String> item2 : params.entrySet()) {
                        if (postData.length() != 0) {
                            postData.append('&');
                        }
                        postData.append(URLEncoder.encode(item2.getKey(), str2));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(item2.getValue()), str2));
                    }
                    byte[] postDataBytes = postData.toString().getBytes(str2);
                    urlConnection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                    urlConnection.getOutputStream().write(postDataBytes);
                }
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == 200 && callback != null) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    int firstline = 0;
                    while (true) {
                        String readLine = reader.readLine();
                        String line = readLine;
                        if (readLine == null) {
                            break;
                        } else if (firstline == 0) {
                            firstline = 1;
                            response.append(line);
                        } else {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append("\n");
                            sb3.append(line);
                            response.append(sb3);
                        }
                    }
                    callback.OnSuccess(response.toString());
                    reader.close();
                } else if (callback != null) {
                    callback.OnError(responseCode, urlConnection.getResponseMessage());
                }
                urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();

                if (callback != null) {
                    callback.OnError(SC_INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
                }
            }
        }).start();
    }
}
