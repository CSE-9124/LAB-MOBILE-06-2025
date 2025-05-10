plugins {
    alias(libs.plugins.android.application)

}

android {
    namespace = "com.example.tp5"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.tp5"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment) // Add Navigation Fragment dependency
    implementation(libs.navigation.ui) // Add Navigation UI dependency
    implementation(libs.glide) // Add Glide dependency for image loading
    implementation(libs.circleimageview) // Add CircleImageView dependency for circular image
    implementation(libs.recyclerview) // Add RecyclerView dependency
    implementation(libs.cardview) // Add CardView dependency
    implementation("androidx.fragment:fragment-ktx:1.8.6") // Add Fragment KTX dependency
    implementation("androidx.core:core-ktx:1.16.0") // Tambahkan Core KTX untuk kompatibilitas
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
