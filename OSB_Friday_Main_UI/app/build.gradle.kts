plugins {
    alias(libs.plugins.androidApplication)
    jacoco
}

android {

    namespace = "com.example.main_ui"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.example.main_ui"
        minSdk = 27
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
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

repositories {
    google()
    mavenCentral()
}

jacoco {
    toolVersion = "0.8.7"
}

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn(tasks.named("connectedDebugAndroidTest"))

    reports {
        xml.required.set(true)
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("customJacocoReportDir/html"))
    }

    val fileFilter = listOf("**/R.class", "**/R$*.class", "**/BuildConfig.*", "**/Manifest*.*", "**/*Test*.*")
    val debugTree = fileTree("${buildDir}/intermediates/javac/debug") { // 경로 수정
        exclude(fileFilter)
    }

    sourceDirectories.setFrom(files("src/main/java"))
    classDirectories.setFrom(files(debugTree))
    executionData.setFrom(files("${buildDir}/jacoco/testDebugUnitTest.exec"))
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.jsoup)
    implementation(libs.junit)
    implementation(files("libs\\jsoup-1.17.2.jar"))
    testImplementation("org.mockito:mockito-core:3.11.2")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
