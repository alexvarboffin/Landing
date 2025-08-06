package com.stdio.javalib;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {

    static final Pattern hrefPattern = Pattern.compile("href=\"([^\"\\s]*)\"");
    static final Pattern srcPattern = Pattern.compile("src=\"([^\"\\s]*)\"");

    public static void main(String[] args) {
        String responseBody = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta http-equiv=\"X-Ua-Compatible\" content=\"IE=edge,chrome=1\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width\">\n" +
                "    <link rel=\"shortcut icon\" href=\"img/favicon.ico\" type=\"image/x-icon\">\n" +
                "    <title>HTTP/2 technology demo</title>\n" +
                "    <link rel=\"stylesheet\" href=\"css/style.css\" media=\"all\">\n" +
                "    <link rel=\"stylesheet\" href=\"css/jssocials.css\" media=\"all\">\n" +
                "    <link rel=\"stylesheet\" href=\"css/jssocials-theme-flat.css\" media=\"all\">\n" +
                "    <link rel=\"stylesheet\" href=\"css/font-awesome.css\" media=\"all\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1 style=\"text-align: center; margin-top:50px;\"><span style=\"color:#F4D200;\">HTTP/2</span> TECHNOLOGY DEMO</h1>\n" +
                "<h3 class=\"center\" id=\"titleInfo\" style=\"margin-top:-14px; width:550px;\">\n" +
                "    <span style=\"color:#56ADE2;\">This test consists of 200 small images from CDN77.com so you can see the difference clearly. <div style=\"visibility:hidden;\">Or try it with your</span> <span style=\"color:#F4D200;\">own website</span>.</div>\n" +
                "</h3><br>";

        String proxyUrl = "@@@@@@@@@@@@@@@@@@=";
        String baseUrl = "BASE";

        String result = replaceAll(responseBody, proxyUrl, baseUrl);
        //System.out.println(result);
    }

    private static String replaceWithEncodedUrl(String responseBody,
                                                Pattern pattern, String proxyUrl, String baseUrl, String part0) throws UnsupportedEncodingException {
        Matcher matcher = pattern.matcher(responseBody);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String originalPath = matcher.group(1);
            System.out.println("@@@"+originalPath);
            String encodedPath = URLEncoder.encode(originalPath, UTF_8.name());
            String replacement = "";
            if (originalPath != null && originalPath.startsWith("//")) {
                replacement = part0+"=\"" + proxyUrl + "https:" + encodedPath + "\"";
            } else {
                replacement = part0+"=\"" + proxyUrl + baseUrl + "/" + encodedPath + "\"";
            }
            System.out.println("----" + originalPath + " == " + "|"+replacement);
            matcher.appendReplacement(result, replacement);
        }
        matcher.appendTail(result);

        return result.toString();
    }

    public static String replaceAll(String responseBody, String proxyUrl, String baseUrl) {
        try {
            responseBody = replaceWithEncodedUrl(responseBody, srcPattern, proxyUrl, baseUrl,"src");
            responseBody = replaceWithEncodedUrl(responseBody, hrefPattern, proxyUrl, baseUrl, "href");
            return responseBody;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return responseBody;
        }
    }
}
