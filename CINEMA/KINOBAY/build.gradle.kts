import java.text.SimpleDateFormat
import java.util.Date

fun versionCodeDate(): Int {
    return SimpleDateFormat("yyMMdd").format(Date()).toInt()
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

//    id("com.google.gms.google-services")
//    id("com.google.firebase.crashlytics")
    id("kotlin-kapt")
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    buildToolsVersion = libs.versions.android.buildTools.get()

    namespace = "run.kinobay.android"

    defaultConfig {
        vectorDrawables.useSupportLibrary = true
        applicationId = "run.kinobay.android"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = versionCodeDate()
        versionName = "1.1.$versionCode.release"
        setProperty("archivesBaseName", "run.kinobay.android_$versionCode")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }

    signingConfigs {

        create("x") {
            keyAlias = "release"
            keyPassword = "release"
            storeFile = file("keystore/keystore.jks")
            storePassword = "release"
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("x")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding =true
        buildConfig =true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.database)

    implementation(libs.androidx.swiperefreshlayout)
    implementation(project(":features:ui"))
    implementation(project(":features:landing"))
    implementation(project(":kwk:c"))

    implementation(libs.okhttp)
    implementation(libs.androidx.multidex)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.gson)
}
