package com.stdio.javalib

import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.sql.DriverManager.println
import java.util.regex.Pattern

object TextUtils {
    val hrefPattern: Pattern = Pattern.compile("href=\"([^\"\\s]*)\"")
    val srcPattern: Pattern = Pattern.compile("src=\"([^\"\\s]*)\"")

    @kotlin.jvm.JvmStatic
    fun main(args: Array<String>) {
        val responseBody = """<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-Ua-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width">
    <link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon">
    <title>HTTP/2 technology demo</title>
    <link rel="stylesheet" href="css/style.css" media="all">
    <link rel="stylesheet" href="css/jssocials.css" media="all">
    <link rel="stylesheet" href="css/jssocials-theme-flat.css" media="all">
    <link rel="stylesheet" href="css/font-awesome.css" media="all">
</head>
<body>
<h1 style="text-align: center; margin-top:50px;"><span style="color:#F4D200;">HTTP/2</span> TECHNOLOGY DEMO</h1>
<h3 class="center" id="titleInfo" style="margin-top:-14px; width:550px;">
    <span style="color:#56ADE2;">This test consists of 200 small images from CDN77.com so you can see the difference clearly. <div style="visibility:hidden;">Or try it with your</span> <span style="color:#F4D200;">own website</span>.</div>
</h3><br>"""

        val proxyUrl = "@@@@@@@@@@@@@@@@@@="
        val baseUrl = "BASE"

        val result = replaceAll(responseBody, proxyUrl, baseUrl)
        //System.out.println(result);
    }

    @kotlin.Throws(UnsupportedEncodingException::class)
    private fun replaceWithEncodedUrl(
        responseBody: String,
        pattern: Pattern, proxyUrl: String, baseUrl: String, part0: String
    ): String {
        val matcher = pattern.matcher(responseBody)
        val result = StringBuffer()

        while (matcher.find()) {
            val originalPath = matcher.group(1)
            println("@@@$originalPath")
            val encodedPath = URLEncoder.encode(originalPath, StandardCharsets.UTF_8.name())
            var replacement = ""
            replacement = if (originalPath != null && originalPath.startsWith("//")) {
                part0 + "=\"" + proxyUrl + "https:" + encodedPath + "\""
            } else {
                "$part0=\"$proxyUrl$baseUrl/$encodedPath\""
            }
            println("----$originalPath == |$replacement")
            matcher.appendReplacement(result, replacement)
        }
        matcher.appendTail(result)

        return result.toString()
    }

    fun replaceAll(responseBody: String, proxyUrl: String, baseUrl: String): String {
        var responseBody = responseBody
        try {
            responseBody = replaceWithEncodedUrl(responseBody, srcPattern, proxyUrl, baseUrl, "src")
            responseBody =
                replaceWithEncodedUrl(responseBody, hrefPattern, proxyUrl, baseUrl, "href")
            return responseBody
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            return responseBody
        }
    }
}
