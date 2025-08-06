import java.text.SimpleDateFormat
import java.util.Date

fun versionCodeDate(): Int {
    return SimpleDateFormat("yyMMdd").format(Date()).toInt()
}

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    buildToolsVersion = libs.versions.android.buildTools.get()

    namespace = "com.betapsbookmakersbonus.client"

    defaultConfig {
        vectorDrawables.useSupportLibrary = true
        applicationId = "com.betapsbookmakersbonus.client"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = versionCodeDate()
        versionName = "1.4.$versionCode"
        setProperty("archivesBaseName", "betapsbookmakersbonus$versionCode")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }

    signingConfigs {
        create("key0") {
            keyAlias = "betapsbookmakersbonus"
            keyPassword = "release"
            storeFile = file("D:\\kwork\\WebView\\Landing\\keystore\\keystore.jks")
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
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.androidx.swiperefreshlayout)
    implementation(project(":features:ui"))
    implementation(project(":features:landing"))
    implementation(libs.androidx.multidex)

    implementation(libs.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.gson)
}