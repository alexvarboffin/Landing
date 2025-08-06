plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false

    //id("kotlin-kapt") apply false
}
//buildscript {
////    extra.apply {
////        set("kotlin_version", "1.9.22")
////    }
//
////    dependencies {
////        classpath(libs.gradle)
////        classpath(libs.kotlin.gradle.plugin)
////        classpath("com.google.gms:google-services:4.4.2")
////        classpath("com.google.firebase:firebase-crashlytics-gradle:3.0.2")
////    }
//}

allprojects {
    extra.apply {
        set("minSdkVersion0", 21)
        set("kotlin_version", "1.9.22")
        set("APP_METRICA", "")
        set("MYTRACKER_KEY", "")
        set("FACEBOOK_SDK", "")
        set("APPSFLYER_DEV_KEY", "")
        set("ONESIGNAL_APP_ID", "")
        set("RETROFIT_VERSION", "2.11.0")
        set("okHttpVersion", "4.12.0")
    }
    extra["versionCodeDate"] = { ->
        java.text.SimpleDateFormat("yyMMdd").format(java.util.Date()).toInt()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}