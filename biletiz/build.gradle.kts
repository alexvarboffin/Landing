import com.android.build.gradle.internal.dsl.SigningConfig
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Properties

fun versionCodeDate(): Int {
    return SimpleDateFormat("yyMMdd").format(Date()).toInt()
}
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.services)
//            alias(libs.plugins.composeMultiplatform)
//            alias(libs.plugins.composeCompiler)
//            alias(libs.plugins.kspCompose)
//            id("com.google.firebase.crashlytics")

}

android {

    compileSdk = libs.versions.android.compileSdk.get().toInt()
    buildToolsVersion = libs.versions.android.buildTools.get()

    namespace = "online.biletiz.rabota"

    //android.defaultConfig.vectorDrawables.setUseSupportLibrary(true)

    var code = versionCodeDate()

    defaultConfig {

        vectorDrawables.useSupportLibrary = true
        applicationId = "online.biletiz.rabota"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = code
        versionName = "1.2.$code"
        setProperty("archivesBaseName", "online.biletiz.rabota$code")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }


    signingConfigs {
        create("key0") {
            storePassword = "H@Y8h2-@)0ak!@P"
            keyPassword = "*&()ujj@!00al+~_AK"
            storeFile = file("keystore\\keystore.jks")
            keyAlias = "AppDev"
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = true
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            signingConfig = signingConfigs.getByName("key0")
            versionNameSuffix = "-DEMO"
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = false
            //proguardFiles getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            //debuggable false
            //jniDebuggable false
            signingConfig = signingConfigs.getByName("key0")
            versionNameSuffix = ".release"
        }
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar", "*.aar"), "dir" to "libs")))
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    //Firebase Crashlytics
//    implementation "com.google.firebase:firebase-crashlytics:$rootProject.crashlyticsVersion"
//    implementation "com.google.firebase:firebase-analytics:$rootProject.analyticsVersion"

    //Optional
    //implementation "com.google.firebase:firebase-database:$rootProject.firebaseDatabase"
    //implementation "com.google.firebase:firebase-messaging:23.3.0"


    implementation(libs.androidx.swiperefreshlayout)
//    implementation("com.google.android.gms:play-services-ads:17.2.0") {
//        exclude group: "com.android.support"
//    }
    implementation(project(":features:ui"))
    implementation(project(":features:landing"))
    implementation(project(":kwk:c"))
    //implementation project(":core lib")
    //implementation project(":stubInjector")

    //implementation "com.google.android.gms:play-services-location:16.0.99"
    //implementation "com.google.firebase:firebase-messaging:17.3.99"
    //implementation "com.google.android.gms:play-services-ads-identifier:16.0.99"
    //implementation "com.google.android.gms:play-services-base:16.1.99"
    implementation(libs.androidx.multidex)

    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    //implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.22"//kotlin plugin version-> 1.7.1
    implementation(libs.gson)

    //features
    //implementation "com.appsflyer:af-android-sdk:6.12.1"
    implementation(libs.pulsator4droid)


    implementation(libs.analytics)
    implementation(libs.push)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.firebase.messaging)
}

