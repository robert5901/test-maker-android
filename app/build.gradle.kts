plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.testmaker"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.testmaker"
        minSdk = 27
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    sourceSets["main"].res {
        srcDirs(
            "src/main/res/layouts/main",
            "src/main/res/layouts/auth",
            "src/main/res/layouts/common",
            "src/main/res/layouts/admin",
            "src/main/res/layouts/uiKit",
        )
    }
}

dependencies {
    implementation(Dependencies.Core.CoreKtx)
    implementation(Dependencies.Core.AppCompat)
    implementation(Dependencies.Core.Material)
    implementation(Dependencies.Core.ViewBindingDelegate)
    implementation(Dependencies.Core.RecyclerView)

    implementation(Dependencies.KTX.LifecycleRuntime)

    implementation(Dependencies.Storage.RoomRuntime)
    implementation(Dependencies.Storage.RoomKtx)
    kapt(Dependencies.Storage.RoomKsp)
    implementation(Dependencies.Storage.Hawk)

    implementation(Dependencies.Koin.Android)
    implementation(Dependencies.Koin.Navigation)

    implementation(Dependencies.Navigation.Runtime)
    implementation(Dependencies.Navigation.Fragment)
    implementation(Dependencies.Navigation.Ui)
    implementation(Dependencies.Navigation.Cicerone)

    implementation(Dependencies.Network.Retrofit2)
    implementation(Dependencies.Network.RetrofitMoshiConverter)
    implementation(Dependencies.Network.Moshi)
    implementation(Dependencies.Network.OkHttp3)
    implementation(Dependencies.Network.OkHttp3Logging)
    implementation(Dependencies.Network.Okio)
}