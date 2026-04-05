plugins {
    alias(libs.plugins.paperweight.userdev)
}

dependencies {
    paperweight.paperDevBundle("26.1.1.build.9-alpha")
    implementation(project(":api"))
    implementation(project(":core"))
}