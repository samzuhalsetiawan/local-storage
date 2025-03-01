plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.maven.publish)
}

android {
    namespace = "com.samzuhalsetiawan.localstorage"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // DataStore
    implementation(libs.datastore)
    implementation(libs.datastore.preferences)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.samzuhalsetiawan.localstorage"
            artifactId = "localstorage"
            version = "1.0.1"
            pom {
                name = "Local Storage"
                description = "Android library that provide convenient way to access local storage"
                developers {
                    developer {
                        id = "samzuhalsetiawan"
                        name = "Sam Zuhal Setiawan"
                        email = "samzuhalsetiawan@gmail.com"
                    }
                }
            }
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}