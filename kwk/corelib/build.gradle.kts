plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

// val ONESIGNAL_APP_ID: String? by rootProject.extra
// val APPSFLYER_DEV_KEY: String? by rootProject.extra

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    buildToolsVersion = libs.versions.android.buildTools.get()

    namespace = "org.apache.cordova"

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            // minifyEnabled = false // not used in lib
        }
        release {
            //isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            consumerProguardFiles("consumer-rules.pro")
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

fun isEmpty(a: String?): Boolean {
    return a.isNullOrEmpty()
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.preference.ktx)

    // testImplementation("junit:junit:4.12")
    // androidTestImplementation("com.android.support.test:runner:1.0.2")
    // androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")

    // Snackbar
    implementation(libs.androidx.cardview)
    implementation(libs.gson)

    // implementation("com.facebook.android:facebook-android-sdk:7.1.0")

    // OneSignal
    if (isEmpty(rootProject.extra["ONESIGNAL_APP_ID"] as? String)) {
        implementation(project(":stub:stub_onesignal"))
    } else {
        implementation("com.onesignal:OneSignal:${rootProject.extra["OneSignal"]}")
    }

    implementation(libs.firebase.database)
    // Firebase Crashlytics
    // implementation("com.google.firebase:firebase-crashlytics:${rootProject.extra["crashlyticsVersion"]}")
    // implementation("com.google.firebase:firebase-analytics:${rootProject.extra["analyticsVersion"]}")

    // Ads
    implementation(libs.play.services.ads.identifier)
    implementation(libs.androidx.ads.identifier)

    // Yandex
    // implementation("com.yandex.android:mobmetricalib:3.18.0")

    implementation(libs.androidx.swiperefreshlayout)

    api(project(":features:webview"))

    // OkHttp
    implementation(libs.okhttp)
    implementation("com.squareup.okhttp3:logging-interceptor:${rootProject.extra["okHttpVersion"]}")

    // Guava
    implementation("com.google.guava:guava:31.1-jre")
    implementation("androidx.annotation:annotation:1.7.1")

    // AppsFlyer
    if (isEmpty(rootProject.extra["APPSFLYER_DEV_KEY"] as? String)) {
        implementation(project(":stub:stub_appsflyer"))
    } else {
        implementation("com.appsflyer:af-android-sdk:6.9.2")
    }

    // Web Libs
    implementation(libs.androidx.browser)
    implementation(libs.androidbrowserhelper)

    api(project(":features:ui"))
    api(project(":kwk:StelthCore"))

    implementation(libs.oopsnointernet)
    implementation(libs.androidx.core.ktx)
}