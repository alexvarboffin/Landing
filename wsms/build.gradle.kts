plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

repositories {
    maven { url = uri("https://maven.google.com") }
}

android {
    namespace = "com.wsms.client"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    buildToolsVersion = libs.versions.android.buildTools.get()

    defaultConfig {
        vectorDrawables.useSupportLibrary = true
        applicationId = "com.wsms.client"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = project.extra["versionCodeDate"]?.let { (it as () -> Int)() } ?: 1
        versionName = "1.2.$versionCode"
        setProperty("archivesBaseName", "com.wsms.client$versionCode")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }

    signingConfigs {
        create("key0") {
            keyAlias = "vavmob"
            keyPassword = "release"
            storeFile = file("..//keystore\\keystore.jks")
            storePassword = "release"
        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("key0")
            versionNameSuffix = "-DEMO"
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.gson)
    implementation(libs.pulsator4droid)

    implementation(libs.androidx.browser)
    implementation(libs.androidbrowserhelper)
} 