@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
    kotlin("kapt")
    alias(libs.plugins.hiltAndroid)
}

android {
    namespace = "me.tbsten.gachagachazamurai.data.prizeItem"
    compileSdk = 33

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // room
    implementation(libs.room.runtime)
    implementation(libs.room.compiler)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    implementation(project(mapOf("path" to ":domain")))
    implementation(project(mapOf("path" to ":database")))
}

kapt {
    correctErrorTypes = true
}
