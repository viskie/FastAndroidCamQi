plugins { id("com.android.application") }

android {
    namespace="info.deltaindices.volumecamera"
    compileSdk=37
    defaultConfig { applicationId="info.deltaindices.volumecamera"; minSdk=26; targetSdk=37; versionCode=3; versionName="1.1.0" }
    buildTypes { release { isMinifyEnabled=false; proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),"proguard-rules.pro") } }
    compileOptions { sourceCompatibility=JavaVersion.VERSION_17; targetCompatibility=JavaVersion.VERSION_17 }
}

dependencies {
    val cameraX="1.5.3"
    implementation("androidx.activity:activity:1.12.4")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("androidx.camera:camera-core:$cameraX")
    implementation("androidx.camera:camera-camera2:$cameraX")
    implementation("androidx.camera:camera-lifecycle:$cameraX")
    implementation("androidx.documentfile:documentfile:1.1.0")
    implementation("androidx.work:work-runtime:2.10.5")
    implementation("androidx.security:security-crypto:1.1.0")
    implementation("com.google.android.gms:play-services-auth:21.4.0")
}
