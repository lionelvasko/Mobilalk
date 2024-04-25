plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}

android {
    namespace = "hu.inf.szte"
    compileSdk = 34

    defaultConfig {
        applicationId = "hu.inf.szte"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("com.squareup.picasso:picasso:2.71828")

    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.google.firebase:firebase-database:20.3.1")
}