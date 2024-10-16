import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinx.serialization)
    kotlin("kapt")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("com.google.gms.google-services")
}

val properties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

android {

    namespace = libs.versions.applicationId.get()
    compileSdk = libs.versions.compileSdk.get().toInt()

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/INDEX.LIST")
        exclude("META-INF/io.netty.versions.properties")
    }

    defaultConfig {
        applicationId = libs.versions.applicationId.get()
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.appVersion.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


        val appKey = properties["native.app.key"] as? String ?: ""

        manifestPlaceholders["appKey"] = properties["native.app.key"] as String
        buildConfigField("String", "NATIVE_APP_KEY", "\"${appKey}\"")

        val gptApiKey = properties["gpt.api.key"] as? String ?: ""
        buildConfigField("String", "GPT_API_KEY", "\"${gptApiKey}\"")
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
    implementation(libs.firebase.messaging)
    implementation(platform("com.google.firebase:firebase-bom:33.4.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation(libs.firebase.database.ktx)

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
    implementation(libs.dot.indicator)

    // Coil
    implementation(libs.coil)

    // Lottie
    implementation(libs.lottie)

    // Kakao
    implementation(libs.kakao)

    // Jetpack navi
    implementation(libs.bundles.jetpack.navi)

    // Google Cloud Speech
    implementation(libs.google.cloud.speech)
    implementation(libs.google.auth)
    implementation(libs.google.gax)
    implementation(libs.google.protobuf)

    // gRPC
    implementation(libs.grpc.okhttp)
    implementation(libs.grpc.stub)
    implementation(libs.grpc.core)
    implementation(libs.grpc.protobuf)
}