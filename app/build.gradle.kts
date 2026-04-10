plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.towergame"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.towergame"
        minSdk = 28
        targetSdk = 36
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
    buildFeatures {
        compose = true
    }
}

val natives by configurations.creating

dependencies {
    // Native dependencies for libGDX
    natives("com.badlogicgames.gdx:gdx-platform:1.12.1:natives-arm64-v8a")
    natives("com.badlogicgames.gdx:gdx-platform:1.12.1:natives-armeabi-v7a")
    natives("com.badlogicgames.gdx:gdx-platform:1.12.1:natives-x86")
    natives("com.badlogicgames.gdx:gdx-platform:1.12.1:natives-x86_64")

    implementation(libs.androidx.navigation.compose)
    implementation(libs.gdx)
    implementation(libs.gdx.backend.android)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

// Task to extract native libraries from the 'natives' configuration
tasks.register("copyAndroidNatives") {
    doFirst {
        natives.resolvedConfiguration.resolvedArtifacts.forEach { artifact ->
            val architecture = artifact.classifier?.removePrefix("natives-")
            if (architecture != null) {
                val outputDir = file("src/main/jniLibs/$architecture")
                copy {
                    from(zipTree(artifact.file))
                    into(outputDir)
                    include("*.so")
                }
            }
        }
    }
}

// Ensure natives are copied before the build
tasks.whenTaskAdded {
    if (name.startsWith("merge") && name.endsWith("NativeLibs")) {
        dependsOn("copyAndroidNatives")
    }
}
