/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    id("buildlogic.java-application-conventions")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":domain-implementations:jpa"))
    implementation(project(":domain-implementations:jdbc"))
    implementation(project(":domain-implementations:jdbc"))


    implementation("com.athaydes.rawhttp:rawhttp-core:2.6.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
}

application {
    // Define the main class for the application.
    mainClass = "com.albertdiaz.bookstore.services.App"
}