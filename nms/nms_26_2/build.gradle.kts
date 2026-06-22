plugins {
    alias(libs.plugins.paperweight.userdev)
}

dependencies {
    paperweight.paperDevBundle("26.2.build.+")
    implementation(project(":api"))
    implementation(project(":core"))
}

tasks.getByName("reobfJar") {
    enabled = false
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}