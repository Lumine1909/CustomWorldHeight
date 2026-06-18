repositories {
    mavenLocal()
}

dependencies {
    implementation(project(":api"))
    compileOnly(libs.paper.api)
    implementation(libs.reflexion)
    implementation(libs.proxying)
}