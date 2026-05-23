plugins {
    alias(libs.plugins.paperweight.userdev)
}

dependencies {
    paperweight.paperDevBundle("26.1.2.build.65-stable")
    implementation(project(":api"))
    implementation(project(":core"))
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}