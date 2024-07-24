plugins {
    alias(libs.plugins.jetbrainsKotlinJvm)
    id("kotlin-kapt")
}

dependencies{

    implementation(libs.androidx.room.common)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.3")
}