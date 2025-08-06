plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

//one_sygnal

//apply plugin: 'com.onesignal.androidsdk.onesignal-gradle-plugin'
////@@ apply plugin: 'com.google.gms.google-services'
////@@ apply plugin: 'com.google.firebase.crashlytics'

repositories {
    maven { url = uri("https://maven.google.com") }
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    buildToolsVersion = libs.versions.android.buildTools.get()

    namespace = "io.zerocard.pro"

    defaultConfig {
        vectorDrawables.useSupportLibrary = true

        applicationId = "io.zerocard.pro"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = project.extra["versionCodeDate"]?.let { (it as () -> Int)() } ?: 1
        versionName = "1.1.${versionCode}"
        setProperty("archivesBaseName", "Zer0card")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

//        manifestPlaceholders = [
//                onesignal_app_id               : '67248bc7-59d4-483c-a077-e2eba58c76ed',
//                // Project number pulled from dashboard, local value is ignored.
//                onesignal_google_project_number: 'REMOTE'
//        ]

        multiDexEnabled = true
    }

    signingConfigs {
//        debug {
//            storeFile = file('D:\\android\\keystore\\debug.keystore')
//            storePassword = 'android'
//            keyAlias = 'androiddebugkey'
//            keyPassword = 'android'
//        }

        create("key0") {
            keyAlias = "zerocard"
            keyPassword = "release"
            storeFile = file("D:\\kwork\\WebView\\Landing\\keystore\\keystore.jks")
            storePassword = "release"
        }

//        a19laz {
//            keyAlias = 'release'
//            keyPassword = 'release'
//            storeFile = file('keystore/keystore.jks')
//            storePassword = 'release'
//        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("key0")
            versionNameSuffix = "-DEMO"
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = false
            //proguardFiles getDefaultProguardFile('proguard-android.txt'), "proguard-rules.pro"
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            //debuggable false
            //jniDebuggable false
            signingConfig = signingConfigs.getByName("key0")
            versionNameSuffix = ".release"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
    }

//    flavorDimensions 'W'
//    productFlavors {
//        iqcent {
//            dimension = 'W'
//            applicationId = 'io.zerocard.pro'
//        }
//    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.firebase.messaging)
    
    //Firebase Crashlytics
//    implementation(libs.firebase.crashlytics)
//    implementation(libs.firebase.analytics)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)

    implementation(libs.androidx.swiperefreshlayout)
//    implementation('com.google.android.gms:play-services-ads:17.2.0') {
//        exclude(group = "com.android.support")
//    }

    implementation(project(":features:ui"))
    implementation(project(":features:landing"))

    //One Signal
    //-implementation("com.onesignal:OneSignal:[4.0.0, 4.99.99]")
    //implementation("com.google.android.gms:play-services-location:16.0.99")
    //implementation("com.google.firebase:firebase-messaging:17.3.99")
    //implementation("com.google.android.gms:play-services-ads-identifier:16.0.99")
    //implementation("com.google.android.gms:play-services-base:16.1.99")
    implementation(libs.androidx.multidex)

    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(libs.androidx.preference)
    //implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${rootProject.extra["kotlin_version"]}")
    implementation(libs.kotlin.stdlib.jdk8)
}

