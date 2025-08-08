enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        flatDir {
            dirs("D:\\walhalla\\sdk\\android\\JetpackCompose\\WebViewKnopkakz0\\libs")
        }
        google()
//        google {
//            mavenContent {
//                includeGroupAndSubgroups("androidx")
//                includeGroupAndSubgroups("com.android")
//                includeGroupAndSubgroups("com.google")
//            }
//        }
        //mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
        jcenter()
    }
}
dependencyResolutionManagement {
    repositories {
        flatDir {
            dirs("D:\\walhalla\\sdk\\android\\JetpackCompose\\WebViewKnopkakz0\\libs")
        }
        google()
//        google {
//            mavenContent {
//                includeGroupAndSubgroups("androidx")
//                includeGroupAndSubgroups("com.android")
//                includeGroupAndSubgroups("com.google")
//            }
//        }
        //mavenLocal()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://repo1.maven.org/maven2/")
        maven("https://androidx.dev/storage/compose-compiler/repository/")

        maven("https://maven.google.com")
        maven("https://dl.bintray.com/videolan/Android")
    }
}

//pluginManagement {
//    plugins {
//        id 'org.jetbrains.kotlin.jvm' version '2.1.0'
//    }
//}

include(":T_Simple:BetAps")
//include(":T_Simple:slotmachinesonline24")

//include(":topzaymnakartu")
include(":biletiz")

//include(":tirauto") //onesignal
//include(":wsms")

//include(":camwhoer")
//include(":VavMob")//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//include(":advear")//{Firebase Crashlytics}

//include(":Zerocard") //на маркете {key}
//include(":Salebay")
//
//include(":LightningRoulette") //Не на маркете {key}
//include(":Nocook")//на маркете {key} -- {Firebase Crashlytics}
//
//
//include(":chezap")
//include(":luckyjet")

//include(":c0")

//include(":skladchina")

//include(":Mostbet")
//include(":Betting:winline")
//include(":Betting:gold_cup")

//include ":freebetbookmakers" //with splashscreen
//include ":microchecker"//with splashscreen
//include ":mostbetru"
//include(":chatgpt")
//include(":fedorova")
//include(":iqcent")

//include(":libSimpleCore")
//include(":Dashboard")
//include(":CINEMA:Royspins")
include(":CINEMA:Lordseriala")


//include(":Simple0")
//include(":Ketodieto")
//include(":Prm4u")
//include(":Empty")
//include(":cryptotracker")
//include(":microchecker")
//include(":j0bar")

//include(":stub_mtracker")
//project(':stub_mtracker').projectDir = File("@@@/stub_mtracker")

//include(":stub_appmetrica")
//project(':stub_appmetrica').projectDir = File("@@@/stub_appmetrica")

//include(":stub_facebook")
//project(':stub_facebook').projectDir = File("@@@/stub_facebook")

//include(":stub_appsflyer")
//project(':stub_appsflyer').projectDir = File("@@@/stub_appsflyer")

//include(":stubInjector")
//project(':stubInjector').projectDir = File("@@@/stubInjector")

//include(":stub_onesignal")
//project(':stub_onesignal').projectDir = File("@@@/stub_onesignal")





include(":shared")
project(":shared").projectDir = File("../WalhallaUI\\shared")

include(":features:landing")
project(":features:landing").projectDir = file("../WalhallaUI\\features\\landing")
include(":features:webview")
project(":features:webview").projectDir = File("../WalhallaUI\\features\\webview")

include(":features:ui")
project(":features:ui").projectDir = file("../WalhallaUI\\features\\ui")
//include(":kwk:c")
//project(':kwk:c').projectDir = File("@@@/corelib")

//libs for Winline
include(":sourcefb")
//libs for Winline
include(":kwk:c")
project(":kwk:c").projectDir = file("kwk/corelib")
//libs for Winline
apply("kwk/corelib/submodules.gradle.kts")

include(":kwk:StelthCore")
project(":kwk:StelthCore").projectDir = file("kwk/StelthCore")

include(":stub:stubInjector")
project(":stub:stubInjector").projectDir = file("stub/stubInjector")

//include(":javalib")
include(":example")
include(":ex")
include(":sourcefb")
