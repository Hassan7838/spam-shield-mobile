plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.stm"
    compileSdk = 36
    buildFeatures {
        dataBinding = true
    }

    defaultConfig {
        applicationId = "com.example.stm"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    // --- Add these lines for Networking ---
// Retrofit - for making HTTP requests
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
// Gson Converter - for parsing JSON to Java objects
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
// OkHttp Logging Interceptor (optional but highly recommended for debugging)
// Helps you see the requests and responses in your Logcat
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

