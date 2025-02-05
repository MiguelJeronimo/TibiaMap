plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.miguel.tibiamap"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.miguel.tibiamap"
        minSdk = 24
        //noinspection EditedTargetSdkVersion
        targetSdk = 35
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation ("ovh.plrapps:mapcompose:2.12.12")
    //libreria para trabajar con imagenes comprimidas
    implementation("org.apache.commons:commons-compress:1.27.1")
    implementation("io.coil-kt.coil3:coil-compose:3.0.4")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.4")
    implementation ("com.google.code.gson:gson:2.11.0")
    implementation("com.robinhood.ticker:ticker:2.0.4")
    implementation (libs.converter.scalars)
    implementation (libs.koin.androidx.compose)
//    implementation("io.coil-kt:coil-gif:2.6.0")
//    implementation("io.coil-kt:coil-compose:2.6.0")
//    implementation("io.coil-kt:coil-compose:2.0.0-rc01")
    implementation(libs.compose)
    //para consumo de apis
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
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