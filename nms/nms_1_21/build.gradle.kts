plugins {
    alias(libs.plugins.paperweight.userdev)
}

dependencies {
    paperweight.paperDevBundle("1.21-R0.1-SNAPSHOT")
    implementation(project(":api"))
    implementation(project(":core"))
}