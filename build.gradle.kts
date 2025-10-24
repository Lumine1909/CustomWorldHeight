plugins {
    java
    id("com.gradleup.shadow")
}

allprojects {
    plugins.apply("java")
    plugins.apply("com.gradleup.shadow")
    //plugins.apply("io.papermc.paperweight.userdev")

    group = "io.github.lumine1909"
    version = "1.5.2"
    description = "A plugin that allows you modify world's height"

    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    }
}