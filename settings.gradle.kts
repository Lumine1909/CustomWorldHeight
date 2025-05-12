rootProject.name = "CustomWorldHeight"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }

    plugins {
        id("java")
        id("com.gradleup.shadow") version "9.0.0-beta13"
        id("io.papermc.paperweight.userdev") version "2.0.0-beta.16"
    }
}


include(":plugin")
include(":core")
include(":nms:nms_1_21_4")