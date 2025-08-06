package com.advear.client.newweb;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.CookieManager;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.google.common.base.Charsets;
import com.walhalla.ui.DLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import fi.iki.elonen.NanoHTTPD;
import okhttp3.CookieJar;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class ProxyServer extends NanoHTTPD {

    private static final Map<String, String> MIME_TYPES = new HashMap<>();

    static {
        MIME_TYPES.put("ico", "image/x-icon");
        MIME_TYPES.put("css", "text/css");
        MIME_TYPES.put("htm", "text/html");
        MIME_TYPES.put("html", "text/html");
        MIME_TYPES.put("txt", "text/plain");
        MIME_TYPES.put("js", "application/javascript");
        MIME_TYPES.put("jpg", "image/jpeg");
        MIME_TYPES.put("jpeg", "image/jpeg");
        MIME_TYPES.put("gif", "image/gif");
        MIME_TYPES.put("png", "image/png");
        MIME_TYPES.put("woff", "application/font-woff");
        MIME_TYPES.put("mp3", "audio/mpeg");
        MIME_TYPES.put("mpeg", "audio/mpeg");
        MIME_TYPES.put("oga", "audio/ogg");
    }

    ///@@@   http://localhost:1234/?proxy=https%3A%2F%2Fhtz-srv3-ts.spac.me%2Ftpic%2F2129369339%2F1724527800%2F06635193dfeab949b20cbcb76b9c86b8%2F313283084.p.41.40.0.jpg

    //private final String proxyUrl;
    private final String encodeUrl;

    private final OkHttpClient client;
    private final SessionConfig sessionConfig;


    public ProxyServer(SessionConfig sessionConfig, CookieManager cookieManager, Context context) {
        super(sessionConfig.getPort());
        this.sessionConfig = sessionConfig;
        //proxyUrl = "http://localhost:" + this.sessionConfig.getPort() + "/?proxy=";
        encodeUrl = "http://localhost:" + this.sessionConfig.getPort() + "/?encode=";

        CookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(),
                new MySharedPrefsCookiePersistor(context, sessionConfig.getSessionName()));
        //new SimpleCookieJar()

        client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .followRedirects(true)
                .build();
        try {
            start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        } catch (IOException e) {
            DLog.handleException(e);
        }
        DLog.d("\nRunning! Point your browsers to http://localhost:" + sessionConfig.port + "/ \n");

        String m = cookieManager.getCookie("http://localhost:" + sessionConfig.port);
        DLog.d("+++www++++cookie -->" + m);

        //sid=XfDOcIlPMmzJoG6RvNBG
        //.spaces.im
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        String query = session.getQueryParameterString();


        try {


//            if(1==1){
////                return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "text/html",
////                        "<html><body><h1>" +
////                                "<a href=\"" + encodeUrl + _e("https://spaces.im") + "\">@@@@</a>" +
////                                "</h1><img src=\"http://1153288396.rsc.cdn77.org/http2/tiles_final/tile_138.png\"/></body></html>");
//
//                return demo();
//
//            }
            DLog.d(query + " --> [server]");

            if (query == null) {

                //index page

                return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "text/html",
                        "<html><body><h1>" +
                                "<a href=\"" + encodeUrl + _e("https://spaces.im") + "\">@@@@</a>" +
                                "</h1><img src=\"http://1153288396.rsc.cdn77.org/http2/tiles_final/tile_138.png\"/></body></html>");

            } else if (query.contains("proxy=")) {
                String proxiedUrl = query.replace("proxy=", "");
                return newEncoded(proxiedUrl, session);
            } else if (query.contains("encode=")) {
                String tmp = query.replace("encode=", "");
                String proxiedUrl = URLDecoder.decode(tmp);
                return newEncoded(proxiedUrl, session);
            } else {
                return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "text/html",
                        "<html><body><h1>" +
                                "Proxy Error " +
                                session.getQueryParameterString() + "</h1></body></html>");

            }


        } catch (Exception e) {
            DLog.d("==========================" + e.getLocalizedMessage() + " " + uri);
            DLog.handleException(e);
            return newFixedLengthResponse(Response.Status.INTERNAL_ERROR,
                    "text/html", "<html><body><h1>" +
                            "Proxy Error " +
                            e.getLocalizedMessage() + "</h1></body></html>");
        }
    }




    private Response newEncoded(String proxiedUrl, IHTTPSession session) throws IOException {

        Method method = session.getMethod();
        URL url = new URL(proxiedUrl);
        // Получаем базовый URL
        String baseUrl = url.getProtocol() + "://" + url.getHost();

        // Проверяем, есть ли у URL нестандартный порт
        if (url.getPort() != -1) {
            baseUrl += ":" + url.getPort();
        }

        //DLog.d(query + " --> [server]");/gcaptcha/


        Request.Builder requestBuilder = new Request.Builder()
                .url(proxiedUrl)
                .method(method.name(), createRequestBody(session, method));
        // Передаем заголовки из исходного запроса
        for (Map.Entry<String, String> header : session.getHeaders().entrySet()) {
            String key = header.getKey();
            String value = header.getValue();

            // Логи заголовков для отладки
            DLog.d("@@@@@@@@@@@ " + key + " --> " + value);
            // Удаление ненужных заголовков
            if ("sec-fetch-mode".equalsIgnoreCase(key) ||
                    "sec-fetch-site".equalsIgnoreCase(key) ||
                    "sec-fetch-user".equalsIgnoreCase(key) ||
                    "sec-fetch-dest".equalsIgnoreCase(key) ||
                    "sec-ch-ua".equalsIgnoreCase(key) ||
                    "sec-ch-ua-mobile".equalsIgnoreCase(key) ||
                    "sec-ch-ua-platform".equalsIgnoreCase(key) ||
                    "upgrade-insecure-requests".equalsIgnoreCase(key) ||
                    "host".equalsIgnoreCase(key) ||


                    "remote-addr".equalsIgnoreCase(key) ||
                    "http-client-ip".equalsIgnoreCase(key) ||
                    //Ajax support "x-requested-with".equalsIgnoreCase(key) ||

                    "accept-encoding".equalsIgnoreCase(key)) {
                continue; // Пропускаем этот заголовок
            } else {

            }
//                     --> 127.0.0.1 █
//                    http-client-ip --> 127.0.0.1 █
//                    x-requested-with --> com.advear.client █

            if ("cookie".equalsIgnoreCase(key)) {

//                            StringBuilder valueBuilder = new StringBuilder(value);
//
//                            // Загружаем куки из CookieJar
//                            List<okhttp3.Cookie> cookies = client.cookieJar().loadForRequest(HttpUrl.get(url));
//                            if (!cookies.isEmpty()) {
//                                if (valueBuilder.length() > 0) {
//                                    valueBuilder.append("; ");  // Если в исходных куках что-то есть, добавляем разделитель
//                                }
//
//                                // Добавляем куки из CookieJar
//                                for (okhttp3.Cookie cookie : cookies) {
//                                    if (valueBuilder.length() > 0 && valueBuilder.charAt(valueBuilder.length() - 1) != ' ') {
//                                        valueBuilder.append("; ");
//                                    }
//                                    valueBuilder.append(cookie.name()).append("=").append(cookie.value());
//                                }
//                            }
//
//                            // Обновляем значение заголовка
//                            value = valueBuilder.toString();
                requestBuilder.addHeader(key, value);
            } else {
                requestBuilder.addHeader(key, value);
            }
        }
        // Добавляем заголовок Connection как "close"
        requestBuilder.addHeader("Connection", "close");

        // Устанавливаем заголовок Host на целевой сервер
        //requestBuilder.addHeader("Host", "target-server.com");

        Request request = requestBuilder.build();

        // Выполняем запрос и получаем ответ
        okhttp3.Response response = executeRequest(request);
        String encoding = response.header("Content-Encoding");
        String responseBody;
        ResponseBody BODY = response.body();

        if ("gzip".equalsIgnoreCase(encoding)) {
            DLog.d("@@@// Декомпрессия GZIP вручную");

            try (GZIPInputStream gzipInputStream = new GZIPInputStream(BODY.byteStream());
                 InputStreamReader reader = new InputStreamReader(gzipInputStream);
                 BufferedReader in = new BufferedReader(reader)) {
                StringBuilder decompressedResponse = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    decompressedResponse.append(line);
                }
                responseBody = decompressedResponse.toString();
            }
        } else if ("deflate".equalsIgnoreCase(encoding)) {
            DLog.d("// Декомпрессия Deflate вручную");
            try (InflaterInputStream inflaterInputStream = new InflaterInputStream(BODY.byteStream());
                 InputStreamReader reader = new InputStreamReader(inflaterInputStream);
                 BufferedReader in = new BufferedReader(reader)) {
                StringBuilder decompressedResponse = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    decompressedResponse.append(line);
                }
                responseBody = decompressedResponse.toString();
            }
        } else {
            DLog.d("// Если нет сжатия, просто читаем строку");
            responseBody = BODY.string();
        }

        //DLog.d(responseBody);

        String mimeType = null;
        String contentTypeHeader = response.header("Content-Type");
        if (TextUtils.isEmpty(contentTypeHeader)) {
            // Optionally, you can use the MediaType class to parse the content type
            MediaType mediaType = BODY != null ? BODY.contentType() : null;
            if (mediaType != null) {
                mimeType = mediaType.toString();
                DLog.d("Media Type: " + mimeType);
            } else {
                DLog.d("No Media Type found.");
            }
        } else {
            mimeType = contentTypeHeader.split(";")[0].trim();
            DLog.d("MIME Type: " + mimeType);
        }
        if (TextUtils.isEmpty(mimeType)) {
            mimeType = getMimeType(proxiedUrl);
//            DLog.d("=====================" + mime + " " + proxiedUrl);
        }
        DLog.d(mimeType);
        // Проксируем абсолютные ссылки
        if (mimeType.contains("text/html")) {
            //responseBody = responseBody.replaceAll("http://", proxyUrl + "http://");
            //responseBody = responseBody.replaceAll("https://", proxyUrl + "https://");
            responseBody = com.advear.client.newweb.TextUtils.replaceAll(responseBody, encodeUrl, baseUrl);
        }

        DLog.d("[" + baseUrl + "] [" + proxiedUrl + "]" + mimeType + " "
                + session.getQueryParameterString()
                +" "
                + session.getHeaders());

