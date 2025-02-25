plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "pt.ipt.dam.movies"
    compileSdk = 34

    defaultConfig {
        applicationId = "pt.ipt.dam.movies"
        minSdk = 31
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Para slider de imagens
    implementation("androidx.viewpager2:viewpager2:1.1.0")
    // Carregamento e cache de imagens
    implementation ("com.github.bumptech.glide:glide:4.12.0")

    // Networking e Parse de JSON
    implementation("com.android.volley:volley:1.2.1")              // Requisições HTTP
    implementation("com.google.code.gson:gson:2.9.1")              // Conversão JSON <-> Objetos Kotlin
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Supabase
    implementation ("io.github.jan-tennert.supabase:postgrest-kt:1.4.7") // Cliente PostgreSQL
    implementation ("io.github.jan-tennert.supabase:gotrue-kt:1.4.7")    // Autenticação
    implementation ("io.ktor:ktor-client-android:2.3.7")                 // Cliente HTTP



}