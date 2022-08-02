group "com.example"
version "1.0-SNAPSHOT"

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

plugins {
    kotlin("multiplatform") apply false
    kotlin("android") apply false
    id("com.android.application") version "7.2.0" apply false
    id("com.android.library")  version "7.2.0" apply false
    id("org.jetbrains.compose") apply false
}