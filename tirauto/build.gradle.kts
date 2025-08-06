
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
}

repositories {
    maven { url = uri("https://maven.google.com") }
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    buildToolsVersion = libs.versions.android.buildTools.get()

    namespace = "com.tirauto.transport.app"

    defaultConfig {
        vectorDrawables.useSupportLibrary = true
        applicationId = "com.tirauto.transport.app"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = project.extra["versionCodeDate"]?.let { (it as () -> Int)() } ?: 1
        versionName = "1.2.${versionCode}"
        setProperty("archivesBaseName", "com.tirauto.transport.app${versionCode}")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }

    signingConfigs {
        create("key0") {
            storePassword = "release"
            keyPassword = "release"
            storeFile = file("keystore\\keystore0rg.jks")
            keyAlias = "release"
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = true
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("key0")
            versionNameSuffix = "-DEMO"
        }
        release {
            isMinifyEnabled = false
            isShrinkResources = false
            //proguardFiles getDefaultProguardFile('proguard-android.txt'), "proguard-rules.pro"
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    //Firebase Crashlytics
//    implementation(libs.firebase.crashlytics)
//    implementation(libs.firebase.analytics)

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

    //implementation("com.google.android.gms:play-services-location:16.0.99")
    //implementation("com.google.firebase:firebase-messaging:17.3.99")
    //implementation("com.google.android.gms:play-services-ads-identifier:16.0.99")
    //implementation("com.google.android.gms:play-services-base:16.1.99")
    implementation(libs.androidx.multidex)

    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.runtime.compose)
    //implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.22")//kotlin plugin version-> 1.7.1
    implementation(libs.gson)

    //features
    //implementation("com.appsflyer:af-android-sdk:6.12.1")
    implementation(libs.pulsator4droid)
    implementation(libs.androidx.core.ktx)

//    implementation("io.appmetrica.analytics:analytics:7.2.0")
//    implementation("io.appmetrica.analytics:push:4.0.0")
    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.firebase.messaging)
    implementation("com.onesignal:OneSignal:${rootProject.extra["OneSignal"]}")
}

