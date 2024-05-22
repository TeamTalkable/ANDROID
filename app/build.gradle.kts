plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinx.serialization)
    kotlin("kapt")
    id("org.jetbrains.kotlin.plugin.parcelize")
}

android {
    namespace = libs.versions.applicationId.get()
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = libs.versions.applicationId.get()
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.appVersion.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


        val apiKey = properties["native.app.key"] as? String ?: ""
        manifestPlaceholders["apiKey"] = properties["native.app.key"] as String
        buildConfigField ("String", "NATIVE_APP_KEY", "${properties["native.app.key"]}")
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
    buildFeatures {
        dataBinding = true
        buildConfig = true
    }
}

dependencies {
    // Google
    implementation(libs.material)

    // Test Dependency
    testImplementation(libs.junit)
    // ("test-espresso", "test-junit")
    androidTestImplementation(libs.bundles.androidx.android.test)

    // AndroidX
    implementation(libs.activity.ktx)
    implementation(libs.fragment.ktx)
    // ("core-ktx", "constraintlayout", "appcompat", "activity")
    implementation(libs.bundles.androidx)
    //("lifecycle-runtime-ktx", "lifecycle-viewmodel-ktx", "lifecycle-livedata")
    implementation(libs.bundles.lifecycle)
    implementation(libs.recyclerview)

    // Third-Party
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.serialization.converter)
    implementation(platform(libs.okhttp3.bom))
    //("retrofit2", "retrofit2-serialization-converter", "okhttp", "okhttp-logging-interceptor")
    implementation(libs.bundles.retrofit)
    implementation(libs.timber)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlin.coroutines)

    // Coil
    implementation(libs.coil)

    // Lottie
    implementation(libs.lottie)

    // Kakao
    implementation(libs.kakao)

    // Jetpack navi
    implementation(libs.bundles.jetpack.navi)
}