//        return newFixedLengthResponse(
//                Response.Status.lookup(response.code()),
//                response.header("Content-Type", mimeType),
//                responseBody
//        );


//        return newFixedLengthResponse(
//                Response.Status.lookup(response.code()), mimeType,
//                responseBody
//        );
      return newFixedLengthResponse(
              Response.Status.lookup(response.code()), mimeType,
                responseBody
        );

//        @Override
//        public Response serve(IHTTPSession session) {
//            FileInputStream fis = null;
//            try {
//                File file = new File(Environment.getExternalStoragePublicDirectory(ROOT)
//                        + PATH + "picture.jpg"); //path exists and its correct
//                fis = new FileInputStream(file);
//            } catch (FileNotFoundException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//          }


//        return newFixedLengthResponse(
//                Response.Status.OK
//                , mimeType,
//                fis=BODY.byteStream(), fis.available()
//        );        //the last parameter is totalBytes. Not sure what to put there

    }

    
    private String _e(String query) throws UnsupportedEncodingException {
        return URLEncoder.encode(query, Charsets.UTF_8.name());
    }


    static String def = "application/octet-stream";

    public static String getMimeType(String uri) {
        // Получение расширения файла
        String extension = getFileExtension(uri);

        // Возвращаем соответствующий MIME-тип или дефолтный
        return getOrDefault(extension, def);
    }

    static String getOrDefault(String key, String defaultValue) {
        String v;
        return (((v = MIME_TYPES.get(key)) != null) || MIME_TYPES.containsKey(key))
                ? v
                : defaultValue;
    }

    private static String getFileExtension(String uri) {
        int lastDotIndex = uri.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == uri.length() - 1) {
            return "";
        }
        return uri.substring(lastDotIndex + 1).toLowerCase();
    }

    private RequestBody createRequestBody(IHTTPSession session, Method method) throws IOException {
        if (method == Method.POST || method == Method.PUT) {
            byte[] postDataBytes = new byte[session.getInputStream().available()];
            session.getInputStream().read(postDataBytes);
            return RequestBody.create(postDataBytes);
        }
        return null;
    }

    private okhttp3.Response executeRequest(Request request) throws IOException {
        return client.newCall(request).execute();
    }
}