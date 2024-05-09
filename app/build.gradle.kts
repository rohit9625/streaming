import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.dagger.hilt.android)
    id("kotlin-kapt")
    kotlin("plugin.serialization") version "1.9.24"
}

android {
    namespace = "com.devx.streaming"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.devx.streaming"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val supabaseUrl: String?
        val supabaseAnonKey: String?

        val localPropertiesFile = File("local.properties")
        val properties = Properties()

        if(localPropertiesFile.exists()) {
            properties.load(localPropertiesFile.inputStream())
            supabaseUrl = properties.getProperty("SUPABASE_URL")
            supabaseAnonKey = properties.getProperty("SUPABASE_ANON_KEY")
        }else {
            println("local.properties file not found!")
            supabaseUrl = System.getenv("SUPABASE_URL")
            supabaseAnonKey = System.getenv("SUPABASE_ANON_KEY")
        }

        buildConfigField("String", "SUPABASE_URL", supabaseUrl)
        buildConfigField("String", "SUPABASE_ANON_KEY", supabaseAnonKey)
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
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.coil.compose)
    // Exoplayer
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.exoplayer.dash)
    // Viewmodel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    // Navigation
    implementation(libs.androidx.navigation.compose)
    // Dagger Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    // Supabase
    implementation(platform(libs.supabase.bom))
    implementation(libs.postgrest.kt)
    implementation(libs.ktor.client.android)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}