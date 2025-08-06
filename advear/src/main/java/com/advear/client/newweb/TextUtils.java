package com.advear.client.newweb;

import com.google.common.base.Charsets;
import com.walhalla.ui.DLog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {

    static final Pattern hrefPattern = Pattern.compile("href=\"([^\"\\s]*)\"");
    static final Pattern srcPattern = Pattern.compile("src=\"([^\"\\s]*)\"");
    static final Pattern actionPattern = Pattern.compile("action=\"([^\"\\s]*)\"");

    //<form class="no_ajax" action="

    private static String replaceWithEncodedUrl(String responseBody,
                                                Pattern pattern, String proxyUrl, String baseUrl, String part0) throws UnsupportedEncodingException {
        Matcher matcher = pattern.matcher(responseBody);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String originalPath = matcher.group(1);


            String replacement = "";
            if (originalPath != null && originalPath.startsWith("//")) {
                String encodedPath = URLEncoder.encode("https:" + originalPath, Charsets.UTF_8.name());
                replacement = part0 + "=\"" + proxyUrl + encodedPath + "\"";
                //DLog.d("----" + originalPath + " == " + "|"+replacement);
            } else if (originalPath.startsWith("/")) {
                String encodedPath = URLEncoder.encode(baseUrl + originalPath, Charsets.UTF_8.name());
                replacement = part0 + "=\"" + proxyUrl + encodedPath + "\"";
                //DLog.d("----" + originalPath + " == " + "|"+replacement);
            } else if (originalPath.startsWith("http")) {
                //http or https

                String encodedPath = URLEncoder.encode(originalPath, Charsets.UTF_8.name());
                replacement = part0 + "=\"" + proxyUrl + encodedPath + "\"";
                //DLog.d("----" + originalPath + " == " + "|"+replacement);
            } else {

                //empty

                String encodedPath = URLEncoder.encode(originalPath, Charsets.UTF_8.name());
                replacement = part0 + "=\"" + proxyUrl + encodedPath + "\"";
                DLog.d("----" + originalPath + " == " + "|" + replacement);
            }

            matcher.appendReplacement(result, replacement);
        }
        matcher.appendTail(result);

        return result.toString();
    }

    public static String replaceAll(String responseBody, String proxyUrl, String baseUrl) {
        try {
            responseBody = replaceWithEncodedUrl(responseBody, srcPattern, proxyUrl, baseUrl, "src");
            responseBody = replaceWithEncodedUrl(responseBody, hrefPattern, proxyUrl, baseUrl, "href");
            responseBody = replaceWithEncodedUrl(responseBody, actionPattern, proxyUrl, baseUrl, "action");

            return responseBody;
        } catch (UnsupportedEncodingException e) {
            DLog.handleException(e);
            return responseBody;
        }
    }
}
