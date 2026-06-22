dependencies {
    implementation(project(":api"))
    compileOnly(libs.paper.api)
    implementation(libs.reflexion)
    implementation(files("libs/Proxying-1.0.1.jar"))
}