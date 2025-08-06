plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
}

//def MYTRACKER_KEY = ""
//def APP_METRICA = ""
//def FACEBOOK_SDK = ""

//def APPSFLYER_DEV_KEY = ""
//def ONESIGNAL_APP_ID = ""

//apply plugin: 'com.onesignal.androidsdk.onesignal-gradle-plugin'
//buildscript {
//    repositories {
//        maven { url 'https://plugins.gradle.org/m2/' }
//    }
//    dependencies {
//        classpath 'gradle.plugin.com.onesignal:onesignal-gradle-plugin:0.14.0'
//    }
//}

//@@ apply plugin: 'com.google.gms.google-services'
//@@ apply plugin: 'com.google.firebase.crashlytics'

repositories {
    maven { url = uri("https://maven.google.com") }
}

android {
    namespace = "com.kwknocook.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    buildToolsVersion = libs.versions.android.buildTools.get()

    android.defaultConfig.vectorDrawables.useSupportLibrary = true

    defaultConfig {
        //buildConfigField("String", "ONESIGNAL_APP_ID", "\"${ONESIGNAL_APP_ID}\"")
        //buildConfigField("String", "APPSFLYER_DEV_KEY", "\"${APPSFLYER_DEV_KEY}\"")

        vectorDrawables.useSupportLibrary = true
        applicationId = "com.kwknocook.app"//+++
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = project.extra["versionCodeDate"]?.let { (it as () -> Int)() } ?: 1
        versionName = "1.1.$versionCode.release"
        setProperty("archivesBaseName", "appsflyer_lottie_kwkNoCook$versionCode")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
//        if (!isEmpty(ONESIGNAL_APP_ID)) {
//            manifestPlaceholders = mapOf(
//                "onesignal_app_id" to "\"${ONESIGNAL_APP_ID}\"",
//                // Project number pulled from dashboard, local value is ignored.
//                "onesignal_google_project_number" to "REMOTE"
//            )
//        }
    }

    signingConfigs {
        create("debug") {
            keyAlias = "release"
            keyPassword = "release"
            storeFile = file("keystore/keystore.jks")
            storePassword = "release"
        }
        create("release") {
            keyAlias = "release"
            keyPassword = "release"
            storeFile = file("keystore/keystore.jks")
            storePassword = "release"
        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("debug")
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = false
            //proguardFiles getDefaultProguardFile('proguard-android.txt'), "proguard-rules.pro"
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            //debuggable false
            //jniDebuggable false
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)

    //Firebase Crashlytics
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)

    //Optional
    //implementation(libs.firebase.database)
    //implementation("com.google.firebase:firebase-messaging:23.3.0")

    implementation(libs.androidx.swiperefreshlayout)
//    implementation("com.google.android.gms:play-services-ads:17.2.0") {
//        exclude(group = "com.android.support")
//    }
    implementation(project(":features:ui"))
    implementation(project(":features:landing"))
    //implementation(project(":corelib"))
    //implementation(project(":stubInjector"))

    //One Signal
//    if (isEmpty(ONESIGNAL_APP_ID)) {
//        implementation(project(":stub_onesignal"))
//    } else {
//        implementation("com.onesignal:OneSignal:[5.0.0, 5.99.99]")
//    }
    //implementation("com.google.android.gms:play-services-location:16.0.99")
    //implementation("com.google.firebase:firebase-messaging:17.3.99")
    //implementation("com.google.android.gms:play-services-ads-identifier:16.0.99")
    //implementation("com.google.android.gms:play-services-base:16.1.99")
    implementation(libs.androidx.multidex)

    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.gson)

    //features
    implementation("com.appsflyer:af-android-sdk:6.12.1")
} 