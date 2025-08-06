@echo off

rem set sourceDir=D:\kwork\WebView\Landing\Prm4u
rem set sourceDir=D:\kwork\WebView\Landing\microchecker
rem set sourceDir=D:\kwork\WebView\Landing\microchecker
rem set sourceDir=C:\Users\combo\Desktop\kwork\yoshop\app
rem set sourceDir=D:\kwork\WebView\Landing\freebetbookmakers
set sourceDir=D:\kwork\WebView\Landing\vavmob


echo ===================
mkdir %1
mkdir %1\src\main\java
mkdir %1\src\main\res
mkdir %1\src\main\assets
mkdir %1\src\main\java\com\%1\client\activity
mkdir %1\keystore

IF NOT EXIST "%1\src\main\AndroidManifest.xml" copy "%sourceDir%\src\main\AndroidManifest.xml" "%1\src\main\AndroidManifest.xml"
IF NOT EXIST "%1\build.gradle" copy "%sourceDir%\build.gradle" "%1\build.gradle"
IF NOT EXIST "%1\google-services.json" copy "%sourceDir%\google-services.json" "%1\google-services.json"

robocopy "%sourceDir%\keystore" "%1\keystore" /s /xn
robocopy "%sourceDir%\src\main\res" "%1\src\main\res" /s /xn
robocopy "%sourceDir%\src\main\assets" "%1\src\main\assets" /s /xn


