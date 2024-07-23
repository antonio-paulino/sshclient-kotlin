pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal() // Ensure this is included for plugins like KSP
    }
    plugins {
        id("org.jetbrains.kotlin.android") version "1.9.0"
        id("com.google.devtools.ksp") version "1.9.0-1.0.13"
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "sshclient-kotlin"
include(":app")
