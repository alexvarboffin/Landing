import java.text.SimpleDateFormat
import java.util.Date

fun versionCodeDate(): Int {
    return SimpleDateFormat("yyMMdd").format(Date()).toInt()
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    buildToolsVersion = libs.versions.android.buildTools.get()

    namespace = "com.microchecker.app"

    defaultConfig {
        vectorDrawables.useSupportLibrary = true
        applicationId = "com.microchecker.app"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = versionCodeDate()
        versionName = "1.2.$versionCode"
        setProperty("archivesBaseName", "microchecker$versionCode")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }

    signingConfigs {
        create("key0") {
            keyAlias = "microchecker"
            keyPassword = "release"
            storeFile = file("keystore/keystore.jks")
            storePassword = "release"
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("key0")
            versionNameSuffix = "-DEMO"
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(
        fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar")))
    )

    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.androidx.swiperefreshlayout)
    implementation(project(":features:ui"))
    implementation(project(":features:landing"))

    implementation(libs.androidx.multidex)
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.9.2")
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.kotlin.stdlib.jdk8)
    
    implementation(libs.gson)
}