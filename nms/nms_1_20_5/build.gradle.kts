plugins {
    id("io.papermc.paperweight.userdev")
}

dependencies {
    paperweight.paperDevBundle("1.20.6-R0.1-SNAPSHOT")
    implementation(project(":core"))
}